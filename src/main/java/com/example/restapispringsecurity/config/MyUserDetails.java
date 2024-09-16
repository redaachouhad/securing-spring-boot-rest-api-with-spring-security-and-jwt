package com.example.restapispringsecurity.config;


import com.example.restapispringsecurity.models.Role;
import com.example.restapispringsecurity.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class MyUserDetails implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Role> roleNameList = user.getRoleSet();
        for(Role role:roleNameList){
            grantedAuthorities.add(new SimpleGrantedAuthority(String.valueOf(role.getRoleName())));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

