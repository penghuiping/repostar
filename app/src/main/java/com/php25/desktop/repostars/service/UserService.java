package com.php25.desktop.repostars.service;

import com.php25.desktop.repostars.respository.entity.TbUser;

/**
 * @author penghuiping
 * @date 2020/9/29 13:40
 */
public interface UserService {

    /**
     * 根据用户名与令牌进行登入
     *
     * @param username 用户名
     * @param token    令牌
     */
    TbUser login(String username, String token);

    /**
     * 同步用户的stars到本地
     *
     * @param token
     */
    void syncStarRepo(String username, String token);
}
