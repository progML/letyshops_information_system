package com.businesslogic.client.repository;


import com.businesslogic.client.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    ClientEntity findByLogin(String login);

    @Query(nativeQuery = true, value = "Select count(id) from clients")
    int countUsers();
}
