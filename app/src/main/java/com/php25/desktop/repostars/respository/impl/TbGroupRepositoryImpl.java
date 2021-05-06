package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbGroupRepository;
import com.php25.desktop.repostars.respository.entity.TbGroup;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/10/10 21:12
 */
@Repository
public class TbGroupRepositoryImpl extends BaseDbRepositoryImpl<TbGroup, Long> implements TbGroupRepository {

    public TbGroupRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public List<TbGroup> findByLogin(String login) {
        SqlParams sqlParams = Queries.of(dbType).from(TbGroup.class).whereEq("login", login).select();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
    }

    @Override
    public TbGroup findByLoginAndGroupId(String login, Long groupId) {
        SqlParams sqlParams = Queries.of(dbType).from(TbGroup.class)
                .whereEq("login", login).andEq("id", groupId).single();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
    }
}
