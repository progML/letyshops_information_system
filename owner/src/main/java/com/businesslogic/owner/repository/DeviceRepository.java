package com.businesslogic.owner.repository;


import com.businesslogic.owner.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
    //Просмотр товаров в выбранном магазине
    List<DeviceEntity> findDeviceEntityByShopEntity_Id(int id);

    //Просмотр товаров продовца
    @Query(nativeQuery = true, value = "SELECT d.id, d.cost, d.name, s.name as call FROM device d INNER JOIN shop s ON d.shop_id = s.id WHERE owner_id =:userId")
    List<Object> findAllDeviceFromOwner(@Param("userId") int userId);

    //Изменение цены товара
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE device SET cost =:cost where id =:id")
    void updateDeviceCost(@Param("cost") float cost, @Param("id") int id);


}

