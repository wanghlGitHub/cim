package com.edi.im.client.service;

/**
 * 自定义收到消息的回调函数
 * @author: <a href="568227120@qq.com">heliang.wang</a>
 * @date:   2019-08-19 0019 16:58
 */
public interface CustomMsgHandleListener {

    /**
     * 消息回调
     * @param msg
     */
    void handle(String msg);
}
