package com.php25.github;

import com.php25.github.dto.Repos;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/23 13:33
 */
public interface ReposManager {

    /**
     * 获取个人的repos列表
     *
     * @param token github个人oauth token
     * @return repos列表
     */
    List<Repos> getReposList(String token);
}
