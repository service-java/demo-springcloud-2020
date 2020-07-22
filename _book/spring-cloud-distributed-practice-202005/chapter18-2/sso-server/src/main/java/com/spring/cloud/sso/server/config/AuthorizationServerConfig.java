package com.spring.cloud.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**** imports ****/
/**
 * 认证服务器配置类
 */
@Configuration
@EnableAuthorizationServer // 驱动启动认证服务器
public class AuthorizationServerConfig 
        extends AuthorizationServerConfigurerAdapter { // ①

	private PasswordEncoder passwordEncoder = null; // 密码编码器

    // 创建PasswordEncoder对象
    @Bean(name="passwordEncoder")
    public PasswordEncoder initPasswordEncoder() {
    	if (passwordEncoder == null) {
    	    passwordEncoder = new BCryptPasswordEncoder();
    	}
    	return passwordEncoder;
    }

	// 配置客户端
	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
		    throws Exception { // ③
		passwordEncoder = initPasswordEncoder();
		clients.inMemory()
			// 注册一个客户端，设置名称
			.withClient("client1")
				// 设置客户端阴匙
				.secret(passwordEncoder.encode("client1-secrect"))
				// 对应客户端登录请求URI
				.redirectUris("http://localhost:7001/client1/login")
				// 授权方式
				.authorizedGrantTypes("authorization_code", 
						"password", "refresh_token")
				// 授权范围
				.scopes("all")
				// 是否自动同意，如果采用非自动同意，则需要用户手动授权
				.autoApprove(true)
			.and().withClient("client2")
			    .redirectUris("http://localhost:8001/client2/login")
				.secret(passwordEncoder.encode("client2-secrect"))
				.authorizedGrantTypes("authorization_code", 
						"password", "refresh_token")
				.scopes("all")
				.autoApprove(true);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception { // ④
		// 其他应用要访问认证服务器，也需要通过需要经过身份认证，获取到秘钥才能解析JWT
		// 这个配置就是要求其他应用在签名下访问认证服务器
		security.tokenKeyAccess("isAuthenticated()");
	}
	
	// Token存放仓库
	@Bean
	public TokenStore jwtTokenStore() { 
	    return new JwtTokenStore(jwtAccessTokenConverter());
	}

	/**
	 * JWT转换器，可以设置签名Key
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	    converter.setSigningKey("jwt-key"); // 设置签名key
	    return converter;
	}

	// 暴露签名端点
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
	        throws Exception {
	    endpoints.tokenStore(jwtTokenStore())
	        .accessTokenConverter(jwtAccessTokenConverter());
	}

}