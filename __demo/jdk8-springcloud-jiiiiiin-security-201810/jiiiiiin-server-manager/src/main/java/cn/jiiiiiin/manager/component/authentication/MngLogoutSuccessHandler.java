/**
 *
 */
package cn.jiiiiiin.manager.component.authentication;

import cn.jiiiiiin.security.browser.logout.BrowserLogoutSuccessHandler;
import cn.jiiiiiin.security.browser.utils.HttpUtils;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import cn.jiiiiiin.security.core.support.SimpleResponse;
import cn.jiiiiiin.security.core.utils.HttpDataUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认的退出成功处理器，如果设置了`jiiiiiin.security.browser.signOutUrl`，则跳到配置的地址上，
 * 如果没配置，则返回json格式的响应。
 *
 * @author zhailiang
 * @author jiiiiiin
 */
@Slf4j
@Component("logoutSuccessHandler")
public class MngLogoutSuccessHandler extends BrowserLogoutSuccessHandler {

    public MngLogoutSuccessHandler(SecurityProperties securityProperties) {
        super(securityProperties.getBrowser().getSignOutUrl());
    }

    @Override
    protected void respJson(HttpServletResponse response) throws IOException {
        HttpDataUtil.respJson(response, objectMapper.writeValueAsString(R.ok("退出成功")));
    }

}
