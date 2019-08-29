package cn.jiiiiiin.manager.component.authentication;


import cn.jiiiiiin.module.mngauth.component.MngUserDetails;
import cn.jiiiiiin.security.browser.component.authentication.BrowserAuthenticationSuccessHandler;
import cn.jiiiiiin.security.core.dict.CommonConstants;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理接口
 *
 * @author jiiiiiin
 */
@Slf4j
@Component("authenticationSuccessHandler")
public class MngAuthenticationSuccessHandler extends BrowserAuthenticationSuccessHandler {

    @Override
    protected void respJson(HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
        // 删除pwd
        val userDetails = (MngUserDetails)authentication.getPrincipal();
        // userDetails.getAdmin().setPassword(null).setId(null);
        response.getWriter().write(objectMapper.writeValueAsString(R.ok(authentication)));
    }
}
