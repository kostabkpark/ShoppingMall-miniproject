package org.example.shoppingmall_miniproject.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateDto {
    private String memberName;
    private String userId;
    private String password;
    private String email;
    private String phone;
    private String address;
}

//{
//        "memberName" : "테스트",
//        "userId" : "testA",
//        "password" : "1111",
//        "email" : "a@naver.com",
//        "phone" : "0100000000",
//        "address" : "test address"
//        }
