package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.Db;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbGroupRepository;
import com.php25.desktop.repostars.respository.entity.TbGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/10/10 21:12
 */
@Repository
public class TbGroupRepositoryImpl extends BaseDbRepositoryImpl<TbGroup, Long> implements TbGroupRepository {

    public TbGroupRepositoryImpl(Db db) {
        super(db);
    }

    @Override
    public List<TbGroup> findByLogin(String login) {
        return db.cndJdbc(TbGroup.class).whereEq("login", login).select();
    }
}
