package cn.jiiiiiin.security.core.properties;

import cn.jiiiiiin.security.core.validate.code.ValidateCodeController;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * 短信验证码配置类
 * ![](https://ws1.sinaimg.cn/large/0069RVTdgy1fuo9h1z6mrj30t30eu0tl.jpg)
 *
 * @author jiiiiiin
 */
@Setter
@Getter
public class SmsCodeProperties {

    /**
     * 验证码生成位数
     */
    private String length = "4";
    /**
     * 验证码有效期
     * 请求时候会检测是否在请求参数中携带 {@link ValidateCodeController#REQ_EXPIRE_IN}
     * 如果携带则覆盖配置项
     */
    private int expireIn = 60;

    /**
     * 需要进行拦截的接口
     *
     * 默认会添加 {@link cn.jiiiiiin.security.core.validate.code.ValidateCodeFilter#LOGIN_PROCESSING_URL} 身份认证接口
     */
    private Set<String> interceptorUrls = new HashSet<>();

}
