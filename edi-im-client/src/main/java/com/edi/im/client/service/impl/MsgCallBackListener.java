package com.edi.im.client.service.impl;

import com.edi.im.client.service.CustomMsgHandleListener;
import com.edi.im.client.service.MsgLogger;
import com.edi.im.client.util.SpringBeanFactory;

/**
 * 自定义收到消息的回调函数
 * @author: <a href="568227120@qq.com">heliang.wang</a>
 * @date:   2019-08-19 0019 16:58
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
