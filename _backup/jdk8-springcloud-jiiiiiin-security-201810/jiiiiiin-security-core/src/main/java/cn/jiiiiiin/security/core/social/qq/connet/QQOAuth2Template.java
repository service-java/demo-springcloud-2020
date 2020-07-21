/**
 *
 */
package cn.jiiiiiin.security.core.social.qq.connet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * 参考：http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
 * 根据qq要求进行框架的微调，以进行 access token令牌的解析
 * <p>
 * 提供给{@link QQServiceProvider}使用
 *
 * @author zhailiang
 * @author jiiiiiin
 * @see AccessGrant 是对access token，访问令牌的一个封装
 */
public class QQOAuth2Template extends OAuth2Template {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        // 设置该属性，social才会在基类的authenticateClient方法帮我们设置第三方应用的client_id和client_secret
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 解析qq特殊格式的响应数据
     * <p>
     * 类似：
     * access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
     *
     * @see org.springframework.social.oauth2.OAuth2Template#postForAccessGrant(java.lang.String, org.springframework.util.MultiValueMap)
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        // 覆盖默认实现，得到String类型的响应数据
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
        try {
            logger.info("获取accessToke的响应：" + responseStr);
            String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
            String accessToken = StringUtils.substringAfterLast(items[0], "=");
            Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
            String refreshToken = StringUtils.substringAfterLast(items[2], "=");
            return new AccessGrant(accessToken, null, refreshToken, expiresIn);
        } catch (RestClientException e) {
            throw e;
        } catch (NumberFormatException e) {
            throw new RestClientException("获取qq accessToke 出错[" + responseStr + "]", e);
        }
    }

    /**
     * 这里很可能出现异常，因为第三方返回的响应数据，social解析不出来的时候
     * 发送获取授权码的默认实现：extractAccessGrant(getRestTemplate().postForObject(accessTokenUrl, parameters, Map.class));
     * 标明框架期望得到的是一个json数据【RestTemplate】
     * 如果返回的数据内容类型非`application/json`或者数据不是json格式，就会出问题，qq返回的就不是这样的数据结构
     * access_token=FE04************************CCE2&expires_in=7776000&refresh_token=88E4************************BE14
     * http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
     * 解决方式就是自己实现template，添加相应的数据格式解析器
     *
     * @see org.springframework.social.oauth2.OAuth2Template#createRestTemplate()
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
