package com.businesslogic.client.repository;


import com.businesslogic.client.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {


    @Query(nativeQuery = true, value = "Select SUM(cost) from device d INNER JOIN orderitem o ON d.id = o.device_id WHERE order_id =:orderId")
    float findCost(@Param("orderId") int orderId);



    @Query(nativeQuery = true, value = "Select r.id, r.state,r.place, d.name, d.cost from device d inner join orderitem o ON d.id = o.device_id inner join orders r on r.id = o.order_id where client_id =:userId")
    List<Object> findAllOrder(@Param("userId") int userId);

    @Query(nativeQuery = true, value = "SELECT device_id from basket where client_id =:userId")
    List<Object> findByBasketId(@Param("userId") int userId);

    @Query(nativeQuery = true, value = "SELECT count(device_id) from basket where client_id =:userId")
    long findCount(@Param("userId") int userId);


    @Query(nativeQuery = true, value = "SELECT id FROM orders where client_id =:userId ORDER BY ID DESC LIMIT 1")
    long findOrderId(@Param("userId") int userId);



    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE orders SET place = 'Продавец собирает заказ', state = 'Оплачено' where id =:id and place = 'На складе' and client_id =:userId")
    void buyOrder(@Param("id") int id, @Param("userId") int userId);



    //Отмена заказа покупателем
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE orders SET state = 'Отменено', place = '-' where id =:id and place = 'На складе' and client_id =:userId")
    void undoOrder(@Param("id") int id, @Param("userId") int userId);

    //Отмена заказа продовцом
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE orders SET state = 'Продавец отменил заказ', place = '-' where id =:id and place = 'Продавец собирает заказ'")
    void cancellationOrder(@Param("id") int id);

    @Query(nativeQuery = true, value = "SELECT count(id) from orders WHERE place = 'На складе' and id =:id and client_id =:userId")
    int checkOrder(@Param("id") int id, @Param("userId") int userId);



    //Просмотор оплаченных товаров
    @Query(nativeQuery = true, value = "SELECT o.id, d.name, o.place, o.state from orders o inner join orderitem o2 on o.id = o2.order_id inner join device d on o2.device_id = d.id inner join shop s on d.shop_id = s.id where s.client_id =:userId")
    List<Object> findAllOrdersFromStore(@Param("userId") int userId);

    //Отправка товара продавцом
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE orders SET place = 'Продавец отправил заказ' where id =:Id and place = 'Продавец собирает заказ' ")
    void sendOrder(@Param("Id") int id);


    //Получение заказа пользователем
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE orders SET place = 'Получено' where place = 'Продавец отправил заказ' and client_id =:userId")
    void getOrder(@Param("userId") int userId);

    // Сумма кэшбэка
    @Query(nativeQuery = true, value = "Select SUM(cashback*d.cost)/100 FROM shop s INNER JOIN device d ON s.id = d.shop_id INNER JOIN orderitem o ON d.id = o.device_id INNER JOIN orders r on o.order_id = r.id WHERE r.place = 'Получено' and r.client_id =:userId")
    float getCashback(@Param("userId") int userId);
}
