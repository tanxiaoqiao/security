/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.security.security;

import com.security.redis.RedisMap;
import com.security.redis.RedisMapFactory;
import com.security.service.RoleService;
import com.security.util.RedisConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 资源数据源，初始化资源-权限之间的关系
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Logger logger = LoggerFactory.getLogger(SecurityMetadataSource.class);

    private static RedisMapFactory redisFactory;
    private static RedisMap securityMap;

    @Autowired
    public void setRedisFactory(RedisMapFactory redisFactory) {
        SecurityMetadataSource.redisFactory = redisFactory;
        securityMap = RedisMapFactory.getRedisMap(RedisConstant.HASH_KEY_SECURITY);
    }

    @Autowired
    RedisResourceInitService configService;
    @Autowired
    RoleService roleService;

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // 初始化 权限-资源关系表
        configService.initAll();

        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        Object resourceMap = securityMap.get(RedisConstant.HASH_KEY_SECURITY_RESOURCE);
        if (resourceMap != null) {
            //格式是method，URL为key，rolename为value
            Map<String, Collection<Object>> map = (Map<String, Collection<Object>>) resourceMap;
            for (Map.Entry<String, Collection<Object>> entry : map.entrySet()) {
                entry.getValue().forEach(
                        authName -> {
                            allAttributes.add(new SecurityConfig(authName.toString()));
                        });
            }
        }

        return allAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        Set<ConfigAttribute> configs = new HashSet<>();

        Object resourceMap = securityMap.get(RedisConstant.HASH_KEY_SECURITY_RESOURCE);

        if (resourceMap != null) {
            final HttpServletRequest request = ((FilterInvocation) object).getRequest();
            Map<String, Collection<Object>> map = (Map<String, Collection<Object>>) resourceMap;

            // 访问url所需要的角色集合


            for (Map.Entry<String, Collection<Object>> entry : map.entrySet()) {
                /**
                 * 做URL-Method匹配,并且区分URL中的大小写
                 */
                try {
                    String[] res = entry.getKey().split(",");
                    AntPathRequestMatcher um = new AntPathRequestMatcher(res[1], res[0], false);
                    //请求的url与redis缓存匹配上
                    if (um.matches(request)) {

                        entry.getValue().forEach(
                                authName -> {
                                    configs.add(new SecurityConfig(authName.toString()));
                                }
                        );

                    }
                } catch (Exception e) {
                    logger.error("资源解析异常：" + e.getMessage());
                } finally {
                }

            }
            // 超级管理员可以访问任何API
            configs.add(new SecurityConfig("ADMIN"));
        }
        return configs;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
