package com.edi.im.client.service;

/**
 * 通信消息相关，聊天记录等
 * @author: <a href="568227120@qq.com">heliang.wang</a>
 * @date:   2019-08-19 001916:43
 */
public interface MsgLogger {

    /**
     * 异步写入消息
     * @param msg
     */
    void log(String msg) ;


    /**
     * 停止写入
     */
    void stop() ;

    /**
     * 查询聊天记录
     * @param key 关键字
     * @return
     */
    String query(String key) ;
}
