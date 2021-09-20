package com.businesslogic.client.service;


import com.businesslogic.client.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager entityManager;


    public List<Object> findAllOrder(int userId) {
        return orderRepository.findAllOrder(userId);
    }


    //Количество позицей в корзине
    public long findCount(int userId) {
        return orderRepository.findCount(userId);
    }

    public long findOrderId(int userId) {
        return orderRepository.findOrderId(userId);
    }


    @Transactional
    public List<Object> findByBasketId(int userId) {
        return orderRepository.findByBasketId(userId);
    }


    //Создаю заказ, заношу все девайсы заказа
    @Transactional
    public void addItem(long count, Object[] allBasketId, int userId) {
        entityManager.createNativeQuery("INSERT  INTO orders (place, state, client_id) values (?,?,?)")
                .setParameter(1, "На складе")
                .setParameter(2, "Ожидает оплаты")
                .setParameter(3, userId)

                .executeUpdate();
        long order_id = findOrderId(userId);
        for (int i = 0; i < count; i++) {
            entityManager.createNativeQuery("INSERT INTO orderitem (device_id, order_id) values (?, ?)")
                    .setParameter(1, allBasketId[i])
                    .setParameter(2, order_id)
                    .executeUpdate();
        }
    }


    @Transactional
    public List<Object> findAllOrdersFromStore(int userId) {
        return orderRepository.findAllOrdersFromStore(userId);
    }


    public void buyAll(int id, int userId) {
        orderRepository.buyOrder(id, userId);
    }


    //получение заказа
    public void getOrder(int userId) {
        orderRepository.getOrder(userId);
    }


    //отмена заказа
    public void undoOrder(int id, int userId) {
        orderRepository.undoOrder(id, userId);
    }

    //проверка на наличие заказа
    public int checkOrder(int id, int userId) {
        return orderRepository.checkOrder(id, userId);
    }


    public float getCost(Integer id) {
        return orderRepository.findCost(id);
    }


    public float getCashback(int userId) {
        return orderRepository.getCashback(userId);
    }

}
