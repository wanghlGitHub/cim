package com.edi.im.server.controller;

import com.edi.im.common.constant.Constants;
import com.edi.im.common.enums.StatusEnum;
import com.edi.im.common.res.BaseResponse;
import com.edi.im.server.server.IMServer;
import com.edi.im.server.vo.req.SendMsgReqVO;
import com.edi.im.server.vo.res.SendMsgResVO;
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
public class ServerController {

    @Autowired
    private IMServer IMServer;

    /**
     * 统计 service
     */
    @Autowired
    private CounterService counterService;

    /**
     * 向服务端发消息
     *
     * @param sendMsgReqVO
     * @return
     */
    @ApiOperation("服务端发送消息")
    @PostMapping("sendMsg")
    public BaseResponse<SendMsgResVO> sendMsg(@RequestBody SendMsgReqVO sendMsgReqVO) {
        BaseResponse<SendMsgResVO> res = new BaseResponse();
        boolean sendResult = IMServer.sendMsg(sendMsgReqVO);

        counterService.increment(Constants.COUNTER_SERVER_PUSH_COUNT);

        SendMsgResVO sendMsgResVO = new SendMsgResVO();
        sendMsgResVO.setMsg("OK");
        if (sendResult) {
            res.setCode(StatusEnum.SUCCESS.getCode());
            res.setMessage(StatusEnum.SUCCESS.getMessage());
        } else {
            res.setCode(StatusEnum.OFFLINE_MSG.getCode());
            res.setMessage(StatusEnum.OFFLINE_MSG.getMessage());
        }
        res.setDataBody(sendMsgResVO);
        return res;
    }

}
