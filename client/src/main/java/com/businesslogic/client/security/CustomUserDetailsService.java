package com.businesslogic.client.security;


import com.businesslogic.client.entity.ClientEntity;
import com.businesslogic.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {


  private final ClientService clientService;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ClientEntity clientEntity = clientService.findByLogin(username);
    return CustomUserDetails.fromUserEntityToCustomUserDetails(clientEntity);
  }
}
