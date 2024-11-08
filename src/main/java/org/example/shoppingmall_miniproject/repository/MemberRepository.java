package org.example.shoppingmall_miniproject.repository;

import org.example.shoppingmall_miniproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
