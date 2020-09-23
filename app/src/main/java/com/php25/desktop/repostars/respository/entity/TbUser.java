package com.php25.desktop.repostars.respository.entity;

import com.php25.common.db.cnd.annotation.Column;
import com.php25.common.db.cnd.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2020/9/23 14:29
 */
@Setter
@Getter
@Table("tb_user")
public class TbUser implements Persistable<Long> {

    @Id
    private Long id;

    private String login;

    private String token;

    @Column("avatar_url")
    private String avatarUrl;

    @Column("html_url")
    private String htmlUrl;

    private String name;

    private String email;

    private LocalDateTime createTime;

    private LocalDateTime lastModifiedTime;

    private Integer enable;

    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
