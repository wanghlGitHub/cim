package com.edi.im.route.controller;

import com.edi.im.common.enums.StatusEnum;
import com.edi.im.common.exception.IMException;
import com.edi.im.common.pojo.IMUserInfo;
import com.edi.im.common.res.BaseResponse;
import com.edi.im.common.res.NULLBody;
import com.edi.im.common.route.algorithm.RouteHandle;
import com.edi.im.route.cache.ServerCache;
import com.edi.im.route.service.AccountService;
import com.edi.im.route.service.UserInfoCacheService;
import com.edi.im.route.vo.req.ChatReqVO;
import com.edi.im.route.vo.req.LoginReqVO;
import com.edi.im.route.vo.req.OffLineReqVO;
import com.edi.im.route.vo.req.P2PReqVO;
import com.edi.im.route.vo.req.RegisterInfoReqVO;
import com.edi.im.route.vo.res.IMServerResVO;
import com.edi.im.route.vo.res.RegisterInfoResVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * IM路由
 *
 * @author: <a href="568227120@qq.com">heliang.wang</a>
 * @date: 2019-08-19 0019 14:48
 */
@RestController
@RequestMapping("/")
public class RouteController {

    private final static Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private ServerCache serverCache;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserInfoCacheService userInfoCacheService;
    @Autowired
    private RouteHandle routeHandle;

    /**
     * <p>方法名称: registerAccount | 描述: 注册账号</p>
     *
     * @param registerInfoReqVO
     * @return com.edi.im.common.res.BaseResponse<com.edi.im.route.vo.res.RegisterInfoResVO>
     * @author: heliang.wang
     * @date: 2019-08-19 0019 14:44
     */
    @ApiOperation("注册账号")
    @PostMapping("registerAccount")
    public BaseResponse<RegisterInfoResVO> registerAccount(@RequestBody RegisterInfoReqVO registerInfoReqVO) throws Exception {
        BaseResponse<RegisterInfoResVO> res = new BaseResponse();

        long userId = System.currentTimeMillis();
        RegisterInfoResVO info = new RegisterInfoResVO(userId, registerInfoReqVO.getUserName());
        info = accountService.register(info);

        res.setDataBody(info);
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }

    /**
     * <p>方法名称: login | 描述: 登录服务器</p>
     *
     * @param loginReqVO
     * @return com.edi.im.common.res.BaseResponse<com.edi.im.route.vo.res.IMServerResVO>
     * @author: heliang.wang
     * @date: 2019-08-19 0019 14:45
     */
    @ApiOperation("登录并获取服务器")
    @PostMapping("login")
    public BaseResponse<IMServerResVO> login(@RequestBody LoginReqVO loginReqVO) throws Exception {
        BaseResponse<IMServerResVO> res = new BaseResponse();

        //登录校验
        StatusEnum status = accountService.login(loginReqVO);
        if (status == StatusEnum.SUCCESS) {

            String server = routeHandle.routeServer(serverCache.getAll(), String.valueOf(loginReqVO.getUserId()));
            String[] serverInfo = server.split(":");
            IMServerResVO vo = new IMServerResVO(serverInfo[0], Integer.parseInt(serverInfo[1]), Integer.parseInt(serverInfo[2]));

            //保存路由信息
            accountService.saveRouteInfo(loginReqVO, server);

            res.setDataBody(vo);

        }
        res.setCode(status.getCode());
        res.setMessage(status.getMessage());

        return res;
    }

    /**
     * <p>方法名称: groupRoute | 描述: 群聊</p>
     *
     * @param groupReqVO
     * @return com.edi.im.common.res.BaseResponse<com.edi.im.common.res.NULLBody>
     * @author: heliang.wang
     * @date: 2019-08-19 0019 14:46
     */
    @ApiOperation("群聊 API")
    @PostMapping("groupRoute")
    public BaseResponse<NULLBody> groupRoute(@RequestBody ChatReqVO groupReqVO) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();
        LOGGER.info("msg=[{}]", groupReqVO.toString());
        //获取所有的推送列表
        Map<Long, IMServerResVO> serverResVOMap = accountService.loadRouteRelated();
        for (Map.Entry<Long, IMServerResVO> cimServerResVOEntry : serverResVOMap.entrySet()) {
            Long receiveId = cimServerResVOEntry.getKey();
            IMServerResVO value = cimServerResVOEntry.getValue();
            if (receiveId.equals(groupReqVO.getUserId())) {
                //过滤掉自己
                IMUserInfo IMUserInfo = userInfoCacheService.loadUserInfoByUserId(groupReqVO.getUserId());
                LOGGER.warn("过滤掉了发送者 userId={}", IMUserInfo.toString());
                continue;
            }
            //推送消息
            String url = "http://" + value.getIp() + ":" + value.getHttpPort() + "/sendMsg";
            ChatReqVO chatVO = new ChatReqVO(groupReqVO.getUserId(), receiveId, groupReqVO.getMsg());
            accountService.pushMsg(url, groupReqVO.getUserId(), chatVO);
        }

        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }


    /**
     * <p>方法名称: p2pRoute | 描述: 私聊</p>
     *
     * @param p2pRequest
     * @return com.edi.im.common.res.BaseResponse<com.edi.im.common.res.NULLBody>
     * @author: heliang.wang
     * @date: 2019-08-19 0019 14:47
     */
    @ApiOperation("私聊 API")
    @PostMapping("p2pRoute")
    public BaseResponse<NULLBody> p2pRoute(@RequestBody P2PReqVO p2pRequest) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();

        try {
            //获取接收消息用户的路由信息
            IMServerResVO IMServerResVO = accountService.loadRouteRelatedByUserId(p2pRequest.getReceiveUserId());
            //推送消息
            String url = "http://" + IMServerResVO.getIp() + ":" + IMServerResVO.getHttpPort() + "/sendMsg";

            //p2pRequest.getReceiveUserId()==>消息接收者的 userID
            ChatReqVO chatVO = new ChatReqVO(p2pRequest.getUserId(), p2pRequest.getReceiveUserId(), p2pRequest.getMsg());
            accountService.pushMsg(url, p2pRequest.getUserId(), chatVO);

            res.setCode(StatusEnum.SUCCESS.getCode());
            res.setMessage(StatusEnum.SUCCESS.getMessage());

        } catch (IMException e) {
            res.setCode(e.getErrorCode());
            res.setMessage(e.getErrorMessage());
        }
        return res;
    }

    /**
     * <p>方法名称: offLine | 描述: 下线</p>
     *
     * @param offLineReqVO
     * @return com.edi.im.common.res.BaseResponse<com.edi.im.common.res.NULLBody>
     * @author: heliang.wang
     * @date: 2019-08-19 0019 14:47
     */
    @ApiOperation("客户端下线")
    @PostMapping("offLine")
    public BaseResponse<NULLBody> offLine(@RequestBody OffLineReqVO offLineReqVO) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();

        IMUserInfo IMUserInfo = userInfoCacheService.loadUserInfoByUserId(offLineReqVO.getUserId());

        LOGGER.info("下线用户[{}]", IMUserInfo.toString());
        accountService.offLine(offLineReqVO.getUserId());
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }


    /**
     * <p>方法名称: onlineUser | 描述: 获取所有在线用户</p>
     *
     * @param
     * @return com.edi.im.common.res.BaseResponse<java.util.Set   <   com.edi.im.common.pojo.IMUserInfo>>
     * @author: heliang.wang
     * @date: 2019-08-19 0019 14:47
     */
    @ApiOperation("获取所有在线用户")
    @PostMapping("onlineUser")
    public BaseResponse<Set<IMUserInfo>> onlineUser() throws Exception {
        BaseResponse<Set<IMUserInfo>> res = new BaseResponse();

        Set<IMUserInfo> IMUserInfos = userInfoCacheService.onlineUser();
        res.setDataBody(IMUserInfos);
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }
}
