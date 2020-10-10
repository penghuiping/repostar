package com.php25.desktop.repostars.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbRepos;
import com.php25.desktop.repostars.respository.entity.TbUser;
import org.springframework.data.domain.PageRequest;

import java.util.List;

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
     * @param username 用户名
     * @param token    令牌
     */
    void syncStarRepo(String username, String token);

    /**
     * 获取用户自己的repos
     *
     * @param username 用户名
     * @param token    令牌
     * @return repos列表
     */
    List<TbRepos> getMyRepos(String username, String token);

    /**
     * 分页搜搜
     *
     * @param username  用户名
     * @param token     令牌
     * @param searchKey 搜索关键字
     * @param request   分页
     * @return 分页数据
     */
    DataGridPageDto<TbGist> searchPage(String username, String token, String searchKey, PageRequest request);
}
