package com.businesslogic.client.service;


import com.businesslogic.client.repository.ClientRepository;
import com.businesslogic.client.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CashBackService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private float tempSum;

    @PersistenceContext
    private EntityManager entityManager;


    //Автоматическое начислени кэшбэка на счет
    @Transactional
    public void sendCashBack() {
        for (int i = 1; i <= clientRepository.countUsers(); i++) {
            tempSum = orderRepository.getCashback(i);
            entityManager.createNativeQuery("UPDATE clients SET balance = (?) where id = (?)")
                    .setParameter(1, tempSum)
                    .setParameter(2, i)
                    .executeUpdate();
        }

    }
//    "UPDATE orders SET place = 'Продавец отправил заказ' where id =:Id and place = 'Продавец собирает заказ' ")
}
