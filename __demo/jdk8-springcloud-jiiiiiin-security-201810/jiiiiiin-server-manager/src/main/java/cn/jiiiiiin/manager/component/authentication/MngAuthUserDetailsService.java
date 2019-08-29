package cn.jiiiiiin.manager.component.authentication;

import cn.jiiiiiin.manager.properties.ManagerProperties;
import cn.jiiiiiin.module.common.dto.mngauth.Menu;
import cn.jiiiiiin.module.common.entity.mngauth.Admin;
import cn.jiiiiiin.module.common.entity.mngauth.Resource;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceChannelEnum;
import cn.jiiiiiin.module.common.enums.mngauth.ResourceTypeEnum;
import cn.jiiiiiin.module.mngauth.component.MngUserDetails;
import cn.jiiiiiin.module.mngauth.service.IAdminService;
import cn.jiiiiiin.security.core.authentication.AuthenticationBeanConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * 内管`UserDetailsService`
 * <p>
 * 配置：{@link AuthenticationBeanConfig#userDetailsService()}
 *
 * @author jiiiiiin
 */
@Component
@Slf4j
public class MngAuthUserDetailsService implements UserDetailsService {

    @Autowired
    private IAdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据channel去获取登录用户的权限信息
        val optionalAdmin = adminService.signInByUsername(username, ResourceChannelEnum.MNG);
        if (optionalAdmin == null) {
            throw new UsernameNotFoundException("用户名密码不符");
        } else {
            _formatResource(optionalAdmin);
            return new MngUserDetails(optionalAdmin);
        }
    }

    /**
     * 格式化前端需要的菜单
     *
     * @param optionalAdmin
     */
    private void _formatResource(Admin optionalAdmin) {
        val roles = optionalAdmin.getRoles();
        // 过滤菜单和授权资源
        val menuResources = new HashSet<Resource>();
        val authorizeResources = new HashSet<Resource>();
        roles.forEach(item -> item.getResources().forEach(resource -> {
            if (resource.getType().equals(ResourceTypeEnum.MENU)) {
                menuResources.add(resource);
            } else {
                authorizeResources.add(resource);
            }
        }));

        val menus = new ArrayList<Menu>();
        menuResources.forEach(resource -> {
            // 过滤一级节点
            if (resource.getPid().equals(Resource.IS_ROOT_MENU)) {
                val node = Menu.parserMenu(resource, menuResources);
                menus.add(node);
            }
        });
        menus.sort(Comparator.comparingInt(Menu::getNum));
        optionalAdmin.setAuthorizeResources(authorizeResources);
        optionalAdmin.setMenus(menus);
        log.debug("响应的授权资源 {}", authorizeResources);
        log.debug("响应的菜单 {}", menus);
    }

}
