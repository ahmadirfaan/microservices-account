package com.irfaan.microservicesaccount.db.repository;

import com.irfaan.microservicesaccount.db.entity.TempAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ahmad Irfaan
 * @date 7/19/2021 10:51 AM
 * microservices-account
 */

@Repository
public interface TempAccountRepository extends CrudRepository<TempAccount, String> {
    TempAccount getFirstByEmail(String email);
}
