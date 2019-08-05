package com.security.security;

import com.security.security.login.LoginFailureHandler;
import com.security.security.login.LoginSuccessHandler;
import com.security.security.login.UserDetailsServiceImpl;
import com.security.security.logout.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;

/**
 * @Author: Kris
 * @Date: 2019-08-01  09:45
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public ResourceAccessDecisionManager getResourceAccessDecisionManager() {
        return new ResourceAccessDecisionManager();
    }

    @Bean
    public SecurityMetadataSource getSecurityMetadataSource() {
        return new SecurityMetadataSource();
    }

    @Bean
    public FilterSecurityInterceptor resourceFilterSecurityInterceptor() {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        // 设定每次请求都检查
        filterSecurityInterceptor.setObserveOncePerRequest(false);
        // 访问决策管理器
        filterSecurityInterceptor.setAccessDecisionManager(getResourceAccessDecisionManager());
        // resource数据源，服务启动时获取权限-资源关系
        filterSecurityInterceptor.setSecurityMetadataSource(getSecurityMetadataSource());
        return filterSecurityInterceptor;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .failureHandler(new LoginFailureHandler())
                .successHandler(new LoginSuccessHandler())
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password");

        http.securityContext().securityContextRepository(new HttpSessionSecurityContextRepository());

        /** ------------  关闭cache，避免session分布式存储到redis中时导致反序列化问题  ---------- **/
        http.requestCache().requestCache(new NullRequestCache());

        /**-------------  其他安全配置  ------------**/
        http.headers()
                .xssProtection().xssProtectionEnabled(false).and()  // 开启XSS攻击防护
                .frameOptions().sameOrigin();  // 允许页面加载同源下的iframe
        http.sessionManagement()
                //一个用户最多可以10个地点登陆
                .maximumSessions(3)
                .sessionRegistry(getSessionRegistry())
                .expiredUrl("/");
        http.csrf().disable();
        http.cors(); // 开启Cors跨域,会自动加载CorsFilter
        http.rememberMe();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("sessionRegistry")
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }
}
