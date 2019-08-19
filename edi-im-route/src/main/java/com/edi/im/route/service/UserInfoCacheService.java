package com.edi.im.route.service;

import com.edi.im.common.pojo.IMUserInfo;

import java.util.Set;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 2018/12/24 11:06
 * @since JDK 1.8
 */
public interface UserInfoCacheService {

    /**
     * 通过 userID 获取用户信息
     * @param userId 用户唯一 ID
     * @return
     * @throws Exception
     */
    IMUserInfo loadUserInfoByUserId(Long userId) ;

    /**
     * 保存和检查用户登录情况
     * @param userId userId 用户唯一 ID
     * @return true 为可以登录 false 为已经登录
     * @throws Exception
     */
    boolean saveAndCheckUserLoginStatus(Long userId) throws Exception ;

    /**
     * 清除用户的登录状态
     * @param userId
     * @throws Exception
     */
    void removeLoginStatus(Long userId) throws Exception ;


    /**
     *
     * @return 获取所有在线用户
     */
    Set<IMUserInfo> onlineUser() ;
}
