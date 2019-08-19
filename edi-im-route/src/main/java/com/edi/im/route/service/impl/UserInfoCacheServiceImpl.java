package com.edi.im.route.service.impl;

import com.edi.im.common.pojo.IMUserInfo;
import com.edi.im.route.service.UserInfoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.edi.im.route.constant.Constant.ACCOUNT_PREFIX;
import static com.edi.im.route.constant.Constant.LOGIN_STATUS_PREFIX;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/12/24 11:06
 * @since JDK 1.8
 */
@Service
public class UserInfoCacheServiceImpl implements UserInfoCacheService {

    /**
     * 本地缓存，为了防止内存撑爆，后期可换为 LRU。
     */
    private final static Map<Long, IMUserInfo> USER_INFO_MAP = new ConcurrentHashMap<>(64) ;

    @Autowired
    private RedisTemplate<String,String> redisTemplate ;

    @Override
    public IMUserInfo loadUserInfoByUserId(Long userId) {

        //优先从本地缓存获取
        IMUserInfo IMUserInfo = USER_INFO_MAP.get(userId);
        if (IMUserInfo != null){
            return IMUserInfo;
        }

        //load redis
        String sendUserName = redisTemplate.opsForValue().get(ACCOUNT_PREFIX + userId);
        if (sendUserName != null){
            IMUserInfo = new IMUserInfo(userId,sendUserName) ;
            USER_INFO_MAP.put(userId, IMUserInfo) ;
        }

        return IMUserInfo;
    }

    @Override
    public boolean saveAndCheckUserLoginStatus(Long userId) throws Exception {

        Long add = redisTemplate.opsForSet().add(LOGIN_STATUS_PREFIX, userId.toString());
        if (add == 0){
            return false ;
        }else {
            return true ;
        }
    }

    @Override
    public void removeLoginStatus(Long userId) throws Exception {
        redisTemplate.opsForSet().remove(LOGIN_STATUS_PREFIX,userId.toString()) ;
    }

    @Override
    public Set<IMUserInfo> onlineUser() {
        Set<IMUserInfo> set = null ;
        Set<String> members = redisTemplate.opsForSet().members(LOGIN_STATUS_PREFIX);
        for (String member : members) {
            if (set == null){
                set = new HashSet<>(64) ;
            }
            IMUserInfo IMUserInfo = loadUserInfoByUserId(Long.valueOf(member)) ;
            set.add(IMUserInfo) ;
        }

        return set;
    }

}
