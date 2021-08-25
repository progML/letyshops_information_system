package com.businesslogic.owner.security;


import com.businesslogic.owner.entity.OwnerEntity;
import com.businesslogic.owner.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {


  private final OwnerService ownerService;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    OwnerEntity ownerEntity = ownerService.findByLogin(username);
    return CustomUserDetails.fromUserEntityToCustomUserDetails(ownerEntity);
  }
}
