package org.hackathon.economy.member.service;

import lombok.RequiredArgsConstructor;
import org.hackathon.economy.member.domain.LoginDTO;
import org.hackathon.economy.member.domain.Member;
import org.hackathon.economy.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        memberRepository.save(member);
        return member.getMemberNo();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberNo) {
        return memberRepository.findOne(memberNo);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional
    public void delete(Long memberNo) {
        memberRepository.delete(memberNo);
    }

    public Member login(LoginDTO loginDTO) {
        Member member = memberRepository.findByEmail(loginDTO.getEmail());
        if (member != null && member.getMemberPassword().equals(loginDTO.getPassword())) {
            return member;
        }
        return null;
    }
}
