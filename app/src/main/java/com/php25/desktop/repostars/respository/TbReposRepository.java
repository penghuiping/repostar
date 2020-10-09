package com.php25.desktop.repostars.respository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.desktop.repostars.respository.entity.TbRepos;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/23 14:38
 */
public interface TbReposRepository extends BaseDbRepository<TbRepos, Long> {

    /**
     * 根据登入用户名 获取次用户所有的repos
     *
     * @param login 登入用户名
     * @return 此用户所有的repos
     */
    List<TbRepos> findAllByLogin(String login);
}
