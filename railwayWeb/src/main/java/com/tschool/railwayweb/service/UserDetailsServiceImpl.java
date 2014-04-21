package com.tschool.railwayweb.service;

import com.tschool.railwayweb.dao.UserDAO;
import com.tschool.railwayweb.model.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsServiceImpl")
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        
        com.tschool.railwayweb.model.User user = userDAO.getUser(login);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Role> roleList = user.getRoleList();
        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        UserDetails authenticatedUser = new User(
                user.getLogin(),
                user.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
        );
        return authenticatedUser;
    }
}
