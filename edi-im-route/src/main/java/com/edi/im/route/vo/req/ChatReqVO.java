package com.edi.im.route.vo.req;

import com.edi.im.common.req.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Function: Google Protocol 编解码发送
 *
 * @author crossoverJie
 *         Date: 2018/05/21 15:56
 * @since JDK 1.8
 */
public class ChatReqVO extends BaseRequest {

    @NotNull(message = "userId 不能为空")
    @ApiModelProperty(required = true, value = "发送人的id", example = "1545574049323")
    private Long userId ;

    @NotNull(message = "userId 不能为空")
    @ApiModelProperty(required = true, value = "消息接收者的 userId", example = "1545574049323")
    private Long receiveUserId;

    @NotNull(message = "msg 不能为空")
    @ApiModelProperty(required = true, value = "发送的具体消息", example = "hello")
    private String msg ;

    public ChatReqVO() {
    }

    public ChatReqVO(Long userId, Long receiveUserId,String msg) {
        this.userId = userId;
        this.msg = msg;
        this.receiveUserId = receiveUserId;
    }

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChatReqVO{" +
                "userId=" + userId +
                ", receiveUserId=" + receiveUserId +
                ", msg='" + msg + '\'' +
                '}' + super.toString();
    }
}
