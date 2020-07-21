package cn.jiiiiiin.security.core.validate.code;

import cn.jiiiiiin.security.core.dict.CommonConstants;
import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 验证码校验
 * <p>
 * 包括图形、短信验证码的校验逻辑
 * <p>
 * 被配置到ss框架过滤器链的{@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}之前执行
 *
 * @author jiiiiiin
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    final static Logger L = LoggerFactory.getLogger(ValidateCodeFilter.class);

    /**
     * 系统配置信息
     */
    @Autowired
    SecurityProperties securityProperties;

    /**
     * 验证码校验失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * 系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 验证请求url与配置的url是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 存放所有需要校验验证码的url
     * key:需要校验的url
     * value: 对应上对应的处理器（图形、短信）
     */
    private Map<String, ValidateCodeType> interceptorUrlsMap = new HashMap<>();

    /**
     * 初始化要拦截的url配置信息
     *
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 将系统中配置的需要校验验证码(图形和短信)的接口根据校验的类型放入map
        interceptorUrlsMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getValidate().getImageCode().getInterceptorUrls(), ValidateCodeType.IMAGE);
        interceptorUrlsMap.put(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getValidate().getSmsCode().getInterceptorUrls(), ValidateCodeType.SMS);
        L.info("验证码将会拦截的接口集合 {}", interceptorUrlsMap);
    }

    /**
     * 将系统中配置的需要校验验证码的URL根据校验的类型放入map
     *
     * @param urls
     * @param type
     */
    protected void addUrlToMap(Set<String> urls, ValidateCodeType type) {
        if (urls != null && !urls.isEmpty()) {
            for (String url : urls) {
                interceptorUrlsMap.put(url, type);
            }
        }
    }

    /**
     * 进行验证码的校验
     *
     * 通用校验器
     *
     * 如：拦截身份认证表单提交请求等配置在当前 {@link ValidateCodeFilter#interceptorUrlsMap} 中的请求
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final ValidateCodeType type = getValidateCodeType(request);
        if (type != null) {
            logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
            try {
                validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
                logger.info("验证码校验通过");
            } catch (ValidateCodeException exception) {
                logger.error("验证码校验失败", exception);
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     *
     * @param request
     * @return 返回null则标识当前请求不在需要进行验证码校验的配置范畴
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), CommonConstants.GET)) {
            final Set<String> urls = interceptorUrlsMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = interceptorUrlsMap.get(url);
                }
            }
        }
        return result;
    }
}
