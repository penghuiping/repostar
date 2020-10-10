package com.php25.desktop.repostars.respository.entity;

import com.php25.common.db.cnd.annotation.Column;
import com.php25.common.db.cnd.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.util.Set;

/**
 * @author penghuiping
 * @date 2020/10/10 21:02
 */
@Setter
@Getter
@Table("t_group")
public class TbGroup implements Persistable<Long> {

    private Long id;

    private String name;

    @Transient
    private Boolean isNew;

    private String login;

    @Column("group_id")
    private Set<TbGistRef> gists;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
