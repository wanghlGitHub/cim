package com.edi.im.common.protocol;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/8/1 12:24
 * @since JDK 1.8
 */
public class ProtocolUtil {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        IMRequestProto.IMReqProtocol protocol = IMRequestProto.IMReqProtocol.newBuilder()
                .setRequestId(123L)
                .setReqMsg("你好啊")
                .build();

        byte[] encode = encode(protocol);

        IMRequestProto.IMReqProtocol parseFrom = decode(encode);

        System.out.println(protocol.toString());
        System.out.println(protocol.toString().equals(parseFrom.toString()));
    }

    /**
     * 编码
     * @param protocol
     * @return
     */
    public static byte[] encode(IMRequestProto.IMReqProtocol protocol){
        return protocol.toByteArray() ;
    }

    /**
     * 解码
     * @param bytes
     * @return
     * @throws InvalidProtocolBufferException
     */
    public static IMRequestProto.IMReqProtocol decode(byte[] bytes) throws InvalidProtocolBufferException {
        return IMRequestProto.IMReqProtocol.parseFrom(bytes);
    }
}
