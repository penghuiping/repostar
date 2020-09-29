package com.php25.desktop.repostars.service;

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
    void login(String username, String token);
}
