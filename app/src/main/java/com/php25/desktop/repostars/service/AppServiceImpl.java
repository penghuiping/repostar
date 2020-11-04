package com.php25.desktop.repostars.service;

import com.google.common.collect.Lists;
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
import com.php25.desktop.repostars.respository.entity.TbGistRef;
import com.php25.desktop.repostars.respository.entity.TbGroup;
import com.php25.desktop.repostars.respository.entity.TbRepos;
import com.php25.desktop.repostars.respository.entity.TbUser;
import com.php25.desktop.repostars.service.dto.GistDto;
import com.php25.desktop.repostars.service.dto.GroupDto;
import com.php25.desktop.repostars.service.dto.ReposDto;
import com.php25.desktop.repostars.service.dto.UserDto;
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
import org.springframework.transaction.annotation.Transactional;

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
public class AppServiceImpl implements AppService {

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
    public UserDto login(String username, String token) {
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
                return new UserDto.UserDtoConverter().reverse().convert(tbUser);
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
                return new UserDto.UserDtoConverter().reverse().convert(tbUser);
            } else {
                //更新
                BeanUtils.copyProperties(user, tbUser);
                tbUser.setToken(token);
                tbUser.setLastModifiedTime(Instant.now().toEpochMilli());
                tbUser.setLastLoginTime(Instant.now().toEpochMilli());
                tbUser.setIsNew(false);
                tbUserRepository.save(tbUser);
                return new UserDto.UserDtoConverter().reverse().convert(tbUser);
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
    public List<ReposDto> getMyRepos(String username, String token) {
        List<TbRepos> result = tbReposRepository.findAllByLogin(username);
        var result1 = new ArrayList<ReposDto>();
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
        result1 = Lists.newArrayList(new ReposDto.ReposDtoConverter().reverse().convertAll(result));
        return result1;
    }

    @Override
    public DataGridPageDto<GistDto> getMyGistUngroup(String username, String searchKey, PageRequest request) {
        var result1 = tbGistRepository.findAllByLoginUnGroup(username, searchKey, request.getPageNumber(), request.getPageSize());
        var content1 = result1.getData();
        var content = new ArrayList<GistDto>();
        var result = new DataGridPageDto<GistDto>();
        result.setData(Lists.newArrayList(new GistDto.GistConverter().reverse().convertAll(content1)));
        result.setRecordsTotal(result1.getRecordsTotal());
        return result;
    }

    @Override
    public DataGridPageDto<GistDto> searchPage(String username, String token, String searchKey, PageRequest request) {
        Long count = tbGistRepository.countByLogin(username);
        if (null == count || count <= 0) {
            this.syncStarRepo0(username, token);
        }
        Page<TbGist> page = tbGistRepository.findAll(SearchParamBuilder.builder().append(SearchParam.of("login", Operator.EQ, username)).append(SearchParam.of("description", Operator.LIKE, "%" + searchKey + "%")), request);

        var gistDtos = new ArrayList<GistDto>();

        if (!page.getContent().isEmpty()) {
            var content = page.getContent();
            gistDtos = Lists.newArrayList(new GistDto.GistConverter().reverse().convertAll(content));
        }
        var dataGridPageDto = new DataGridPageDto<GistDto>();
        dataGridPageDto.setData(gistDtos);
        dataGridPageDto.setRecordsTotal(page.getTotalElements());
        return dataGridPageDto;
    }

    @Override
    public DataGridPageDto<GistDto> searchPageByGroupId(String username, String token, Long groupId, PageRequest request) {
        var result1 = tbGistRepository
                .findPageByLoginAndGroupId(username, groupId, request.getPageNumber(), request.getPageSize());
        var result = new DataGridPageDto<GistDto>();
        var tbGists = result1.getData();
        var converter = new GistDto.GistConverter();
        if (null != tbGists && !tbGists.isEmpty()) {
            result.setData(Lists.newArrayList(converter.reverse().convertAll(tbGists)));
        }
        return result;
    }

    @Override
    public List<GroupDto> getGroups(String username) {
        var group = tbGroupRepository.findByLogin(username);
        var converter = new GroupDto.GroupDtoConverter();
        if (null != group && !group.isEmpty()) {
            return Lists.newArrayList(converter.reverse().convertAll(group));
        } else {
            return null;
        }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOneGistIntoGroup(Long gistId, Long groupId) {
        TbGistRef tbGistRef = new TbGistRef();
        tbGistRef.setGistId(gistId);
        tbGistRef.setGroupId(groupId);
        var tbGistOptional = tbGistRepository.findById(gistId);
        var tbGist = tbGistOptional.get();
        tbGist.setIsJoinGroup(true);
        tbGist.setIsNew(false);
        tbGistRepository.save(tbGist);
        tbGistRefRepository.save(tbGistRef);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOneGistFromGroup(Long gistId, Long groupId) {
        TbGistRef tbGistRef = new TbGistRef();
        tbGistRef.setGistId(gistId);
        tbGistRef.setGroupId(groupId);
        tbGistRefRepository.delete(tbGistRef);

        var tbGistOptional = tbGistRepository.findById(gistId);
        var tbGist = tbGistOptional.get();
        tbGist.setIsJoinGroup(false);
        tbGist.setIsNew(false);
        tbGistRepository.save(tbGist);
    }

    @Override
    public GistDto findOneByFullName(String fullName) {
        var gist = tbGistRepository.findByFullName(fullName);
        return new GistDto.GistConverter().reverse().convert(gist);
    }


    @Override
    public ReposDto findReposByFullName(String fullName) {
        var repos = tbReposRepository.findByFullName(fullName);
        return new ReposDto.ReposDtoConverter().reverse().convert(repos);
    }

    @Override
    public void saveGist(GistDto gistDto) {
        var gist = new GistDto.GistConverter().convert(gistDto);
        gist.setIsNew(true);
        tbGistRepository.save(gist);
    }

    @Override
    public void updateGist(GistDto gistDto) {
        var gist = new GistDto.GistConverter().convert(gistDto);
        gist.setIsNew(false);
        tbGistRepository.save(gist);
    }


    @Override
    public void updateRepos(ReposDto reposDto) {
        var repos = new ReposDto.ReposDtoConverter().convert(reposDto);
        repos.setIsNew(false);
        tbReposRepository.save(repos);
    }
}
