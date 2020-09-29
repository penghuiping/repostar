package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.Db;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbUserRepository;
import com.php25.desktop.repostars.respository.entity.TbUser;
import org.springframework.stereotype.Repository;

/**
 * @author penghuiping
 * @date 2020/9/23 14:49
 */
@Repository
public class TbUserRepositoryImpl extends BaseDbRepositoryImpl<TbUser, Long> implements TbUserRepository {

    public TbUserRepositoryImpl(Db db) {
        super(db);
    }


    @Override
    public TbUser findByLoginName(String loginName) {
        return db.cndJdbc(TbUser.class).whereEq("login", loginName).single();
    }
}
