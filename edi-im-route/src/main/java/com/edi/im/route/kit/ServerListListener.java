package com.edi.im.route.kit;

import com.edi.im.route.config.AppConfiguration;
import com.edi.im.route.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/12/23 00:35
 * @since JDK 1.8
 */
public class ServerListListener implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ServerListListener.class);

    private ZKit zkUtil;

    private AppConfiguration appConfiguration;


    public ServerListListener() {
        zkUtil = SpringBeanFactory.getBean(ZKit.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {
        //注册监听服务
        zkUtil.subscribeEvent(appConfiguration.getZkRoot());

    }
}
