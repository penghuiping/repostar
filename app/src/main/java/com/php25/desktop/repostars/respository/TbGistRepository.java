package com.php25.desktop.repostars.respository;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.db.repository.BaseDbRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;

/**
 * @author penghuiping
 * @date 2020/9/23 14:37
 */
public interface TbGistRepository extends BaseDbRepository<TbGist, Long> {

    /**
     * 分页查询
     *
     * @param login    登入名
     * @param groupId  组id
     * @param pageNum  第几页
     * @param pageSize 每页多少条
     * @return gist列表
     */
    DataGridPageDto<TbGist> findPageByLoginAndGroupId(String login, Long groupId, Integer pageNum, Integer pageSize);

    /**
     * 查询某个用户的gist数
     *
     * @param login 登入名
     * @return gist数
     */
    Long countByLogin(String login);


    /**
     * 查询用户所有还没分组的gist
     *
     * @param login     用户名
     * @param searchKey 搜索关键字
     * @return gist列表
     */
    DataGridPageDto<TbGist> findAllByLoginUnGroup(String login, String searchKey, Integer pageNum, Integer pageSize);

    /**
     * 根据gist全面查询gist
     *
     * @param fullName 全面
     * @return gist
     */
    TbGist findByFullName(String fullName);
}
