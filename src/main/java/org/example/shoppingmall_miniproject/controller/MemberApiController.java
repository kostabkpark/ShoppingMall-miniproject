package org.example.shoppingmall_miniproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.member.MemberCreateDto;
import org.example.shoppingmall_miniproject.dto.member.MemberInquiryDto;
import org.example.shoppingmall_miniproject.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping
    public List<MemberInquiryDto> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{memberId}")
    public MemberInquiryDto getOneMember(@PathVariable("memberId") String memberId) {
        return memberService.getOneMember(memberId);
    }

    @PostMapping
    public MemberInquiryDto createNewMember(@RequestBody MemberCreateDto memberDto) {
        return memberService.addMember(memberDto);
    }
}
