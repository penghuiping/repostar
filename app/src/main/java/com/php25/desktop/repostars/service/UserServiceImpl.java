package com.php25.desktop.repostars.service;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.core.exception.Exceptions;
import com.php25.common.core.mess.IdGenerator;
import com.php25.common.core.util.AssertUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.core.util.TimeUtil;
import com.php25.common.db.specification.Operator;
import com.php25.common.db.specification.SearchParam;
import com.php25.common.db.specification.SearchParamBuilder;
import com.php25.desktop.repostars.constant.AppError;
import com.php25.desktop.repostars.respository.TbGistRefRepository;
import com.php25.desktop.repostars.respository.TbGistRepository;
import com.php25.desktop.repostars.respository.TbGroupRepository;
import com.php25.desktop.repostars.respository.TbReposRepository;
import com.php25.desktop.repostars.respository.TbUserRepository;
import com.php25.desktop.repostars.respository.entity.TbGist;
import com.php25.desktop.repostars.respository.entity.TbGroup;
import com.php25.desktop.repostars.respository.entity.TbRepos;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.github.GistManager;
import com.php25.github.ReposManager;
import com.php25.github.UserManager;
import com.php25.github.dto.Gist;
import com.php25.github.dto.Repos;
import com.php25.github.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2020/9/29 13:41
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private GistManager gistManager;

    @Autowired
    private ReposManager reposManager;

    @Autowired
    private TbUserRepository tbUserRepository;

    @Autowired
    private TbGistRepository tbGistRepository;

    @Autowired
    private TbReposRepository tbReposRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private TbGroupRepository tbGroupRepository;

    @Autowired
    private TbGistRefRepository tbGistRefRepository;


    @Override
    public TbUser login(String username, String token) {
        AssertUtil.hasText(username, "用户名不能为空");
        AssertUtil.hasText(token, "令牌不能为空");

        TbUser tbUser = tbUserRepository.findByLoginName(username);
        if (tbUser != null) {
            if (!token.equals(tbUser.getToken())) {
                throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
            }

            //登入时长维持2小时
            if (LocalDateTime.now().minusHours(2).isBefore(TimeUtil.toLocalDateTime(new Date(tbUser.getLastLoginTime())))) {
                //更新
                tbUser.setToken(token);
                tbUser.setLastModifiedTime(Instant.now().toEpochMilli());
                tbUser.setLastLoginTime(Instant.now().toEpochMilli());
                tbUser.setIsNew(false);
                tbUserRepository.save(tbUser);
                return tbUser;
            }
        }


        User user = userManager.getUserInfo(token);
        if (user != null && StringUtil.isNotBlank(user.getLogin()) && user.getLogin().equals(username)) {
            if (tbUser == null) {
                //新增
                tbUser = new TbUser();
                tbUser.setId(idGenerator.getSnowflakeId());
                BeanUtils.copyProperties(user, tbUser);
                tbUser.setToken(token);
                tbUser.setCreateTime(Instant.now().toEpochMilli());
                tbUser.setLastModifiedTime(Instant.now().toEpochMilli());
                tbUser.setLastLoginTime(Instant.now().toEpochMilli());
                tbUser.setEnable(1);
                tbUser.setIsNew(true);
                tbUserRepository.save(tbUser);
                return tbUser;
            } else {
                //更新
                BeanUtils.copyProperties(user, tbUser);
                tbUser.setToken(token);
                tbUser.setLastModifiedTime(Instant.now().toEpochMilli());
                tbUser.setLastLoginTime(Instant.now().toEpochMilli());
                tbUser.setIsNew(false);
                tbUserRepository.save(tbUser);
                return tbUser;
            }
        } else {
            throw Exceptions.throwBusinessException(AppError.LOGIN_ERROR);
        }
    }

    @Override
    public void syncStarRepo(String username, String token) {
        executorService.execute(() -> {
            syncStarRepo0(username, token);
        });

    }

    private void syncStarRepo0(String username, String token) {
        //全量同步
        int pageNum = 1;
        int pageSize = 50;
        while (true) {
            List<Gist> gists = gistManager.getAllStarredGist(username, token, pageNum, pageSize);
            if (gists == null || gists.size() < pageSize) {
                break;
            }
            List<TbGist> tbGists = gists.stream().map(gist -> {
                TbGist tbGist = new TbGist();
                BeanUtils.copyProperties(gist, tbGist);
                tbGist.setEnable(1);
                return tbGist;
            }).collect(Collectors.toList());

            List<TbGist> createList = new ArrayList<>();
            List<TbGist> updateList = new ArrayList<>();

            for (TbGist tbGist : tbGists) {
                if (tbGistRepository.findById(tbGist.getId()).isPresent()) {
                    //更新
                    tbGist.setLastModifiedTime(Instant.now().toEpochMilli());
                    tbGist.setIsNew(false);
                    updateList.add(tbGist);
                } else {
                    //新增
                    tbGist.setCreateTime(Instant.now().toEpochMilli());
                    tbGist.setLastModifiedTime(Instant.now().toEpochMilli());
                    tbGist.setLogin(username);
                    tbGist.setIsNew(true);
                    createList.add(tbGist);
                }
            }

            if (!createList.isEmpty()) {
                tbGistRepository.saveAll(createList);
            }

            if (!updateList.isEmpty()) {
                tbGistRepository.saveAll(updateList);
            }
            pageNum = pageNum + 1;
        }
    }

    @Override
    public List<TbRepos> getMyRepos(String username, String token) {
        List<TbRepos> result = tbReposRepository.findAllByLogin(username);
        if (null == result || result.isEmpty()) {
            List<Repos> repos = reposManager.getReposList(token);
            if (null != repos && !repos.isEmpty()) {
                result = repos.stream().map(repos1 -> {
                    TbRepos tbRepos = new TbRepos();
                    BeanUtils.copyProperties(repos1, tbRepos);
                    tbRepos.setLogin(username);
                    tbRepos.setIsNew(true);
                    tbRepos.setCreateTime(System.currentTimeMillis());
                    tbRepos.setLastModifiedTime(System.currentTimeMillis());
                    tbRepos.setEnable(1);
                    return tbRepos;
                }).collect(Collectors.toList());
                tbReposRepository.saveAll(result);
            }
        }
        return result;
    }

    @Override
    public DataGridPageDto<TbGist> searchPage(String username, String token, String searchKey, PageRequest request) {
        Long count = tbGistRepository.countByLogin(username);
        if (null == count || count <= 0) {
            this.syncStarRepo0(username, token);
        }
        Page<TbGist> page = tbGistRepository.findAll(SearchParamBuilder.builder().append(SearchParam.of("login", Operator.EQ, username)).append(SearchParam.of("description", Operator.LIKE, "%" + searchKey + "%")), request);
        DataGridPageDto<TbGist> dataGridPageDto = new DataGridPageDto<>();
        dataGridPageDto.setData(page.getContent());
        dataGridPageDto.setRecordsTotal(page.getTotalElements());
        return dataGridPageDto;
    }

    @Override
    public DataGridPageDto<TbGist> searchPageByGroupId(String username, String token, Long groupId, PageRequest request) {
        var result = tbGistRepository
                .findPageByLoginAndGroupId(username, groupId, request.getPageNumber(), request.getPageSize());
        return result;
    }

    @Override
    public List<TbGroup> getGroups(String username) {
        return tbGroupRepository.findByLogin(username);
    }

    @Override
    public void addGroup(String username, String groupName) {
        TbGroup tbGroup = new TbGroup();
        tbGroup.setIsNew(true);
        tbGroup.setName(groupName);
        tbGroup.setLogin(username);
        tbGroup.setId(idGenerator.getSnowflakeId());
        tbGroupRepository.save(tbGroup);
    }

    @Override
    public void deleteGroup(String username, Long groupId) {
        Long count = tbGistRefRepository.countGistsByGroupId(groupId);
        if (count > 0) {
            throw Exceptions.throwBusinessException(AppError.GROUP_NOT_EMPTY_ERROR);
        }
        TbGroup tbGroup = tbGroupRepository.findByLoginAndGroupId(username, groupId);
        if (null != tbGroup) {
            tbGroupRepository.delete(tbGroup);
        }
    }

    @Override
    public void changeGroupName(String username, Long groupId, String groupName) {
        TbGroup tbGroup = tbGroupRepository.findByLoginAndGroupId(username, groupId);
        tbGroup.setName(groupName);
        tbGroup.setIsNew(false);
        tbGroupRepository.save(tbGroup);
    }
}
