package com.businesslogic.owner.controller.owner;


import com.businesslogic.owner.dto.CashbackDto;
import com.businesslogic.owner.dto.DeviceDto;
import com.businesslogic.owner.entity.ShopEntity;
import com.businesslogic.owner.service.DeviceService;
import com.businesslogic.owner.service.OrderService;
import com.businesslogic.owner.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/owner")
public class OwnerController {


    private final OwnerService ownerService;
    private final DeviceService deviceService;
    private final OrderService orderService;
    private int userId;


    //просмотр магазинов продовца
    @GetMapping("/shop")
    public ResponseEntity<List<ShopEntity>> showShops(Principal principal) {
        userId = ownerService.findByLogin(principal.getName()).getId();
        return new ResponseEntity<>(ownerService.findAllShopsByUserId(userId), HttpStatus.OK);
    }

    //Обновления кэшбэка
    @PostMapping("/shop/cashback")
    public ResponseEntity<String> changeCashback(@RequestBody CashbackDto cashbackDto, Principal principal) {
        userId = ownerService.findByLogin(principal.getName()).getId();
        ownerService.updateCashback(cashbackDto.getCashback(), cashbackDto.getId(), userId);
        return new ResponseEntity<>("Кэшбэк успешно изменен", HttpStatus.OK);
    }

    //просомотр девайсов продовца
    @GetMapping("/shop/device")
    public ResponseEntity<Object> findAllDevice(Principal principal) {
        userId = ownerService.findByLogin(principal.getName()).getId();
        return new ResponseEntity<>(deviceService.showDeviceFromOwner(userId), HttpStatus.OK);
    }

    //изменение цены на девайс
    @PostMapping("/device/change")
    public ResponseEntity<String> changeDeviceCost(@RequestBody DeviceDto deviceDto) {
        deviceService.updateDeviceCost(deviceDto.getCost(), deviceDto.getId());
        return new ResponseEntity<>("Цена успешна изменена", HttpStatus.OK);
    }

    //Просмотр заказов от пользователей
    @GetMapping("/orders")
    public ResponseEntity<Object> findAllOrders(Principal principal) {
        userId = ownerService.findByLogin(principal.getName()).getId();
        return new ResponseEntity<>(orderService.findAllOrdersFromClients(userId), HttpStatus.OK);
    }

    //Отправка товара
    @GetMapping("/order/{id}")
    public ResponseEntity<String> orderConfirm(@PathVariable int id){
        orderService.sendOrder(id);
        return new ResponseEntity<>("Отправка товара клиенту прошла успешно", HttpStatus.OK);
    }

    //Отмена заказа
    @GetMapping("/order/cancellation/{id}")
    public ResponseEntity<String> orderCancellation(@PathVariable int id){
        orderService.cancellationOrder(id);
        return new ResponseEntity<>("Товар отменен, деньги вернутся к клиенту через 7 дней", HttpStatus.OK);
    }


}
