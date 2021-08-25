package com.businesslogic.owner.service;


import com.businesslogic.owner.entity.ShopEntity;
import com.businesslogic.owner.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ShopService {


    private final ShopRepository shopRepository;


    //просмотр всех магаинов
    public List<ShopEntity> showShops() {
        return shopRepository.findAll();
    }


    //добавление нового магазина
    public ShopEntity saveStore(ShopEntity shopEntity) {
        return shopRepository.save(shopEntity);
    }

}
