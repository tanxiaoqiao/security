package com.security.security;

import com.security.security.login.UserDetailsServiceImpl;
import com.security.security.logout.LogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                //.loginPage("/login")
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
                .key("demoSecurity")
                .and()
                .logout()
                //默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/custom-logout")
                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("")
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
