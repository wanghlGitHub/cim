package com.edi.im.client.service.impl;

import com.edi.im.client.service.CustomMsgHandleListener;
import com.edi.im.client.service.MsgLogger;
import com.edi.im.client.util.SpringBeanFactory;

/**
 * Function:自定义收到消息回调
 *
 * @author crossoverJie
 *         Date: 2019/1/6 17:49
 * @since JDK 1.8
 */
public class MsgCallBackListener implements CustomMsgHandleListener {


    private MsgLogger msgLogger ;

    public MsgCallBackListener() {
        this.msgLogger = SpringBeanFactory.getBean(MsgLogger.class) ;
    }

    @Override
    public void handle(String msg) {
        msgLogger.log(msg) ;
    }
}
