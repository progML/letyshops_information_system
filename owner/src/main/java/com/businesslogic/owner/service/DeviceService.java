package com.businesslogic.owner.service;


import com.businesslogic.owner.entity.DeviceEntity;
import com.businesslogic.owner.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {


    private final DeviceRepository deviceRepository;


    //Просмотр товаров в выбранном магазине
    public List<DeviceEntity> showDevice(int id) {
        return deviceRepository.findDeviceEntityByShopEntity_Id(id);
    }


    //Все магазины продавца
    public List<Object> showDeviceFromOwner(int id) {
        return deviceRepository.findAllDeviceFromOwner(id);
    }

    //изменени цены на товары
    public void updateDeviceCost(float cost, int id) {
        deviceRepository.updateDeviceCost(cost, id);
    }


}


