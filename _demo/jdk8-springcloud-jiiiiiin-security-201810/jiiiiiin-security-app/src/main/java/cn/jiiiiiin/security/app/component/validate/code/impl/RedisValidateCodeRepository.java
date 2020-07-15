/**
 *
 */
package cn.jiiiiiin.security.app.component.validate.code.impl;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.validate.code.ValidateCodeException;
import cn.jiiiiiin.security.core.validate.code.ValidateCodeRepository;
import cn.jiiiiiin.security.core.validate.code.ValidateCodeType;
import cn.jiiiiiin.security.core.validate.code.entity.ValidateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 * <p>
 * 注意需要在请求头中携带：
 * String deviceId = request.getHeader("deviceId");
 * 客户端标识
 *
 * @author zhailiang
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 存储的时候设置了30分钟的超时时间，如果超过这个实现，数据将会被自动清除
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, 30, TimeUnit.MINUTES);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeRepository#get(org.
     * springframework.web.context.request.ServletWebRequest,
     * com.imooc.security.core.validate.code.ValidateCodeType)
     */
    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.imooc.security.core.validate.code.ValidateCodeRepository#remove(org.
     * springframework.web.context.request.ServletWebRequest,
     * com.imooc.security.core.validate.code.ValidateCodeType)
     */
    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getHeader(SecurityConstants.DEFAULT_PARAMETER_NAME_DEVICEID);
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }

}
