package com.php25.desktop.repostars.respository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.desktop.repostars.respository.entity.TbUser;

/**
 * @author penghuiping
 * @date 2020/9/23 14:37
 */
public interface TbUserRepository extends BaseDbRepository<TbUser, Long> {

    TbUser findByLoginName(String loginName);

    TbUser findByLoginNameAndToken(String loginName, String token);
}
