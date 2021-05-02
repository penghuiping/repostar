package com.php25.desktop.repostars.respository.entity;

import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.util.Set;

/**
 * @author penghuiping
 * @date 2020/10/10 21:02
 */
@Setter
@Getter
@Table("tb_group")
public class TbGroup implements Persistable<Long> {

    @Id
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
