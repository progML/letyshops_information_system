package com.businesslogic.client.service;


import com.businesslogic.client.entity.ClientEntity;
import com.businesslogic.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {


  private final ClientRepository clientRepository;
  private final PasswordEncoder passwordEncoder;

  //Поиск пользователя
  public ClientEntity findByLogin(String login){
    return clientRepository.findByLogin(login);
  }

  //Поиск по лоигину и паролю
  public ClientEntity findByLoginAndPassword(String login, String password) {
    ClientEntity clientEntity = findByLogin(login);
    if (clientEntity != null) {
      if (passwordEncoder.matches(password, clientEntity.getPassword())) {
        return clientEntity;
      }
    }
    return null;
  }

  //Сохранения пользователя
  public ClientEntity saveUser(ClientEntity clientEntity) {
    clientEntity.setPassword(passwordEncoder.encode(clientEntity.getPassword()));
    return clientRepository.save(clientEntity);
  }
}
