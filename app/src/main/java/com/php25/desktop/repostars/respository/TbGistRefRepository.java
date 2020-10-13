package com.php25.desktop.repostars.respository;

import com.php25.desktop.repostars.respository.entity.TbGistRef;

/**
 * @author penghuiping
 * @date 2020/10/12 19:40
 */
public interface TbGistRefRepository {

    Long countGistsByGroupId(Long groupId);

    void save(TbGistRef tbGistRef);
}
