package cn.jiiiiiin.data.orm.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 基础父类测试
 * </p>
 *
 * @author hubin
 * @author jiiiiiin
 * @since 2018-08-11
 */
@Data
@Accessors(chain = true)
public class BaseEntity <T extends Model> extends Model<T> {
    /**
     * 数据库表主键
     */
    private Long id;

    @Override
    protected Serializable pkVal() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
