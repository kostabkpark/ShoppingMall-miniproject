package org.example.shoppingmall_miniproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.member.MemberInquiryDto;
import org.example.shoppingmall_miniproject.dto.order.OrderCreateDto;
import org.example.shoppingmall_miniproject.dto.order.OrderInquiryDto;
import org.example.shoppingmall_miniproject.dto.order.OrderProductCreateDto;
import org.example.shoppingmall_miniproject.entity.MemberStatus;
import org.example.shoppingmall_miniproject.service.MemberService;
import org.example.shoppingmall_miniproject.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderAPIController {
    private final OrderService orderService;
    private final MemberService memberService;

    @PutMapping
    public OrderInquiryDto createNewOrder(@RequestBody OrderCreateDto orderDto,
                               @RequestBody OrderProductCreateDto... orderProductDtos) {
        return orderService.createOrder(orderDto, orderProductDtos);
    }

    @GetMapping("/members/{userId}")
    public List<OrderInquiryDto> getAllOrdersForOneMember(@PathVariable("userId") String userId) {
        return orderService.getAllOrdersByMember(userId);
    }

    @GetMapping("/{orderId}")
    public OrderInquiryDto getOneOrder(@PathVariable("orderId") long orderId) {
        return orderService.getOneOrder(orderId);
    }
}
