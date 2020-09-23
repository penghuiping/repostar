package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.Db;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbReposRepository;
import com.php25.desktop.repostars.respository.entity.TbRepos;

/**
 * @author penghuiping
 * @date 2020/9/23 14:49
 */
public class TbReposRepositoryImpl extends BaseDbRepositoryImpl<TbRepos, Long> implements TbReposRepository {

    public TbReposRepositoryImpl(Db db) {
        super(db);
    }
}
