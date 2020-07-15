/**
 *
 */
package cn.jiiiiiin.security.app.component.authentication;

import cn.jiiiiiin.security.core.dict.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * APP环境下认证成功处理器
 *
 * @author zhailiang
 */
@Slf4j
public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper objectMapper;

    /**
     * lei {@link org.springframework.security.core.userdetails.UserDetailsService} 用来生成 {@link ClientDetails}
     */
	@Autowired
	private ClientDetailsService clientDetailsService;

    /**
     * 认证服务器令牌服务
     */
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;


    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.web.authentication.
     * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse,
     * org.springframework.security.core.Authentication)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.debug("身份认证（登录 Token）成功");

        // 解析client id
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        // 创建 clientDetails
        final ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }

        // 创建 tokenRequest
        // 不同的oauth模式需要传递的参数不一致
        // params1为空，因为不需要再去创建`authentication`
        // scope不需要进行校验，因为是提供自身使用
        // grantType标识为自定义的
        final TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        final OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        final OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        final OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        respJson(response, token);
    }

    private void respJson(HttpServletResponse response, OAuth2AccessToken token) throws IOException {
        response.setContentType(CommonConstants.CONTENT_TYPE_JSON);
        response.getWriter().write(objectMapper.writeValueAsString(token));
    }

    /**
     * 解析请求头中的`Authorization参数`
     *
     * @param header
     * @param request
     * @return
     * @throws IOException
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        // [clientId, clientSecret]
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

}
