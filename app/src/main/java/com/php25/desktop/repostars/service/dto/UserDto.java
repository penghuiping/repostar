package com.php25.desktop.repostars.service.dto;

import com.google.common.base.Converter;
import com.php25.desktop.repostars.respository.entity.TbUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author penghuiping
 * @date 2020/10/19 15:39
 */
@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String login;

    private String token;

    private String avatarUrl;

    private String htmlUrl;

    private String name;

    private String email;

    private Long createTime;

    private Long lastModifiedTime;

    private Long lastLoginTime;

    private Integer enable;

    public static class UserDtoConverter extends Converter<UserDto, TbUser> {
        @Override
        protected TbUser doForward(UserDto userDto) {
            TbUser tbUser = new TbUser();
            BeanUtils.copyProperties(userDto, tbUser);
            return tbUser;
        }

        @Override
        protected UserDto doBackward(TbUser tbUser) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(tbUser, userDto);
            return userDto;
        }
    }
}
