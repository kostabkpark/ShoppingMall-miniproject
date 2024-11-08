package org.example.shoppingmall_miniproject.service;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall_miniproject.dto.order.OrderCreateDto;
import org.example.shoppingmall_miniproject.dto.order.OrderInquiryDto;
import org.example.shoppingmall_miniproject.dto.order.OrderProductCreateDto;
import org.example.shoppingmall_miniproject.entity.*;
import org.example.shoppingmall_miniproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final DeliveryRepository deliveryRepository;

    public OrderInquiryDto createOrder(OrderCreateDto orderDto,
                                       OrderProductCreateDto... orderProductDtos) {
        Member member = memberRepository.findById(orderDto.getMemberId()).get();
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductCreateDto orderProductDto : orderProductDtos) {
            Product product = productRepository.findById(orderProductDto.getProductId()).get();
            Stock stock = stockRepository.findByWarehouseAndProduct(Warehouse.KR, product).get();
            OrderProduct orderProduct = OrderProduct.createOrderProduct(
                    product, orderProductDto.getQuantity(), product.getPrice(), stock
            );
            orderProducts.add(orderProduct);
        }

        Delivery delivery = new Delivery(
                0L, null, orderDto.getAddress(),
                DeliveryStatus.prepared,
                LocalDateTime.now()
        );

        Order order = Order.createOrder(member, delivery, orderProducts);
        delivery.setOrder(order);
        Order save = orderRepository.save(order);
        return OrderInquiryDto.of(save);
    }
}
