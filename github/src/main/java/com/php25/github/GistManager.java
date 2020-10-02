package com.php25.github;


import com.php25.github.dto.Gist;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/22 16:13
 */
public interface GistManager {

    /**
     * 获取某个用户在github中star的项目列表
     *
     * @param username github的用户名
     * @param token    github令牌
     * @param pageNum  当前第几页
     * @param pageSize 每页有多少条数据
     * @return starred项目列表
     */
    List<Gist> getAllStarredGist(String username, String token, Integer pageNum, Integer pageSize);
}
