package org.example.shoppingmall_miniproject.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.MemberCreateDto;
import org.example.shoppingmall_miniproject.dto.MemberInquiryDto;
import org.example.shoppingmall_miniproject.entity.Member;
import org.example.shoppingmall_miniproject.entity.MemberStatus;
import org.example.shoppingmall_miniproject.exception.NotUniqueUserIdException;
import org.example.shoppingmall_miniproject.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    MemberInquiryDto addMember(MemberCreateDto memberDto){
        if(checkUniqueUserId(memberDto.getUserId())){
            Member member = new Member(
                    0, memberDto.getMemberName(),
                    memberDto.getUserId(), memberDto.getPassword(),
                    memberDto.getEmail(), memberDto.getPhone(),
                    memberDto.getAddress(), MemberStatus.A,
                    LocalDate.now(), null);

            Member save = memberRepository.save(member);

        }
        return null;
    }

    boolean checkUniqueUserId(String userId) {
        Optional<Member> byUserId = memberRepository.findByUserId(userId);
        if(byUserId.isPresent()){
            throw new NotUniqueUserIdException("동일한 ID가 존재합니다.");
        }
        return true;
    }
}
