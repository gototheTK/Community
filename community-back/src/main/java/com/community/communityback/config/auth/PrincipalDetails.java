package com.community.communityback.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import com.community.communityback.model.RoleType;
import com.community.communityback.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


@Data
public class PrincipalDetails implements UserDetails{

    private User user;

    public PrincipalDetails(User user){
        this.user = user;
    }

    public RoleType getAuthority() {
        return user.getRole();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        
        authorities.add(()->{

            return user.getRole().toString();});
        
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    
}
