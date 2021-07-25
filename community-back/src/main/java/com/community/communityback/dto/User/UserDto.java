package com.community.communityback.dto.User;

import com.community.communityback.model.ProviderType;
import com.community.communityback.model.RoleType;
import com.community.communityback.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;
    private String password;
    private String passwordCheck;


    public User defaultJoin(){
        
        if(password.equals(passwordCheck)){
            return User.builder().username(username).password(password).provider(ProviderType.DEFAULT).role(RoleType.ROLE_USER).build();
        }
        return null;
    }

    public User defaultLogin(){
        return User.builder().username(username).password(password).provider(ProviderType.DEFAULT).role(RoleType.ROLE_USER).build();
    }

}
