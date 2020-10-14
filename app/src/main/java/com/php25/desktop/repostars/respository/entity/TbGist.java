package com.php25.desktop.repostars.respository.entity;

import com.php25.common.db.cnd.annotation.Column;
import com.php25.common.db.cnd.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

/**
 * @author penghuiping
 * @date 2020/9/23 14:28
 */
@Setter
@Getter
@Table("tb_gist")
public class TbGist implements Persistable<Long> {

    @Id
    private Long id;

    private String name;

    @Column("full_name")
    private String fullName;

    @Column("html_url")
    private String htmlUrl;

    private String description;

    private String language;

    private String readme;

    private Integer forks;

    private Integer watchers;

    @Column("create_time")
    private Long createTime;

    @Column("last_modified_time")
    private Long lastModifiedTime;

    private String login;

    private Integer enable;

    @Column("is_join_group")
    private Boolean isJoinGroup = false;

    @Transient
    private Boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
