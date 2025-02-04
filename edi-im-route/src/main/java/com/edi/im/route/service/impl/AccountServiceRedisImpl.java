package com.edi.im.route.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.edi.im.common.enums.StatusEnum;
import com.edi.im.common.exception.IMException;
import com.edi.im.common.pojo.IMUserInfo;
import com.edi.im.route.service.AccountService;
import com.edi.im.route.service.UserInfoCacheService;
import com.edi.im.route.vo.req.ChatReqVO;
import com.edi.im.route.vo.req.LoginReqVO;
import com.edi.im.route.vo.res.IMServerResVO;
import com.edi.im.route.vo.res.RegisterInfoResVO;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.edi.im.common.enums.StatusEnum.OFF_LINE;
import static com.edi.im.route.constant.Constant.ACCOUNT_PREFIX;
import static com.edi.im.route.constant.Constant.ROUTE_PREFIX;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2018/12/23 21:58
 * @since JDK 1.8
 */
@Service
public class AccountServiceRedisImpl implements AccountService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccountServiceRedisImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserInfoCacheService userInfoCacheService;

    @Autowired
    private OkHttpClient okHttpClient;

    private MediaType mediaType = MediaType.parse("application/json");

    @Override
    public RegisterInfoResVO register(RegisterInfoResVO info) {
        String key = ACCOUNT_PREFIX + info.getUserId();

        String name = redisTemplate.opsForValue().get(info.getUserName());
        if (null == name) {
            //为了方便查询，冗余一份
            redisTemplate.opsForValue().set(key, info.getUserName());
            redisTemplate.opsForValue().set(info.getUserName(), key);
        } else {
            long userId = Long.parseLong(name.split(":")[1]);
            info.setUserId(userId);
            info.setUserName(info.getUserName());
        }

        return info;
    }

    @Override
    public StatusEnum login(LoginReqVO loginReqVO) throws Exception {
        //再去Redis里查询
        String key = ACCOUNT_PREFIX + loginReqVO.getUserId();
        String userName = redisTemplate.opsForValue().get(key);
        if (null == userName) {
            return StatusEnum.ACCOUNT_NOT_MATCH;
        }

        if (!userName.equals(loginReqVO.getUserName())) {
            return StatusEnum.ACCOUNT_NOT_MATCH;
        }

        //登录成功，保存登录状态
        boolean status = userInfoCacheService.saveAndCheckUserLoginStatus(loginReqVO.getUserId());
        if (status == false) {
            //重复登录
            return StatusEnum.REPEAT_LOGIN;
        }

        return StatusEnum.SUCCESS;
    }

    @Override
    public void saveRouteInfo(LoginReqVO loginReqVO, String msg) throws Exception {
        String key = ROUTE_PREFIX + loginReqVO.getUserId();
        redisTemplate.opsForValue().set(key, msg);
    }

    @Override
    public Map<Long, IMServerResVO> loadRouteRelated() {

        Map<Long, IMServerResVO> routes = new HashMap<>(64);


        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        ScanOptions options = ScanOptions.scanOptions()
                .match(ROUTE_PREFIX + "*")
                .build();
        Cursor<byte[]> scan = connection.scan(options);

        while (scan.hasNext()) {
            byte[] next = scan.next();
            String key = new String(next, StandardCharsets.UTF_8);
            LOGGER.info("key={}", key);
            parseServerInfo(routes, key);
        }
        try {
            scan.close();
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }

        return routes;
    }

    @Override
    public IMServerResVO loadRouteRelatedByUserId(Long userId) {
        String value = redisTemplate.opsForValue().get(ROUTE_PREFIX + userId);

        if (value == null) {
            throw new IMException(OFF_LINE);
        }

        String[] server = value.split(":");
        IMServerResVO IMServerResVO = new IMServerResVO(server[0], Integer.parseInt(server[1]), Integer.parseInt(server[2]));
        return IMServerResVO;
    }

    private void parseServerInfo(Map<Long, IMServerResVO> routes, String key) {
        long userId = Long.valueOf(key.split(":")[1]);
        String value = redisTemplate.opsForValue().get(key);
        String[] server = value.split(":");
        IMServerResVO IMServerResVO = new IMServerResVO(server[0], Integer.parseInt(server[1]), Integer.parseInt(server[2]));
        routes.put(userId, IMServerResVO);
    }


    @Override
    public void pushMsg(String url, long sendUserId, ChatReqVO groupReqVO) throws Exception {
        IMUserInfo IMUserInfo = userInfoCacheService.loadUserInfoByUserId(sendUserId);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", IMUserInfo.getUserName() + ":【" + groupReqVO.getMsg() + "】");
        jsonObject.put("userId", groupReqVO.getUserId());
        jsonObject.put("receiveUserId", groupReqVO.getReceiveUserId());
        jsonObject.put("timeStamp", groupReqVO.getTimeStamp());
        RequestBody requestBody = RequestBody.create(mediaType, jsonObject.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        try {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } finally {
            response.body().close();
        }
    }

    @Override
    public void offLine(Long userId) throws Exception {

        //删除路由
        redisTemplate.delete(ROUTE_PREFIX + userId);
        //删除登录状态
        userInfoCacheService.removeLoginStatus(userId);
    }
}
