package cn.jiiiiiin.module.common.mapper.mngauth;

import cn.jiiiiiin.module.common.entity.mngauth.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cache.decorators.LruCache;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 查询用户即其具有的role角色集合
     *
     * @param username
     * @return
     */
    Admin selectByUsername(String username);

    /**
     * 清空admin对应的role记录
     *
     * @param admin
     * @return (> = 0 - 标识成功 ， 否则标识失败)
     */
    int clearRelationRoleAdminRecord(Admin admin);

    /**
     * 批量插入admin关联的role记录
     * <p>
     * https://my.oschina.net/zjllovecode/blog/1818716
     *
     * @param admin
     * @return {@link com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper#retBool(Integer)} 批量插入是否成功（0-失败，1-成功）
     */
    int relationRole(Admin admin);
}
