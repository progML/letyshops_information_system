package com.businesslogic.owner.service;


import com.businesslogic.owner.entity.OwnerEntity;
import com.businesslogic.owner.entity.ShopEntity;
import com.businesslogic.owner.repository.OwnerRepository;
import com.businesslogic.owner.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class OwnerService {


    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopRepository shopRepository;

    //Поиск владельца
    public OwnerEntity findByLogin(String login) {
        return ownerRepository.findByLogin(login);
    }

    //Поиск по лоигину и паролю
    public OwnerEntity findByLoginAndPassword(String login, String password) {
        OwnerEntity ownerEntity = findByLogin(login);
        if (ownerEntity != null) {
            if (passwordEncoder.matches(password, ownerEntity.getPassword())) {
                return ownerEntity;
            }
        }
        return null;
    }

    //Сохранения пользователя
    public OwnerEntity saveUser(OwnerEntity ownerEntity) {
        ownerEntity.setPassword(passwordEncoder.encode(ownerEntity.getPassword()));
        return ownerRepository.save(ownerEntity);
    }


    //Поиск всех магазинов продовца
    public List<ShopEntity> findAllShopsByUserId(int id){
        return shopRepository.findAllByOwnerId(id);
    }


    //Обновления кэшбэка
    public void updateCashback(float cashback, int id, int userId) {
        shopRepository.updateCashback(cashback, id, userId);
    }
}
