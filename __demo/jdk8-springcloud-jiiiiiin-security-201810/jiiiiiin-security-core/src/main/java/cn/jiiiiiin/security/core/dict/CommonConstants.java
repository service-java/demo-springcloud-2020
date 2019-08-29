package cn.jiiiiiin.security.core.dict;

/**
 * 一些非配置的通用常量
 *
 * @author jiiiiiin
 */
public class CommonConstants {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    /**
     * token 授权需要传递到请求头的 Authorization字段前缀
     */
    public static final String DEFAULT_HEADER_NAME_AUTHORIZATION_PRIFIX = "bearer ";

    /**
     * 判断请求头Accept字段是期望返回json的标记字段
     */
    public static final String ACCEPT_JSON_PREFIX = "application/json";
}
