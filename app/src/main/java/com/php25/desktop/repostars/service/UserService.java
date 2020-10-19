package com.php25.desktop.repostars.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.desktop.repostars.service.dto.GistDto;
import com.php25.desktop.repostars.service.dto.GroupDto;
import com.php25.desktop.repostars.service.dto.ReposDto;
import com.php25.desktop.repostars.service.dto.UserDto;
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
    UserDto login(String username, String token);

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
    List<ReposDto> getMyRepos(String username, String token);

    /**
     * 获取用户自己的未分组的gist
     *
     * @param username  用户名
     * @param searchKey 搜索关键字
     * @return gist列表
     */
    DataGridPageDto<GistDto> getMyGistUngroup(String username, String searchKey, PageRequest request);

    /**
     * 分页搜索
     *
     * @param username  用户名
     * @param token     令牌
     * @param searchKey 搜索关键字
     * @param request   分页
     * @return 分页数据
     */
    DataGridPageDto<GistDto> searchPage(String username, String token, String searchKey, PageRequest request);


    /**
     * 分页搜索
     *
     * @param username 用户名
     * @param token    令牌
     * @param groupId  组id
     * @param request  分页请求
     * @return 分页数据
     */
    DataGridPageDto<GistDto> searchPageByGroupId(String username, String token, Long groupId, PageRequest request);

    /**
     * 获取用户创建的组
     *
     * @param username 用户名
     * @return 组列表
     */
    List<GroupDto> getGroups(String username);

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

    /**
     * 从组中删除一个gist
     *
     * @param gistId
     * @param groupId
     */
    void deleteOneGistFromGroup(Long gistId, Long groupId);

    /**
     * 根据gist全面查找gist
     *
     * @param fullName 全名
     * @return gist
     */
    GistDto findOneByFullName(String fullName);

    /**
     * 保存gist
     *
     * @param gistDto
     */
    void saveGist(GistDto gistDto);

    /**
     * 更新gist
     *
     * @param gistDto
     */
    void updateGist(GistDto gistDto);

}
