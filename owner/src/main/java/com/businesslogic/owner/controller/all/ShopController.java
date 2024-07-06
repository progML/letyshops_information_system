package com.businesslogic.owner.controller.all;



import com.businesslogic.owner.entity.DeviceEntity;
import com.businesslogic.owner.entity.ShopEntity;
import com.businesslogic.owner.service.DeviceService;
import com.businesslogic.owner.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopController {

  private final ShopService shopService;
  private final DeviceService deviceService;



    //просмотр магазинов
    @GetMapping("/shop")
    public ResponseEntity<List<ShopEntity>> getShops(){
        System.out.println("le");
        return new ResponseEntity<>(shopService.showShops(), HttpStatus.OK);
    }


    //Просмотр асартимента в определенном магазине
    @GetMapping("/shop/{id}")
    public ResponseEntity<List<DeviceEntity>> getDevice(@PathVariable int id){
        return new ResponseEntity<>(deviceService.showDevice(id), HttpStatus.OK);
    }

}
