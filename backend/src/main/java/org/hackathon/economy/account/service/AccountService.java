package org.hackathon.economy.account.service;

import lombok.RequiredArgsConstructor;
import org.hackathon.economy.account.domain.Account;
import org.hackathon.economy.account.domain.Interest;
import org.hackathon.economy.account.repository.AccountRepository;
import org.hackathon.economy.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public Account create(Member member) {
        Account account = Account.builder()
                .accountBalance(0L)
                .accountRate(0.0)
                .createDate(new Date())
                .updateDate(new Date())
                .accountStatus(true)
                .build();

        // 연관 관계 설정
        account.setMember(member);

        return accountRepository.create(account);
    }

    // 멤버로 계좌 찾기
    public Account findByMember(Member member) {
        Account findAccount = accountRepository.findByMember(member);
        if(findAccount == null) {
            return null;
        }
        return findAccount;
    }

    @Transactional
    public Long deposit(Account account, Long amount) {
        return accountRepository.deposit(account, amount);
    }

    @Transactional
    public Long withdraw(Account account, Long amount) {
        return accountRepository.withdraw(account, amount);
    }
}
