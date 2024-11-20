package org.hackathon.economy.member.service;

import jakarta.servlet.http.HttpSession;
import org.hackathon.economy.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final MemberService memberService;

    public AuthenticationService(MemberService memberService) {
        this.memberService = memberService;
    }

    public Member getAuthenticatedMember(HttpSession session) {
        String memberEmail = (String) session.getAttribute("memberEmail");
        if (memberEmail == null) {
            throw new IllegalStateException("Unauthorized access: Member email is missing");
        }
        return memberService.findByEmail(memberEmail);
    }

    // 로그인 여부 확인
    public String checkLogin(HttpSession session) {
        String memberEmail = (String) session.getAttribute("memberEmail");
        if (memberEmail == null) {
            throw new IllegalStateException("Not logged in");
        }
        return memberEmail;
    }

    public Long getAccountNo(HttpSession session){
        Member member = getAuthenticatedMember(session);
        return member.getAccount().getAccountNo();
    }
}