/**
 *
 */
package cn.jiiiiiin.security.rbac.component.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhailiang
 * @author jiiiiiin
 */
public interface RbacService {

    /**
     * 判断当前请求是否能通过鉴权
     *
     * @param request        请求信息
     * @param authentication 身份认证信息
     * @return
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
