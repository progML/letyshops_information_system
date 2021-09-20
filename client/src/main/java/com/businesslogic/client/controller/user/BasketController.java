package com.businesslogic.client.controller.user;


import com.businesslogic.client.dto.BasketEntityDto;
import com.businesslogic.client.service.BasketService;
import com.businesslogic.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Контроллер для взаемодействия с корзиной
 *
 * Основные функции:
 * 1) Добавить устройство в корзину
 * 2) Удалить устройство из корзины
 * 3) Просмотреть содержимое корзины
 * 4) Узнать итоговую стоимость содержимого в корзине
 *
 * @author progML
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")

public class BasketController {

    private final BasketService basketService;

    private final ClientService clientService;


    /**
     * Добавление товара в корзину
     *
     * @param basketEntityDto - слой передачи данных
     * @param principal - пользователь, вошедший в систему
     */
    @PostMapping("/basket/add")
    public ResponseEntity<String> addOrder(@RequestBody BasketEntityDto basketEntityDto, Principal principal) {
        if (basketService.checkDeviceId(basketEntityDto.getDevice_id()) > 0) {
            try {
                basketService.addOrder(basketEntityDto, clientService.findByLogin(principal.getName()).getId());
                return new ResponseEntity<>("Товары добавлены в корзину", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Ваша корзина пуста", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Такого товара не существует", HttpStatus.NOT_FOUND);
        }
    }


    //удаление товара из корзины
    @DeleteMapping("/basket/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Integer id, Principal principal) {
        try {
            if (basketService.findUserByBasketId(id) == clientService.findByLogin(principal.getName()).getId()) {
                basketService.deleteBasket(id);
                return new ResponseEntity<>("Удаление из корзины прошло успешно", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("В вашей корзине нет товара с таким номером", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Товара с таким индексом нет в корзине", HttpStatus.NOT_FOUND);
        }
    }


    //  просмотр корзины
    @GetMapping("/basket")
    public ResponseEntity<Object> findAll(Principal principal) {
        int userId = clientService.findByLogin(principal.getName()).getId();
        if (basketService.findAllById(userId).isEmpty()) {
            return new ResponseEntity<>("Корзина пуста", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(basketService.findAllById(userId), HttpStatus.OK);
        }
    }


    //итогавая стоимость в корзине
    @GetMapping("basket/sum")
    public ResponseEntity getPrice(Principal principal) {
        return new ResponseEntity(
                "Итогавая стоимоть: " + basketService.findSum(clientService.findByLogin(principal.getName()).getId()) + " Кэшбэк составит: " + basketService.findCashback(),
                HttpStatus.OK);
    }


}
