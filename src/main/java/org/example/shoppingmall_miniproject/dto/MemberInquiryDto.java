package org.example.shoppingmall_miniproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberInquiryDto {
    private int memberId;
    private String memberName;
    private String userId;
}
