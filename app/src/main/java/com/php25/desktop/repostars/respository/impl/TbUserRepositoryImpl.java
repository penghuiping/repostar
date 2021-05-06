package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbUserRepository;
import com.php25.desktop.repostars.respository.entity.TbUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author penghuiping
 * @date 2020/9/23 14:49
 */
@Repository
public class TbUserRepositoryImpl extends BaseDbRepositoryImpl<TbUser, Long> implements TbUserRepository {

    public TbUserRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public TbUser findByLoginName(String loginName) {
        SqlParams sqlParams = Queries.of(dbType).from(TbUser.class).whereEq("login", loginName).single();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
    }


    @Override
    public TbUser findByLoginNameAndToken(String loginName, String token) {
        SqlParams sqlParams = Queries.of(dbType).from(TbUser.class).whereEq("login", loginName)
                .andEq("token", token).single();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
    }
}
