package org.hackathon.economy.account.service;

import lombok.RequiredArgsConstructor;
import org.hackathon.economy.account.domain.Account;
import org.hackathon.economy.account.domain.DailyInterest;
import org.hackathon.economy.account.repository.AccountRepository;
import org.hackathon.economy.account.repository.DailyInterestRepository;
import org.hackathon.economy.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DailyInterestService {

    private final DailyInterestRepository dailyInterestRepository;
    private final AccountRepository accountRepository;

    public List<DailyInterest> getAll(Member member) {
        Account account = accountRepository.findByMember(member);
        return dailyInterestRepository.getAll(account);
    }

    public Long getTotal(Member member) {
        Account account = accountRepository.findByMember(member);
        return dailyInterestRepository.getTotal(account);
    }

    public List<DailyInterest> getMonthly(Member member) {
        Account account = accountRepository.findByMember(member);
        return dailyInterestRepository.getMonthly(account);
    }
}
