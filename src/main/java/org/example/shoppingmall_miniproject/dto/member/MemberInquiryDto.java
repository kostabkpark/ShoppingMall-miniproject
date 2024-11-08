package org.example.shoppingmall_miniproject.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shoppingmall_miniproject.entity.Member;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInquiryDto {
    private int memberId;
    private String memberName;
    private String userId;

    public static MemberInquiryDto of(Member member) {
        MemberInquiryDto dto = new MemberInquiryDto(
                member.getMemberId(),
                member.getMemberName(),
                member.getUserId()
        );
        return dto;
    }
}
