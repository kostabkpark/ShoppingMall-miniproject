package org.example.shoppingmall_miniproject.service;

import org.assertj.core.api.Assertions;
import org.example.shoppingmall_miniproject.dto.MemberInquiryDto;
import org.example.shoppingmall_miniproject.entity.Member;
import org.example.shoppingmall_miniproject.entity.MemberStatus;
import org.example.shoppingmall_miniproject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 전체회원정보조회() {
        // given
        Member member = new Member(0,"test1","aaa", "1111", "a@naver.com", "1111", "오리역", MemberStatus.A, LocalDate.now(), null);
        memberRepository.save(member);
        // when
        List<MemberInquiryDto> allMembers = memberService.getAllMembers();
        // then
        Assertions.assertThat(allMembers.size()).isEqualTo(1);
    }
}