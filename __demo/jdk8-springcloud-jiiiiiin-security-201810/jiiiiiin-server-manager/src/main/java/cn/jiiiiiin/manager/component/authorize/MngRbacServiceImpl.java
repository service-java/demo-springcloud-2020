/**
 *
 */
package cn.jiiiiiin.manager.component.authorize;

import cn.jiiiiiin.module.mngauth.component.MngUserDetails;
import cn.jiiiiiin.security.rbac.component.dict.RbacDict;
import cn.jiiiiiin.security.rbac.component.service.RbacService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhailiang
 */
@Slf4j
@Component("rbacService")
public class MngRbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 需要读取用户所拥有权限的所有URL
     * 通过用户标识-》用户角色-》角色拥有的资源
     *
     * @param request        请求信息
     * @param authentication 身份认证信息
     * @return
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        // principal即系统的UserDetailsService返回的用户标识对象，如果没有通过认证则是一个匿名的字符串
        val principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof MngUserDetails) {
            val admin = ((MngUserDetails) principal).getAdmin();
            val roles = admin.getRoles();
            val hasAdminRole = roles.stream().anyMatch(role -> role.getAuthorityName().equals(RbacDict.ROLE_ADMIN_AUTHORITY_NAME));
            if (hasAdminRole) {
                hasPermission = true;
            } else {
                val reqURI = request.getRequestURI();
                val reqMethod = request.getMethod();
                // 读取用户所拥有权限的所有URL
                // 通过用户标识-》用户角色-》角色拥有的资源

                log.debug("hasPermission 服务 判断 {} {} {}", reqURI, reqMethod, roles);
                val iterator = roles.iterator();
                while (iterator.hasNext()) {
                    val role = iterator.next();
                    // 进行权限匹配
                    val res = role.getResources()
                            .stream()
                            .anyMatch(resource -> antPathMatcher.match(resource.getUrl(), reqURI)
                                    && request.getMethod().equalsIgnoreCase(reqMethod));
                    if (res) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        return hasPermission;
    }

}
