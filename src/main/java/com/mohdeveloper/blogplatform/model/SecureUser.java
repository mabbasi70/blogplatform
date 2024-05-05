package com.mohdeveloper.blogplatform.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohdeveloper.blogplatform.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class SecureUser implements UserDetails, OAuth2User{

    @Serial
    private static final long serialVersionUID = 1L;
    private final User user;
    private Map<String, Object> attributes;


    @JsonCreator
    public SecureUser(@JsonProperty("user") User user){
       this.user = user;
   }

   public SecureUser(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
   }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()-> "READ");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getEmail(){return  user.getEmail();}

    public void setPasswordToNull(){
       user.setPassword(null);
    }
    public Long getId(){return user.getId();}

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

    @Override
    public String getName() {
        return user.getEmail() != null ? user.getEmail() : "defaultUsername";
    }
}
