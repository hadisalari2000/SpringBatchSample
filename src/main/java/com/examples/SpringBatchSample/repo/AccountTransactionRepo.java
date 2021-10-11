package com.examples.SpringBatchSample.repo;

import com.examples.SpringBatchSample.model.entity.Account;
import com.examples.SpringBatchSample.model.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepo extends JpaRepository<AccountTransaction, Long> {
}
