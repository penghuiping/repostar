package com.php25.desktop.repostars.respository.entity;

import com.google.common.base.Objects;
import com.php25.common.db.cnd.annotation.Column;
import com.php25.common.db.cnd.annotation.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2020/10/10 21:06
 */
@Getter
@Setter
@Table("tb_group_gist")
public class TbGistRef {

    @Column("gist_id")
    private Long gistId;

    @Column("group_id")
    private Long groupId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbGistRef tbGistRef = (TbGistRef) o;
        return Objects.equal(gistId, tbGistRef.gistId) &&
                Objects.equal(groupId, tbGistRef.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gistId, groupId);
    }
}
