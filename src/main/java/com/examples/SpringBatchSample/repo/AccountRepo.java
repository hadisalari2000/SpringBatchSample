package com.examples.SpringBatchSample.repo;

import com.examples.SpringBatchSample.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {
}
