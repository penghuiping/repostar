package com.php25.desktop.repostars.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbGroup;
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
     * 获取用户自己的未分组的gist
     *
     * @param username  用户名
     * @param searchKey 搜索关键字
     * @return gist列表
     */
    List<TbGist> getMyGistUngroup(String username, String searchKey);

    /**
     * 分页搜索
     *
     * @param username  用户名
     * @param token     令牌
     * @param searchKey 搜索关键字
     * @param request   分页
     * @return 分页数据
     */
    DataGridPageDto<TbGist> searchPage(String username, String token, String searchKey, PageRequest request);


    /**
     * 分页搜索
     *
     * @param username 用户名
     * @param token    令牌
     * @param groupId  组id
     * @param request  分页请求
     * @return 分页数据
     */
    DataGridPageDto<TbGist> searchPageByGroupId(String username, String token, Long groupId, PageRequest request);

    /**
     * 获取用户创建的组
     *
     * @param username 用户名
     * @return 组列表
     */
    List<TbGroup> getGroups(String username);

    /**
     * 创建组
     *
     * @param username  用户名
     * @param groupName 组名
     */
    void addGroup(String username, String groupName);

    /**
     * 删除组
     *
     * @param username 用户名
     * @param groupId  组id
     */
    void deleteGroup(String username, Long groupId);

    /**
     * 修改组名
     *
     * @param username  用户名
     * @param groupId   组id
     * @param groupName 组名
     */
    void changeGroupName(String username, Long groupId, String groupName);

    /**
     * 往组中加入一个gist
     *
     * @param gistId
     * @param groupId
     */
    void addOneGistIntoGroup(Long gistId, Long groupId);
}
