package com.php25.desktop.repostars.respository.impl;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.util.PageUtil;
import com.php25.common.db.DbType;
import com.php25.common.db.Queries;
import com.php25.common.db.QueriesExecute;
import com.php25.common.db.core.sql.Query;
import com.php25.common.db.core.sql.SqlParams;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbGistRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbGistRef;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author penghuiping
 * @date 2020/9/23 14:48
 */
@Repository
public class TbGistRepositoryImpl extends BaseDbRepositoryImpl<TbGist, Long> implements TbGistRepository {


    public TbGistRepositoryImpl(JdbcTemplate jdbcTemplate, DbType dbType) {
        super(jdbcTemplate, dbType);
    }

    @Override
    public DataGridPageDto<TbGist> findPageByLoginAndGroupId(String login, Long groupId, Integer start, Integer pageSize) {
        var supplier = new Supplier<Query>() {
            @Override
            public Query get() {
                Query query = Queries.of(dbType).from(TbGist.class, "a").join(TbGistRef.class, "b")
                        .on("a.id", "b.gistId")
                        .whereEq("a.login", login)
                        .andEq("b.groupId", groupId);
                return query;
            }
        };
        var pageIndex = PageUtil.transToStartEnd(start, pageSize);
        SqlParams sqlParams = supplier.get().limit(pageIndex[0], pageSize).select();
        List<TbGist> content = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);

        SqlParams sqlParams1 = supplier.get().count();
        var count = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).count(sqlParams1);
        var result = new DataGridPageDto<TbGist>();
        result.setData(content);
        result.setRecordsTotal(count);
        return result;
    }

    @Override
    public Long countByLogin(String login) {
        SqlParams sqlParams = Queries.of(dbType).from(TbGist.class).whereEq("login", login).count();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).count(sqlParams);
    }

    @Override
    public DataGridPageDto<TbGist> findAllByLoginUnGroup(String login, String searchKey, Integer pageNum, Integer pageSize) {
        var pageIndex = PageUtil.transToStartEnd(pageNum, pageSize);
        var supplier = new Supplier<Query>() {
            @Override
            public Query get() {
                Query query = Queries.of(dbType).from(TbGist.class, "a")
                        .whereEq("a.login", login)
                        .andEq("a.isJoinGroup", false)
                        .and(Queries.group()
                                .andLike("a.description", "%" + searchKey + "%")
                                .orLike("a.name", "%" + searchKey + "%"));
                return query;
            }
        };
        SqlParams sqlParams = supplier.get()
                .limit(pageIndex[0], pageSize)
                .select();
        List<TbGist> tbGists = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).select(sqlParams);
        SqlParams sqlParams1 = supplier.get().count();
        Long count = QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).count(sqlParams1);
        DataGridPageDto<TbGist> result = new DataGridPageDto<>();
        result.setData(tbGists);
        result.setRecordsTotal(count);
        return result;
    }

    @Override
    public TbGist findByFullName(String fullName) {
        SqlParams sqlParams = Queries.of(dbType)
                .from(TbGist.class, "a").whereEq("a.fullName", fullName)
                .single();
        return QueriesExecute.of(dbType).singleJdbc().with(jdbcTemplate).single(sqlParams);
    }
}
