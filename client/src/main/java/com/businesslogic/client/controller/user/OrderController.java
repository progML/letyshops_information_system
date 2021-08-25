package com.businesslogic.client.controller.user;



import com.businesslogic.client.jms.Sender;
import com.businesslogic.client.service.BasketService;
import com.businesslogic.client.service.ClientService;
import com.businesslogic.client.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class OrderController {

  private final OrderService orderService;
  private final BasketService basketService;
  private final ClientService clientService;
  private final Sender sender;
  private int userId;


  //Оформления заказа
  @GetMapping("/order")
  public ResponseEntity makeOrder(Principal principal) {
    userId = clientService.findByLogin(principal.getName()).getId();
    if (!basketService.findAllById(userId).isEmpty()) {
      long count = orderService.findCount(userId); // кол-во позиций в корзине
      orderService.addItem(count, orderService.findByBasketId(userId).toArray(), userId);
      basketService.deleteAllBasketByUserId(userId);
      return new ResponseEntity("Заказ сформирован, оплатите его", HttpStatus.OK);
    } else {
      return new ResponseEntity("Ваша корзина пуста", HttpStatus.NOT_FOUND);
    }
  }



  //Просмотр заказов
  @GetMapping("/order/store")
  public ResponseEntity<Object> seeOrder(Principal principal) {
    userId = clientService.findByLogin(principal.getName()).getId();
    return new ResponseEntity(orderService.findAllOrder(userId), HttpStatus.OK);
  }



  //отмена заказа
  @GetMapping("/order/undo/{id}")
  public ResponseEntity undoOrder(@PathVariable Integer id, Principal principal) {
    userId = clientService.findByLogin(principal.getName()).getId();
    if (orderService.checkOrder(id, userId) != 0) {
      orderService.undoOrder(id, userId);
      return new ResponseEntity("Ваш заказ успешно отменен!", HttpStatus.OK);
    }else {
      return new ResponseEntity("Данный заказ уже отменен или не был создан", HttpStatus.NOT_FOUND);
    }
  }

  //todo: добавить sender
  //Покупка заказа
  @GetMapping("/order/buy/{id}")
  public ResponseEntity buyAll(@PathVariable Integer id, Principal principal) {
    userId = clientService.findByLogin(principal.getName()).getId();
    orderService.buyAll(id, userId);
    sender.send("Пользователь с никнеймом: "+ principal.getName() + " приобрел товар, проверьте систему! ");
    return new ResponseEntity("Ваша покупка в размере: " + orderService.getCost(id) + " рублей прошла успешно! ",
        HttpStatus.OK);
  }



  //получение заказа
  @GetMapping("/order/get")
  public ResponseEntity getOrder(Principal principal) {
    userId = clientService.findByLogin(principal.getName()).getId();
    orderService.getOrder(userId);
    return new ResponseEntity("Спасибо что воспользовались нашим сервисом! Ваш кэшбэк в размере: " +
        orderService.getCashback(userId) + " рублей будет автоматический зачислен на ваш баланс", HttpStatus.OK);
  }
}
