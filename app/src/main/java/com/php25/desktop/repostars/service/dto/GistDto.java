package com.php25.desktop.repostars.service.dto;

import com.google.common.base.Converter;
import com.php25.desktop.repostars.respository.entity.TbGist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author penghuiping
 * @date 2020/10/19 15:05
 */

@Setter
@Getter
@NoArgsConstructor
public class GistDto {

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

    private Boolean isJoinGroup = false;


    public static class GistConverter extends Converter<GistDto, TbGist> {
        @Override
        protected TbGist doForward(GistDto gistDto) {
            TbGist tbGist = new TbGist();
            BeanUtils.copyProperties(gistDto, tbGist);
            return tbGist;
        }

        @Override
        protected GistDto doBackward(TbGist tbGist) {
            GistDto gistDto = new GistDto();
            BeanUtils.copyProperties(tbGist, gistDto);
            return gistDto;
        }
    }
}
