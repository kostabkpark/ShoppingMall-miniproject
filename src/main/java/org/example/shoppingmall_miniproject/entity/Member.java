package org.example.shoppingmall_miniproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;
    @Column(length=20)
    private String memberName;
    @Column(length=6)
    private String userId;
    @Column(length=8)
    private String password;
    @Column(length=30)
    private String email;
    @Column(length=11)
    private String phone;
    @Column(length=100)
    private String address;
    @Enumerated(EnumType.ORDINAL) // EnumType.STRING
    private MemberStatus memberStatus; // refactoring 필요
    private LocalDate registerDate;
    private LocalDate leaveDate;

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    public boolean changePassword(String password, String newPassword){
        // 현재 암호와 같은 경우에 한해서 새로운 암호로 변경
        if(password.equals(this.password)){
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void leave(){
        // 현재 일자로 탈퇴처리
        this.leaveDate=LocalDate.now();
        this.memberStatus = MemberStatus.B;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberName='" + memberName + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
