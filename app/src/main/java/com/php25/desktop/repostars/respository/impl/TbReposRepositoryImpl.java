package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbReposRepository;
import com.php25.desktop.repostars.respository.entity.TbRepos;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/23 14:49
 */
@Repository
public class TbReposRepositoryImpl extends BaseDbRepositoryImpl<TbRepos, Long> implements TbReposRepository {

    public TbReposRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public List<TbRepos> findAllByLogin(String login) {
        SqlParams sqlParams = Queries.of(dbType).from(TbRepos.class).whereEq("login", login).select();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
    }


    @Override
    public TbRepos findByFullName(String fullName) {
        SqlParams sqlParams = Queries.of(dbType).from(TbRepos.class, "a").whereEq("a.fullName", fullName).single();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
    }
}
