package com.php25.desktop.repostars.respository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.desktop.repostars.respository.entity.TbGroup;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/10/10 21:12
 */
public interface TbGroupRepository extends BaseDbRepository<TbGroup, Long> {

    List<TbGroup> findByLogin(String login);

    TbGroup findByLoginAndGroupId(String login, Long groupId);
}
