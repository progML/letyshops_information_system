package com.businesslogic.owner.service;


import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @PersistenceContext
    private EntityManager entityManager;


    //Товары ожидающие отправки
    @Transactional
    public List findAllOrdersFromClients(int userId) {
        return entityManager.createNativeQuery("SELECT o.id, d.name, o.place, o.state from orders o inner join orderitem o2 on o.id = o2.order_id inner join device d on o2.device_id = d.id inner join shop s on d.shop_id = s.id where o.place = 'Продавец собирает заказ' and o.state = 'Оплачено' and s.owner_id =(?)")
                .setParameter(1, userId)
                .getResultList();
    }

    //Отправка заказа
    @Transactional
    public void sendOrder(int userId) {
        entityManager.createNativeQuery("UPDATE orders SET place = 'Продавец отправил заказ' where id =(?) and place = 'Продавец собирает заказ'")
                .setParameter(1, userId)
                .executeUpdate();
    }

    //Отмена заказа
    @Transactional
    public void cancellationOrder(int userId) {
        entityManager.createNativeQuery("UPDATE orders SET state = 'Продавец отменил заказ', place = '-' where id =(?) and place = 'Продавец собирает заказ'")
                .setParameter(1, userId)
                .executeUpdate();
    }

}
