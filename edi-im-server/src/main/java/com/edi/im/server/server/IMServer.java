package com.edi.im.server.server;

import com.edi.im.common.constant.Constants;
import com.edi.im.common.protocol.IMRequestProto;
import com.edi.im.server.constant.Constant;
import com.edi.im.server.init.IMServerInitializer;
import com.edi.im.server.util.RedisUtil;
import com.edi.im.server.util.SessionSocketHolder;
import com.edi.im.server.vo.req.SendMsgReqVO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 21/05/2018 00:30
 * @since JDK 1.8
 */
@Component
public class IMServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(IMServer.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup work = new NioEventLoopGroup();


    @Value("${im.server.port}")
    private int nettyPort;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 启动 cim server
     *
     * @return
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(nettyPort))
                //保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new IMServerInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            LOGGER.info("启动 cim server 成功");
        }
    }


    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        LOGGER.info("关闭 cim server 成功");
    }


    /**
     * 发送 Google Protocol 编码消息
     * @param sendMsgReqVO 消息
     */
    public boolean sendMsg(SendMsgReqVO sendMsgReqVO){
        NioSocketChannel socketChannel = SessionSocketHolder.get(sendMsgReqVO.getReceiveUserId());

        if (null == socketChannel) {
            //用户不在线时，需转换为离线消息，将消息保存到数据库，当用户再次上线时，向服务器拉取对应的离线消息
            String key = Constant.OFFLINE_MSG + sendMsgReqVO.getReceiveUserId();
            Map<String,Object> msg = new HashMap<>(2);
            msg.put("sendUserId",sendMsgReqVO.getUserId());
            msg.put("sendMsg",sendMsgReqVO.getMsg());
            redisUtil.set(key,msg);
            //确保消息及时性，设置过期时间60秒
            redisUtil.expire(key,60);
            LOGGER.info("客户端[" + sendMsgReqVO.getReceiveUserId() + "]不在线！已写入离线消息。");
            return false;
        }
        IMRequestProto.IMReqProtocol protocol = IMRequestProto.IMReqProtocol.newBuilder()
                .setRequestId(sendMsgReqVO.getUserId())
                .setReqMsg(sendMsgReqVO.getMsg())
                .setType(Constants.CommandType.MSG)
                .build();

        ChannelFuture future = socketChannel.writeAndFlush(protocol);
        future.addListener((ChannelFutureListener) channelFuture ->
                LOGGER.info("服务端手动发送 Google Protocol 成功={}", sendMsgReqVO.toString()));
        return true;
    }
}
