package org.hackathon.economy.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hackathon.economy.member.domain.LoginDTO;
import org.hackathon.economy.member.domain.Member;
import org.hackathon.economy.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    //Email로 멤버 검색
//    @GetMapping("/email/{email}")
//    public ResponseEntity<Member> getMemberByEmail(@PathVariable("email") String email) {
//        return ResponseEntity.ok(memberService.findByEmail(email));
//    }

    //no로 멤버 검색
//    @GetMapping("/no/{no}")
//    public ResponseEntity<Member> getMember(@PathVariable("no") Long no) {
//        return ResponseEntity.ok(memberService.findOne(no));
//    }

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<Long> join(@RequestBody Member member) {
        return ResponseEntity.ok(memberService.join(member));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginDTO loginDTO, HttpSession session) {
        Member loginResult = memberService.login(loginDTO);
        if (loginResult != null) {
            //로그인 성공
            session.setAttribute("memberEmail", loginResult.getMemberEmail());
            return ResponseEntity.ok(loginResult.getMemberNo());
        }
        //로그인 실패
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    //로그인 확인
    @GetMapping("/check-login")
    public ResponseEntity<String> checkLogin(HttpSession session) {
        String memberEmail = (String) session.getAttribute("memberEmail");
        if(memberEmail != null) {
          return ResponseEntity.ok("Logged in as : " + memberEmail);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
    }

    //회원정보 조회
    @GetMapping("/")
    public ResponseEntity<Member> getMember(HttpSession session) {
        String memberEmail = (String) session.getAttribute("memberEmail");
        if(memberEmail != null) {
            return ResponseEntity.ok(memberService.findByEmail(memberEmail));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    //회원탈퇴
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> delete(@PathVariable Long no) {
        memberService.delete(no);
        return ResponseEntity.ok().build();
    }
}
