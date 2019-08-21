package com.edi.im.client.client;

import com.edi.im.client.config.AppConfiguration;
import com.edi.im.client.service.MsgHandle;
import com.edi.im.client.service.RouteRequest;
import com.edi.im.client.service.impl.ClientInfo;
import com.edi.im.client.vo.req.GoogleProtocolVO;
import com.edi.im.client.vo.req.LoginReqVO;
import com.edi.im.client.vo.res.IMServerResVO;
import com.edi.im.client.channel.IMClientHandleInitializer;
import com.edi.im.common.constant.Constants;
import com.edi.im.common.protocol.IMRequestProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 22/05/2018 14:19
 * @since JDK 1.8
 */
@Component
public class IMClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMClient.class);

    private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("im-work"));

    @Value("${im.user.id}")
    private long userId;

    @Value("${im.user.userName}")
    private String userName;

    private SocketChannel channel;

    @Autowired
    private RouteRequest routeRequest;

    @Autowired
    private AppConfiguration configuration;

    @Autowired
    private MsgHandle msgHandle;

    @Autowired
    private ClientInfo clientInfo;

    /**
     * 重试次数
     */
    private int errorCount;

    @PostConstruct
    public void start() throws Exception {

        //登录 + 获取可以使用的服务器 ip+port
        IMServerResVO.ServerInfo cimServer = userLogin();
        //启动客户端
        startClient(cimServer);
        //向服务端注册
        loginIMServer();
    }

    /**
     * 启动客户端
     *
     * @param cimServer
     * @throws InterruptedException
     */
    private void startClient(IMServerResVO.ServerInfo cimServer) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new IMClientHandleInitializer());

        ChannelFuture future = null;
        try {
            future = bootstrap.connect(cimServer.getIp(), cimServer.getImServerPort()).sync();
        } catch (InterruptedException e) {
            errorCount++;

            if (errorCount >= configuration.getErrorCount()) {
                LOGGER.error("链接失败次数达到上限[{}]次", errorCount);
                msgHandle.shutdown();
            }
            LOGGER.error("连接失败", e);
        }
        if (future.isSuccess()) {
            LOGGER.info("启动 cim client 成功");
        }
        channel = (SocketChannel) future.channel();
    }

    /**
     * 登录+路由服务器
     *
     * @return 路由服务器信息
     * @throws Exception
     */
    private IMServerResVO.ServerInfo userLogin() {
        LoginReqVO loginReqVO = new LoginReqVO(userId, userName);
        IMServerResVO.ServerInfo cimServer = null;
        try {
            cimServer = routeRequest.getCIMServer(loginReqVO);

            //保存系统信息
            clientInfo.saveServiceInfo(cimServer.getIp() + ":" + cimServer.getImServerPort())
                    .saveUserInfo(userId, userName);

            LOGGER.info("cimServer=[{}]", cimServer.toString());
        } catch (Exception e) {
            errorCount++;

            if (errorCount >= configuration.getErrorCount()) {
                LOGGER.error("重连次数达到上限[{}]次", errorCount);
                msgHandle.shutdown();
            }
            LOGGER.error("登录失败", e);
        }
        return cimServer;
    }

    /**
     * <p>方法名称: loginIMServer | 描述: 向服务器注册</p>
     * @param
     * @return void
     * @author: <a href="568227120@qq.com">heliang.wang</a>
     * @date:   2019-08-19 0019 16:46
     */
    private void loginIMServer() {
        IMRequestProto.IMReqProtocol login = IMRequestProto.IMReqProtocol.newBuilder()
                .setRequestId(userId)
                .setReqMsg(userName)
                .setType(Constants.CommandType.LOGIN)
                .build();
        ChannelFuture future = channel.writeAndFlush(login);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("注册成功={}", login.toString()));
    }

    /**
     * 发送消息字符串
     *
     * @param msg
     */
    public void sendStringMsg(String msg) {
        ByteBuf message = Unpooled.buffer(msg.getBytes().length);
        message.writeBytes(msg.getBytes());
        ChannelFuture future = channel.writeAndFlush(message);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("客户端手动发消息成功={}", msg));

    }

    /**
     * 发送 Google Protocol 编解码字符串
     *
     * @param googleProtocolVO
     */
    public void sendGoogleProtocolMsg(GoogleProtocolVO googleProtocolVO) {

        IMRequestProto.IMReqProtocol protocol = IMRequestProto.IMReqProtocol.newBuilder()
                .setRequestId(googleProtocolVO.getRequestId())
                .setReqMsg(googleProtocolVO.getMsg())
                .setType(Constants.CommandType.MSG)
                .build();


        ChannelFuture future = channel.writeAndFlush(protocol);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("客户端手动发送 Google Protocol 成功={}", googleProtocolVO.toString()));

    }


    public void reconnect() throws Exception {
        if (channel != null && channel.isActive()) {
            return;
        }
        //首先清除路由信息，下线
        routeRequest.offLine();

        LOGGER.info("开始重连。。");
        start();
        LOGGER.info("重连成功！！");
    }

    /**
     * 关闭
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        if (channel != null){
            channel.close();
        }
    }
}
