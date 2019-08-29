package cn.jiiiiiin.module.common.mapper.mngauth;

import cn.jiiiiiin.module.common.entity.mngauth.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 批量插入role管理的resource
     *
     * @param role
     * @return
     */
    int relationResource(Role role);
}
