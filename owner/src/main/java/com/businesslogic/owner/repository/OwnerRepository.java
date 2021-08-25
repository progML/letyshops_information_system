package com.businesslogic.owner.repository;

import com.businesslogic.owner.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEntity, Integer> {

    OwnerEntity findByLogin(String login);

}
