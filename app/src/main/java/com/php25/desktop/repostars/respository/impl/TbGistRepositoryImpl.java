package com.php25.desktop.repostars.respository.impl;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.util.PageUtil;
import com.php25.common.db.Db;
import com.php25.common.db.cnd.Query;
import com.php25.common.db.repository.BaseDbRepositoryImpl;
import com.php25.desktop.repostars.respository.TbGistRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbGistRef;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Supplier;

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
    public DataGridPageDto<TbGist> findPageByLoginAndGroupId(String login, Long groupId, Integer start, Integer pageSize) {

        var supplier = new Supplier<Query>() {
            @Override
            public Query get() {
                Query query = db.cndJdbc(TbGist.class, "a").join(TbGistRef.class, "b")
                        .on("a.id", "b.gistId")
                        .whereEq("a.login", login)
                        .andEq("b.groupId", groupId);
                return query;
            }
        };
        var pageIndex = PageUtil.transToStartEnd(start, pageSize);
        List<TbGist> content = supplier.get().limit(pageIndex[0], pageSize).select();
        var count = supplier.get().count();
        var result = new DataGridPageDto<TbGist>();
        result.setData(content);
        result.setRecordsTotal(count);
        return result;
    }

    @Override
    public Long countByLogin(String login) {
        return db.cndJdbc(TbGist.class).whereEq("login", login).count();
    }

    @Override
    public DataGridPageDto<TbGist> findAllByLoginUnGroup(String login, String searchKey, Integer pageNum, Integer pageSize) {
        var pageIndex = PageUtil.transToStartEnd(pageNum, pageSize);

        Query query = db.cndJdbc(TbGist.class, "a")
                .whereEq("a.login", login)
                .andEq("a.isJoinGroup", false).andLike("a.description", "%" + searchKey + "%");

        List<TbGist> tbGists = query
                .limit(pageIndex[0], pageSize)
                .select();
        Long count = query.count();

        DataGridPageDto<TbGist> result = new DataGridPageDto<>();
        result.setData(tbGists);
        result.setRecordsTotal(count);
        return result;
    }
}
