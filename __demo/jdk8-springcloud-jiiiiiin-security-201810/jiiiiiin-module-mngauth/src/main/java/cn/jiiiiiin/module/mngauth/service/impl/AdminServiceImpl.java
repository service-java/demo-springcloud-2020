package cn.jiiiiiin.module.mngauth.service.impl;

import cn.jiiiiiin.module.common.entity.mngauth.Admin;
import cn.jiiiiiin.module.common.entity.mngauth.Resource;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceChannelEnum;
import cn.jiiiiiin.module.common.mapper.mngauth.AdminMapper;
import cn.jiiiiiin.module.common.mapper.mngauth.ResourceMapper;
import cn.jiiiiiin.module.mngauth.service.IAdminService;
import cn.jiiiiiin.security.rbac.component.dict.RbacDict;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jiiiiiin
 * @since 2018-09-27
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public Admin signInByUsername(@NonNull String username, ResourceChannelEnum channel) {
        log.debug("登录用户名 {}", username);
        val res = adminMapper.selectByUsername(username);
        res.getRoles().forEach(role -> role.setResources(resourceMapper.selectByRoleId(role.getId(), channel)));
        return res;
    }

    @Override
    public boolean relationRole(Admin admin) {
        adminMapper.clearRelationRoleAdminRecord(admin);
        return SqlHelper.retBool(adminMapper.relationRole(admin));
    }
}
