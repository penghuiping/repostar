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
 * @date 2020/9/23 14:28
 */
@Setter
@Getter
@Table("tb_repos")
public class TbRepos implements Persistable<Long> {

    @Id
    private Long id;

    private String name;

    @Column("full_name")
    private String fullName;

    @Column("html_url")
    private String htmlUrl;

    private String description;

    private String language;

    private Integer forks;

    private Integer watchers;

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
