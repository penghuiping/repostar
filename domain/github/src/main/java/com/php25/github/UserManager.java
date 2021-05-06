package com.php25.github;


import com.php25.github.dto.User;

/**
 * @author penghuiping
 * @date 2020/9/22 17:23
 */
public interface UserManager {

    /**
     * 获取用户信息
     *
     * @param token github oauth token
     * @return 用户信息
     */
    User getUserInfo(String token);
}
