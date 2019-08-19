package com.edi.im.server.config;

import com.edi.im.common.constant.Constants;
import com.edi.im.common.protocol.IMRequestProto;
import okhttp3.OkHttpClient;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/12/23 00:25
 * @since JDK 1.8
 */
@Configuration
public class BeanConfig {

    @Autowired
    private AppConfiguration appConfiguration ;

    @Bean
    public ZkClient buildZKClient(){
        return new ZkClient(appConfiguration.getZkAddr(), appConfiguration.getZkConnectTimeout());
    }

    /**
     * http client
     * @return okHttp
     */
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }


    /**
     * 创建心跳单例
     * @return
     */
    @Bean(value = "heartBeat")
    public IMRequestProto.IMReqProtocol heartBeat() {
        IMRequestProto.IMReqProtocol heart = IMRequestProto.IMReqProtocol.newBuilder()
                .setRequestId(0L)
                .setReqMsg("pong")
                .setType(Constants.CommandType.PING)
                .build();
        return heart;
    }
}
