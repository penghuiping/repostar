package com.php25.desktop.repostars.service.dto;

import com.google.common.base.Converter;
import com.php25.desktop.repostars.respository.entity.TbGistRef;
import com.php25.desktop.repostars.respository.entity.TbGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Set;

/**
 * @author penghuiping
 * @date 2020/10/19 16:27
 */
@Setter
@Getter
@NoArgsConstructor
public class GroupDto {

    private Long id;

    private String name;

    private String login;

    private Set<TbGistRef> gists;

    public static class GroupDtoConverter extends Converter<GroupDto, TbGroup> {
        @Override
        protected TbGroup doForward(GroupDto groupDto) {
            TbGroup tbGroup = new TbGroup();
            BeanUtils.copyProperties(groupDto, tbGroup);
            return tbGroup;
        }

        @Override
        protected GroupDto doBackward(TbGroup tbGroup) {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(tbGroup, groupDto);
            return groupDto;
        }
    }
}
