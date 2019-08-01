package com.security.security;

import com.security.entity.Resource;
import com.security.entity.Role;
import com.security.entity.RoleResourceRel;
import com.security.redis.RedisMap;
import com.security.redis.RedisMapFactory;
import com.security.service.RoleService;
import com.security.util.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Redis资源初始化
 */
@Service
@Slf4j
public class RedisResourceInitService {

    ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    RoleService roleService;

    private static RedisMap securityMap;
    private static RedisMapFactory redisFactory;

    @Autowired
    public void setRedisFactory(RedisMapFactory redisFactory) {
        RedisResourceInitService.redisFactory = redisFactory;
        RedisResourceInitService.securityMap = RedisMapFactory.getRedisMap(RedisConstant.HASH_KEY_SECURITY);
    }

    public void initAll() {
        loadResource();
    }

    public void loadResource() {
        /**
         * 应当是资源(格式：method,api)为key， 权限为value。 资源为URL-Method的Map， 权限就是那些以ROLE_为前缀的角色。
         * 一个资源可以由多个权限来访问。
         */
        Map<String, Collection<Object>> resourceMap = new HashMap<String, Collection<Object>>();

        /*
         * 在Web服务器启动时，提取系统中的所有权限。
         */
        List<Role> authorityList = roleService.findAll();

        /*
         * 取得所有权限名
         */
        for (Role auth : authorityList) {
            if (auth.getRoleResourceRels() == null) {
                continue;
            }

            String authName = auth.getName();
            /*
             * 然后，取得资源名：根据权限名 取得 资源名
             */
            for (RoleResourceRel rel : auth.getRoleResourceRels()) {
                /*
                 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要限通过该url为key提取出权集合，将权限增加到权限集合中
                 */
                Resource resource = rel.getResource();
                String resKey = (resource.getMethod() == null ? "" : resource.getMethod()) + "," + resource.getUrl();
                if (resourceMap.containsKey(resKey)) {
                    Collection<Object> value = resourceMap.get(resKey);
                    value.add(authName);
                } else {
                    Collection<Object> authNames = new ArrayList<Object>();
                    authNames.add(authName);
                    resourceMap.put(resKey, authNames);
                }
            }
        }
        /*
         * 更新redis缓存(method,url) 为key，roleName为value
         */
        securityMap.remove(RedisConstant.HASH_KEY_SECURITY_RESOURCE);
        securityMap.put(RedisConstant.HASH_KEY_SECURITY_RESOURCE, resourceMap);
        log.info("资源配置已更新");
    }

}
