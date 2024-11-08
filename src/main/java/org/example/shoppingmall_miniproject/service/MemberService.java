package org.example.shoppingmall_miniproject.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.MemberInquiryDto;
import org.example.shoppingmall_miniproject.entity.Member;
import org.example.shoppingmall_miniproject.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    List<MemberInquiryDto> getAllMembers(){
        List<Member> all = memberRepository.findAll();
        return all.stream()
                .map(m -> new MemberInquiryDto(
                        m.getMemberId(),
                        m.getMemberName(),
                        m.getUserId()
                ))
                .collect(Collectors.toList());
    }
}
