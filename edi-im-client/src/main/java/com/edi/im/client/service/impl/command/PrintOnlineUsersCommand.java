package com.edi.im.client.service.impl.command;

import com.edi.im.client.service.InnerCommand;
import com.edi.im.client.service.RouteRequest;
import com.edi.im.client.vo.res.OnlineUsersResVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-01-27 19:37
 * @since JDK 1.8
 */
@Service
public class PrintOnlineUsersCommand implements InnerCommand {
    private final static Logger LOGGER = LoggerFactory.getLogger(PrintOnlineUsersCommand.class);


    @Autowired
    private RouteRequest routeRequest ;

    @Override
    public void process(String msg) {
        try {
            List<OnlineUsersResVO.DataBodyBean> onlineUsers = routeRequest.onlineUsers();

            LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            for (OnlineUsersResVO.DataBodyBean onlineUser : onlineUsers) {
                LOGGER.info("userId={}=====userName={}", onlineUser.getUserId(), onlineUser.getUserName());
            }
            LOGGER.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
    }
}
