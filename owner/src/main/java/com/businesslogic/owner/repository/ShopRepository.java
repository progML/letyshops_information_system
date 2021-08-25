package com.businesslogic.owner.repository;

import com.businesslogic.owner.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {

    //Получения нового айди для регистрации магазина
    @Query(nativeQuery = true, value = "SELECT id FROM owners ORDER BY ID DESC LIMIT 1")
    int findOwnerId();


    List<ShopEntity>findAllByOwnerId(int id);


    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE shop SET cashback =:cashback where id =:id and owner_id =:userId")
    void updateCashback(@Param("cashback") float cashback, @Param("id") int id, @Param("userId") int userId);
}
