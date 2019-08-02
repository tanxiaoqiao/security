package com.security.security;

import com.security.entity.User;
import com.security.model.UserDto;
import com.security.security.login.UserDetailsServiceImpl;
import com.security.service.UserService;
import com.security.util.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Session管理
 */
@Service
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private static SessionRegistry sessionRegistry;
    private static UserDetailsServiceImpl userDetailsService;
    private static UserService userService;


    @Autowired
    public void setSessionRegistry(SessionRegistry sessionRegistry) {
        SessionManager.sessionRegistry = sessionRegistry;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        SessionManager.userDetailsService = userDetailsService;
    }


    /**
     * 注册用户到内存，模拟登陆
     *
     * @param user
     */
    public static void registerUser(User user) {
        String username = user.getUsername();
        if (username != null) {
            // 检查用户名是否和当前登录用户相同，如果相同则跳过避免重复登录
            UserDto currentUser = SessionUtils.getCurrentUser();
            if (currentUser != null && username.equals(currentUser.getUsername())) {
                logger.debug("User:" + username + " has been in system, skip login...");
                return;
            }

            // 向Spring Security中注册用户
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            // 注册session
            sessionRegistry.registerNewSession(SessionUtils.getRequest().getSession().getId(), user);
        }
    }



    /**
     * 注销当前用户
     */
    public static void expireCurrentUser() {
        User principle = SessionUtils.getPrinciple();
        List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principle, false);
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        if (allSessions != null) {
            for (int i = 0; i < allSessions.size(); i++) {
                SessionInformation sessionInformation = allSessions.get(i);
                sessionInformation.getSessionId();
                sessionInformation.expireNow();
            }
        }
    }
}
