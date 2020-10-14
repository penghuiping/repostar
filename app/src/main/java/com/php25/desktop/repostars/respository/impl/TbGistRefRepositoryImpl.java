package com.php25.desktop.repostars.respository.impl;

import com.php25.common.db.Db;
import com.php25.desktop.repostars.respository.TbGistRefRepository;
import com.php25.desktop.repostars.respository.entity.TbGistRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author penghuiping
 * @date 2020/10/12 19:42
 */
@Repository
public class TbGistRefRepositoryImpl implements TbGistRefRepository {

    @Autowired
    private Db db;

    @Override
    public Long countGistsByGroupId(Long groupId) {
        return db.cndJdbc(TbGistRef.class).whereEq("group_id", groupId).count();
    }

    @Override
    public void save(TbGistRef tbGistRef) {
        db.cndJdbc(TbGistRef.class).insert(tbGistRef);
    }

    @Override
    public void delete(TbGistRef tbGistRef) {
        db.cndJdbc(TbGistRef.class)
                .whereEq("gistId", tbGistRef.getGistId())
                .andEq("groupId", tbGistRef.getGroupId())
                .delete();
    }
}
