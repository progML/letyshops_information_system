package com.businesslogic.client.service;


import com.businesslogic.client.dto.BasketEntityDto;
import com.businesslogic.client.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketService {

  private final BasketRepository basketRepository;
  @PersistenceContext
  private EntityManager entityManager;


  //Добавление в корзину
  @Transactional
  public void addOrder(BasketEntityDto basketEntityDto, int userId) {
    entityManager.createNativeQuery("INSERT INTO basket (device_id, client_id) values (?, ?)")
            .setParameter(1, basketEntityDto.getDevice_id())
            .setParameter(2, userId)
            .executeUpdate();
  }


  //Проверка deviceId перед добовлением в корзину
  public int checkDeviceId(int deviceId) {
    return basketRepository.checkDeviceId(deviceId);
  }



  //Удаление из корзины
  public void deleteBasket(int id) {
    basketRepository.deleteByBasketId(id);
  }

  //Удаление из корзины после оформлени заказа
  public void deleteAllBasketByUserId(int userId) {
    basketRepository.deleteByUserEntity_Id(userId);
  }


  //Просмотр товаров в корзине
  public List<Object> findAllById(int userId) {
    return basketRepository.findAllBasket(userId);
  }

  public int findUserByBasketId(int id){
    return basketRepository.findUserByBasketId(id);
  }




  public float findSum(int userId) {
    return basketRepository.findSum(userId);
  }

  public float findCashback() {
    return basketRepository.findCashback();
  }


}
