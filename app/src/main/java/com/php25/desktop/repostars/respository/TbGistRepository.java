package com.php25.desktop.repostars.respository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;

import java.util.List;

/**
 * @author penghuiping
 * @date 2020/9/23 14:37
 */
public interface TbGistRepository extends BaseDbRepository<TbGist, Long> {

    List<TbGist> findAllByLogin(String login);
}
