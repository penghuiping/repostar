package com.php25.desktop.repostars.service;

import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.util.AssertUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.TimeUtil;
import com.php25.desktop.repostars.constant.AppError;
import com.php25.desktop.repostars.respository.TbGistRepository;
import com.php25.desktop.repostars.respository.TbUserRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.github.GistManager;
import com.php25.github.UserManager;
import com.php25.github.dto.Gist;
import com.php25.github.dto.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2020/9/29 13:41
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private GistManager gistManager;

    @Autowired
    private TbUserRepository tbUserRepository;

    @Autowired
    private TbGistRepository tbGistRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ExecutorService executorService;

    @Override
    public TbUser login(String username, String token) {
        AssertUtil.hasText(username, "用户名不能为空");
        AssertUtil.hasText(token, "令牌不能为空");

        TbUser tbUser = tbUserRepository.findByLoginName(username);
        if (tbUser != null) {
            if (!token.equals(tbUser.getToken())) {
                throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
            }

            //登入时长维持2小时
            if (LocalDateTime.now().minusHours(2).isBefore(TimeUtil.toLocalDateTime(new Date(tbUser.getLastLoginTime())))) {
                //更新
                tbUser.setToken(token);
                tbUser.setLastModifiedTime(Instant.now().toEpochMilli());
                tbUser.setLastLoginTime(Instant.now().toEpochMilli());
                tbUser.setIsNew(false);
                tbUserRepository.save(tbUser);
                return tbUser;
            }
        }


        User user = userManager.getUserInfo(token);
        if (user != null && StringUtil.isNotBlank(user.getLogin()) && user.getLogin().equals(username)) {
            if (tbUser == null) {
                //新增
                tbUser = new TbUser();
                tbUser.setId(idGenerator.getSnowflakeId());
                BeanUtils.copyProperties(user, tbUser);
                tbUser.setToken(token);
                tbUser.setCreateTime(Instant.now().toEpochMilli());
                tbUser.setLastModifiedTime(Instant.now().toEpochMilli());
                tbUser.setLastLoginTime(Instant.now().toEpochMilli());
                tbUser.setEnable(1);
                tbUser.setIsNew(true);
                tbUserRepository.save(tbUser);
                return tbUser;
            } else {
                //更新
                BeanUtils.copyProperties(user, tbUser);
                tbUser.setToken(token);
                tbUser.setLastModifiedTime(Instant.now().toEpochMilli());
                tbUser.setLastLoginTime(Instant.now().toEpochMilli());
                tbUser.setIsNew(false);
                tbUserRepository.save(tbUser);
                return tbUser;
            }
        } else {
            throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
        }
    }

    @Override
    public void syncStarRepo(String username, String token) {
        executorService.execute(() -> {
            //全量同步
            int pageNum = 1;
            int pageSize = 100;
            while (true) {
                List<Gist> gists = gistManager.getAllStarredGist(username, pageNum, pageSize);
                if (gists == null || gists.size() < 100) {
                    break;
                }
                List<TbGist> tbGists = gists.stream().map(gist -> {
                    TbGist tbGist = new TbGist();
                    BeanUtils.copyProperties(gist, tbGist);
                    return tbGist;
                }).collect(Collectors.toList());
                tbGistRepository.saveAll(tbGists);
                pageNum = pageNum + 1;
            }
        });

    }
}
