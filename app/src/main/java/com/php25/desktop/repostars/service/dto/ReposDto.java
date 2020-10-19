package com.php25.desktop.repostars.service.dto;

import com.google.common.base.Converter;
import com.php25.desktop.repostars.respository.entity.TbRepos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author penghuiping
 * @date 2020/10/19 16:33
 */
@Setter
@Getter
@NoArgsConstructor
public class ReposDto {

    private Long id;

    private String name;

    private String fullName;

    private String htmlUrl;

    private String description;

    private String language;

    private String readme;

    private Integer forks;

    private Integer watchers;

    private Long createTime;

    private Long lastModifiedTime;

    private String login;

    private Integer enable;

    public static class ReposDtoConverter extends Converter<ReposDto, TbRepos> {
        @Override
        protected TbRepos doForward(ReposDto reposDto) {
            TbRepos tbRepos = new TbRepos();
            BeanUtils.copyProperties(reposDto, tbRepos);
            return tbRepos;
        }

        @Override
        protected ReposDto doBackward(TbRepos tbRepos) {
            ReposDto reposDto = new ReposDto();
            BeanUtils.copyProperties(tbRepos, reposDto);
            return reposDto;
        }
    }


}
