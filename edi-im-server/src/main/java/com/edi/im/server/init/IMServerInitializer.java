package com.edi.im.server.init;

import com.edi.im.common.protocol.IMRequestProto;
import com.edi.im.server.handle.IMServerHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 17/05/2018 18:51
 * @since JDK 1.8
 */
public class IMServerInitializer extends ChannelInitializer<Channel> {

    private final IMServerHandle IMServerHandle = new IMServerHandle();

    @Override
    protected void initChannel(Channel ch) throws Exception {

        ch.pipeline()
                //11 秒没有向客户端发送消息就发生心跳
                .addLast(new IdleStateHandler(11, 0, 0))
                // google Protobuf 编解码
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(IMRequestProto.IMReqProtocol.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(IMServerHandle);
    }
}
