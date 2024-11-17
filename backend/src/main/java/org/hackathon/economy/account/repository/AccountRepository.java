package org.hackathon.economy.account.repository;

import org.hackathon.economy.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findAccountByAccountNo(Long accountNo);
}
