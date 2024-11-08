package org.example.shoppingmall_miniproject.service;

import org.example.shoppingmall_miniproject.dto.member.MemberCreateDto;
import org.example.shoppingmall_miniproject.dto.member.MemberInquiryDto;
import org.example.shoppingmall_miniproject.entity.Member;
import org.example.shoppingmall_miniproject.entity.MemberStatus;
import org.example.shoppingmall_miniproject.exception.NotUniqueUserIdException;
import org.example.shoppingmall_miniproject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(allMembers.size()).isEqualTo(1);
    }

    @Test
    public void 회원등록테스트(){
        // given
        MemberCreateDto memberCreateDto = new MemberCreateDto(
                "test1", "aaa", "1111","a","1","a"
        );
        MemberCreateDto memberCreateDto1 = new MemberCreateDto(
                "test1", "aaa", "1111","a","1","a"
        );
        // when
        memberService.addMember(memberCreateDto);
        assertThat(memberService.getAllMembers().size()).isEqualTo(1);
        // then
        assertThrows(NotUniqueUserIdException.class, () -> {
            memberService.addMember(memberCreateDto1);
        });
    }
}