package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.Db;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbGistRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/23 14:48
 */
@Repository
public class TbGistRepositoryImpl extends BaseDbRepositoryImpl<TbGist, Long> implements TbGistRepository {

    public TbGistRepositoryImpl(Db db) {
        super(db);
    }


    @Override
    public List<TbGist> findAllByLogin(String login) {
        return db.cndJdbc(TbGist.class).whereEq("login", login).select();
    }
}
