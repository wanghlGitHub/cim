package com.edi.im.client.controller;

import com.edi.im.client.client.IMClient;
import com.edi.im.client.service.RouteRequest;
import com.edi.im.client.vo.req.GoogleProtocolVO;
import com.edi.im.client.vo.req.GroupReqVO;
import com.edi.im.client.vo.req.SendMsgReqVO;
import com.edi.im.client.vo.req.StringReqVO;
import com.edi.im.client.vo.res.SendMsgResVO;
import com.edi.im.common.constant.Constants;
import com.edi.im.common.enums.StatusEnum;
import com.edi.im.common.res.BaseResponse;
import com.edi.im.common.res.NULLBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 22/05/2018 14:46
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/")
public class ClientController {

    /**
     * 统计 service
     */
    @Autowired
    private CounterService counterService;

    @Autowired
    private IMClient heartbeatClient;


    @Autowired
    private RouteRequest routeRequest;


    /**
     * 向服务端发消息 字符串
     *
     * @param stringReqVO
     * @return
     */
    @ApiOperation("客户端发送消息，字符串")
    @PostMapping("sendStringMsg")
    public BaseResponse<NULLBody> sendStringMsg(@RequestBody StringReqVO stringReqVO) {
        BaseResponse<NULLBody> res = new BaseResponse();

        heartbeatClient.sendStringMsg(stringReqVO.getMsg());

        // 利用 actuator 来自增
        counterService.increment(Constants.COUNTER_CLIENT_PUSH_COUNT);

        SendMsgResVO sendMsgResVO = new SendMsgResVO();
        sendMsgResVO.setMsg("OK");
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }

    /**
     * 向服务端发消息 Google ProtoBuf
     *
     * @param googleProtocolVO
     * @return
     */
    @ApiOperation("向服务端发消息 Google ProtoBuf")
    @PostMapping("sendProtoBufMsg")
    public BaseResponse<NULLBody> sendProtoBufMsg(@RequestBody GoogleProtocolVO googleProtocolVO) {
        BaseResponse<NULLBody> res = new BaseResponse();

        heartbeatClient.sendGoogleProtocolMsg(googleProtocolVO);

        // 利用 actuator 来自增
        counterService.increment(Constants.COUNTER_CLIENT_PUSH_COUNT);

        SendMsgResVO sendMsgResVO = new SendMsgResVO();
        sendMsgResVO.setMsg("OK");
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }


    /**
     * 群发消息
     *
     * @param sendMsgReqVO
     * @return
     */
    @ApiOperation("群发消息")
    @PostMapping("sendGroupMsg")
    public BaseResponse sendGroupMsg(@RequestBody SendMsgReqVO sendMsgReqVO) throws Exception {
        BaseResponse<NULLBody> res = new BaseResponse();

        GroupReqVO groupReqVO = new GroupReqVO(sendMsgReqVO.getUserId(), sendMsgReqVO.getMsg());
        routeRequest.sendGroupMsg(groupReqVO);

        counterService.increment(Constants.COUNTER_SERVER_PUSH_COUNT);

        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getMessage());
        return res;
    }
}
