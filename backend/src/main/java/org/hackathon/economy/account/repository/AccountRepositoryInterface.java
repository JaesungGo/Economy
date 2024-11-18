package org.hackathon.economy.account.repository;

import org.hackathon.economy.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositoryInterface extends JpaRepository<Account, Long>{
    Account findAccountByAccountNo(Long accountNo);
}
