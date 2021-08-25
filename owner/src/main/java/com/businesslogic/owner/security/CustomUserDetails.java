package com.businesslogic.owner.security;

import com.businesslogic.owner.entity.OwnerEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

  private String login;
  private String password;
  private Collection<? extends GrantedAuthority> grantedAuthorities;

  public static CustomUserDetails fromUserEntityToCustomUserDetails(OwnerEntity ownerEntity) {
    CustomUserDetails c = new CustomUserDetails();
    c.login = ownerEntity.getLogin();
    c.password = ownerEntity.getPassword();
    c.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_OWNER"));
    return c;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return login;
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
