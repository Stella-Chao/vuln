package com.tf.eye.service.impl;

import com.tf.eye.model.domain.User;
import com.tf.eye.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    private UserService userService;

    /**
     * 校验，根据用户名定位用户
     * @param username 标识需要其数据的用户的用户名
     * @return 核心用户信息，一个完全填充的用户记录
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录，用户名：{}", username);
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在！");
        }
        return null;
    }
}
