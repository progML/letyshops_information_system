package com.businesslogic.client.repository;


import com.businesslogic.client.entity.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface BasketRepository extends JpaRepository<BasketEntity, Integer> {


  //Проверка deviceId перед добовлением в корзину
  @Query(nativeQuery = true, value = "Select count (shop_id) from device WHERE id = :deviceId")
  int checkDeviceId(@Param("deviceId") int deviceId);

  //Просмотр товаров в корзине
  @Query(nativeQuery = true, value = "SELECT o.id, cost, name FROM device t INNER JOIN basket o ON t.id = o.device_id WHERE client_id =:userId")
  List<Object> findAllBasket(@Param("userId") int userId);


  //Подсчет стоимости товаров в корзине
  @Query(nativeQuery = true, value = "SELECT SUM (cost)  FROM device t INNER JOIN basket o ON t.id = o.device_id where client_id =:userId")
  float findSum(@Param("userId") int userId);


  //Просмотр кэшюэка в корзине
  @Query(nativeQuery = true, value = "SELECT sum(cashback*d.cost)/100 FROM shop s INNER JOIN device d ON s.id = d.shop_id INNER JOIN basket o ON d.id = o.device_id")
  float findCashback();


  //удаление из корзины покупок пользователя
  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "DELETE FROM basket WHERE client_id =:clientId")
  void deleteByUserEntity_Id(@Param("clientId") int userId);


  // удаление из карзины по айди
  @Transactional
  @Modifying
  @Query(nativeQuery = true, value = "DELETE FROM basket WHERE id =:Id")
  void deleteByBasketId(@Param("Id") int Id);



  // Поиск пользователя по id
  @Query(nativeQuery = true, value = "SELECT client_id FROM basket WHERE id =:Id")
  int findUserByBasketId(@Param("Id") int Id);





}
