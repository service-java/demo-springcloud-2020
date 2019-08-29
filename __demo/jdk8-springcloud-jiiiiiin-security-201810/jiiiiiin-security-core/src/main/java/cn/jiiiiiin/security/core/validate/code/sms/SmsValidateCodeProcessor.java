/**
 * 
 */
package cn.jiiiiiin.security.core.validate.code.sms;

import cn.jiiiiiin.security.core.validate.code.entity.ValidateCode;
import cn.jiiiiiin.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import static cn.jiiiiiin.security.core.dict.SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
import static cn.jiiiiiin.security.core.dict.SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;

/**
 * 短信验证码处理器
 * 
 * @author zhailiang
 *
 */
@Component
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		final String mobilePhone = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), DEFAULT_PARAMETER_NAME_MOBILE);
		smsCodeSender.send(mobilePhone, validateCode.getCode());
	}

}
