package cn.jiiiiiin.manager.controller;

import lombok.val;
import lombok.var;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * @author jiiiiiin
 */
@Controller
public class ErrorController {

    /**
     * https://www.mkyong.com/spring-security/customize-http-403-access-denied-page-in-spring-security/
     *
     * for 403 access denied page
     * @param user
     * @return
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accesssDenied(Principal user, HttpServletRequest request) {
        val model = new ModelAndView();
        if (user != null) {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "嗨，" + user.getName()
                    + "，您没有权限访问这个页面！");
        } else {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, "您没有访问此页面的权限！");
        }
        return "error/403";
    }


}
