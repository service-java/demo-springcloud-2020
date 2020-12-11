# README

- 芋道 Spring Boot 安全框架 Spring Security 入门
    - <http://www.iocoder.cn/Spring-Boot/Spring-Security>
    - http://www.iocoder.cn/Fight/user_login_auth_terms
    - http://www.iocoder.cn/Fight/Differences-secure-preauthorize-and-rolesallowed
    - http://www.iocoder.cn/Spring-Security/good-collection/
    - http://www.iocoder.cn/Fight/How-to-design-permission-management-module-schedule-structure/ @nice

```
spring-boot-starter-security

BCryptPasswordEncoder

UserDetailsServiceAutoConfiguration 
内存级别的InMemoryUserDetailsManager Bean 对象，提供认证的用户信息
默认会提供

DefaultLoginPageGeneratingFilter 

// 访问
http://127.0.0.1:9098/admin/demo
会被自动重定向到登录页

Cookie: JSESSIONID=943B0A3F997603673BB77F8FA6DFE38F

Using generated security password: 94850918-7739-4347-b4a1-7954f8c0f5ab

===
SecurityConfig

// 不用session的话
.csrf().disable() 
.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

@EnableGlobalMethodSecurity(prePostEnabled = true)

@PermitAll
@PreAuthorize("hasRole('ROLE_ADMIN')")
@PreAuthorize("hasRole('ROLE_NORMAL')")

Java Config 配置的权限，和注解配置的权限，两者是叠加的
所以@PermitAll的注解可能不能生效

authenticationTokenFilter
用于用户使用用户名与密码登录完成后，后续请求基于 JWT 来认证

重写 #authenticationManagerBean 方法
解决无法直接注入 AuthenticationManager 的问题


// 访问
http://127.0.0.1:9098/test/echo 可以直接访问
http://127.0.0.1:9098/test/home 需要登录

http://127.0.0.1:9098/test/admin 需要ADMIN身份
http://127.0.0.1:9098/test/normal 需要NORMAL身份

// 访问
http://127.0.0.1:9098/demo/echo 可以直接访问
http://127.0.0.1:9098/demo/home 需要登录

http://127.0.0.1:9098/demo/admin 需要ADMIN身份
http://127.0.0.1:9098/demo/normal 需要NORMAL身份
可能Role不正确而抛出403

===
缓存 LoginUser 到 Redis 缓存中

public void setUserAgent(LoginUser loginUser) {
    UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
    String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
    loginUser.setIpaddr(ip);
    loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
    loginUser.setBrowser(userAgent.getBrowser().getName());
    loginUser.setOs(userAgent.getOperatingSystem().getName());
}

缓存的 Redis Key 的统一前缀为 "login_tokens:" 

===
jjwt 库

JwtAuthenticationTokenFilter

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // <1> 获得当前 LoginUser
        LoginUser loginUser = tokenService.getLoginUser(request);
        // 如果存在 LoginUser ，并且未认证过
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            // <2> 校验 Token 有效性
            tokenService.verifyToken(loginUser);
            // <3> 创建 UsernamePasswordAuthenticationToken 对象，设置到 SecurityContextHolder 中
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // <4> 继续过滤器
        chain.doFilter(request, response);
    }
    
}
```

# Ruoyi-Vue

- https://gitee.com/y_project/RuoYi-Vue

```
安全代码分析 @balabala

===
// 各种处理器
AuthenticationEntryPointImpl
GlobalExceptionHandler
LogoutSuccessHandlerImpl

===
// 登录日志

实现异步存储日志到数据库中，提升 API 接口的性能
```

# Ruoyi-Vue-数据库表结构

- SysUser

```
salt
delFlag
```

- SysRole

```
roleId
roleName
roleKey
roleSort
dataScope 数据范围??
status
delFlag
```

- SysUserRole

```
userId
roleId
```

- SysMenu

```
menuId
menuName
parentId
parentName
orderNum 显示顺序
path 路由地址
component 组件路径
menuType 菜单类型(目录/菜单/按钮)
visible
perms 权限字符串 @eg system:user:query,system:user:add(可以逗号连接)
icon
```

- SysRoleMenu

```
roleId
menuId
```

# 参考
