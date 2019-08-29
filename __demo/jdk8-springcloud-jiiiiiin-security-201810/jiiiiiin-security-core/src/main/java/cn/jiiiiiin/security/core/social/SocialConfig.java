/**
 *
 */
package cn.jiiiiiin.security.core.social;

import cn.jiiiiiin.security.core.dict.SecurityConstants;
import cn.jiiiiiin.security.core.properties.SecurityProperties;
import cn.jiiiiiin.security.core.social.support.CustomSpringSocialConfigurer;
import cn.jiiiiiin.security.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.*;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

/**
 * 社交登录配置主类
 *
 * @author zhailiang
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 业务系统进行自动授权注册的组件
     */
    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * 提供{@link UsersConnectionRepository}帮助我们将授权数据插入框架定义的表中
     *
     * @param connectionFactoryLocator 查找可用的{@link org.springframework.social.connect.ConnectionFactory}
     *                                 因为每个授权服务提供商都会有自己的一个ConnectionFactory在系统中，需要根据条件去查询不同的工厂
     * @see org.springframework.social.config.annotation.SocialConfigurerAdapter#getUsersConnectionRepository(org.springframework.social.connect.ConnectionFactoryLocator)
     * <p>
     * {@link JdbcUsersConnectionRepository}构建时候传递的第三个参数，帮我们对插入数据库的数据进行加解密，可用使用{@link Encryptors#standard(CharSequence, CharSequence)}来保证安全性
     * <p>
     * 建表语句在{@link JdbcUsersConnectionRepository}类定义的源代码包下
     * <p>
     * create table UserConnection (
     * # 业务系统用户id
     * # {@link org.springframework.social.security.SocialUserDetailsService#loadUserByUserId(String)}
     * # spring social 根据上面这个接口传递当前表的userId字段去获取业务系统的用户信息
     * userId varchar(255) not null,
     * # 服务提供商id
     * providerId varchar(255) not null,
     * # 服务提供商对应用户的id
     * providerUserId varchar(255),
     * <p>
     * # 上面3个字段将我们业务系统和服务提供商的两组用户信息关联
     * <p>
     * # 等级
     * `rank` int not null,
     * # 用户名 通过ApiAdapter设置
     * displayName varchar(255),
     * # 用户主页 通过ApiAdapter设置
     * profileUrl varchar(512),
     * # 用户名头像url 通过ApiAdapter设置
     * imageUrl varchar(512),
     * # 用户token令牌
     * accessToken varchar(512) not null,
     * secret varchar(512),
     * refreshToken varchar(512),
     * expireTime bigint,
     * primary key (userId, providerId, providerUserId));
     * <p>
     * create unique index UserConnectionRank on UserConnection(userId, providerId, `rank`);
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        // !Encryptors.noOpText()为调试使用，不做加解密
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        // 添加表前缀
        repository.setTablePrefix(SecurityConstants.SOCIAL_TABLE_PREFIX);
        if (connectionSignUp != null) {
            // 如果期望让用户进行第三方授权登录之后，自动帮用户创建业务系统的用户记录，完成登录，而无需跳转到下面这个接口进行注册，请看：
            // @see org.springframework.social.security.SocialAuthenticationProvider#toUserId 去获取userIds的方法，在{@link org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository#findUserIdsWithConnection}中通过注入{@link ConnectionSignUp}完成
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
     *
     * @return
     * @see SpringSocialConfigurer 可以参考默认实现
     */
    @Bean
    public SpringSocialConfigurer socialSecurityConfig() {
        final String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        final CustomSpringSocialConfigurer configurer = new CustomSpringSocialConfigurer(filterProcessesUrl);
        // 配置自定义注册页面接口，当第三方授权获取user detail在业务系统找不到的时候默认调整到该页面
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        // 注入后处理器，以便app模式（标准）下授权登录能够完成，动态设置signupUrl根据模块（app/browser）
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    /**
     * 用来处理注册流程的工具类
     * https://coding.imooc.com/lesson/134.html#mid=6891
     * <p>
     * 让我们在注册页面，能拿到授权信息，用户注册成功之后
     * <p>
     * 能将业务系统的id交给social进行处理，存储绑定关系到数据库
     *
     * @param connectionFactoryLocator 基类已经帮我们注入，用来获取{@link org.springframework.social.connect.ConnectionFactory}
     * @return
     * @see SocialConfig#getUsersConnectionRepository
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
