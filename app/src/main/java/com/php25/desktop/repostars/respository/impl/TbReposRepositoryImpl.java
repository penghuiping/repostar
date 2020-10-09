package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.Db;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbReposRepository;
import com.php25.desktop.repostars.respository.entity.TbRepos;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/23 14:49
 */
@Repository
public class TbReposRepositoryImpl extends BaseDbRepositoryImpl<TbRepos, Long> implements TbReposRepository {

    public TbReposRepositoryImpl(Db db) {
        super(db);
    }

    @Override
    public List<TbRepos> findAllByLogin(String login) {
        return db.cndJdbc(TbRepos.class).whereEq("login", login).select();
    }
}
