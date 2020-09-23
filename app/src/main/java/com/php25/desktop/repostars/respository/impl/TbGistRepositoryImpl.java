package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.Db;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbGistRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;

/**
 * @author penghuiping
 * @date 2020/9/23 14:48
 */
public class TbGistRepositoryImpl extends BaseDbRepositoryImpl<TbGist, Long> implements TbGistRepository {

    public TbGistRepositoryImpl(Db db) {
        super(db);
    }
}
