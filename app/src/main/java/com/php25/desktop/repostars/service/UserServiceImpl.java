package com.php25.desktop.repostars.service;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.util.AssertUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.desktop.repostars.constant.AppError;
import com.php25.github.UserManager;
import com.php25.github.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author penghuiping
 * @date 2020/9/29 13:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Override
    public void login(String username, String token) {
        AssertUtil.hasText(username, "用户名不能为空");
        AssertUtil.hasText(token, "令牌不能为空");
        User user = userManager.getUserInfo(token);
        if (user != null && StringUtil.isNotBlank(user.getLogin()) && user.getLogin().equals(username)) {
        } else {
            throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
        }
    }
}
