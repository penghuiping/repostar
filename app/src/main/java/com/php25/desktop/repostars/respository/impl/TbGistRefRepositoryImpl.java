package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.desktop.repostars.respository.TbGistRefRepository;
import com.php25.desktop.repostars.respository.entity.TbGistRef;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author penghuiping
 * @date 2020/10/12 19:42
 */
@Repository
@RequiredArgsConstructor
public class TbGistRefRepositoryImpl implements TbGistRefRepository {

    private final DbType dbType;

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Long countGistsByGroupId(Long groupId) {
        SqlParams sqlParams = Queries.of(dbType).from(TbGistRef.class)
                .whereEq("group_id", groupId).count();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
    }

    @Override
    public void save(TbGistRef tbGistRef) {
        SqlParams sqlParams = Queries.of(dbType).from(TbGistRef.class).insert(tbGistRef);
        QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).insert(sqlParams);
    }

    @Override
    public void delete(TbGistRef tbGistRef) {
        SqlParams sqlParams = Queries.of(dbType).from(TbGistRef.class)
                .whereEq("gistId", tbGistRef.getGistId())
                .andEq("groupId", tbGistRef.getGroupId())
                .delete();
        QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).delete(sqlParams);
    }
}
