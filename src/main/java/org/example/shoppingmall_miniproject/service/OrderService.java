package org.example.shoppingmall_miniproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shoppingmall_miniproject.dto.order.OrderCreateDto;
import org.example.shoppingmall_miniproject.dto.order.OrderInquiryDto;
import org.example.shoppingmall_miniproject.dto.order.OrderProductCreateDto;
import org.example.shoppingmall_miniproject.entity.*;
import org.example.shoppingmall_miniproject.exception.OrderCancelException;
import org.example.shoppingmall_miniproject.exception.StockNotExistException;
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
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final DeliveryRepository deliveryRepository;

    public OrderInquiryDto getOneOrderByMember(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        Optional<Order> byMember = orderRepository.findByMember(member);
        if (byMember.isPresent()) {
            Order order = byMember.get();
            return OrderInquiryDto.of(order);
        }
        return null;
    }

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

          /* 구글링 결과
             엔티티 클래스에 @Id를 부여한 필드에 @GeneratedValue를
            작성하여 AUTO, SEQUENCE, IDENTITY 전략 등 데이터베이스에게
            key 값을 자동 생성하도록 하는 전략을 선택하였으면서 엔티티 객체 생성 시
            Id에 해당하는 필드에 직접 값을 입력하면 detach persist 오류 발생함 (cascade된 객체에)
             나의 경우 Long 인데 delivery 생성시 0L 을 delivery_id 에 넣어서 오류가 나서
             null 로 변경해서 오류 해결됨. */
        Delivery delivery = new Delivery(
                // delivery_id 가 long 이면 0L 로 줘도 되지만, Long 인 경우에는 null 로 줘야 함.
                null, null, orderDto.getAddress(),
                DeliveryStatus.prepared,
                LocalDateTime.now()
        );

        Order order = Order.createOrder(member, delivery, orderProducts);
        delivery.setOrder(order);
        // order 가 저장될 때 delivery 와 orderProduct 은 영속성 전이에 의해 함께 저장된다.
        Order save = orderRepository.save(order);
        log.info("order : {}", save.getOrderId());
        OrderInquiryDto orderInquiryDto = OrderInquiryDto.of(save);
        log.info("orderInquiryDto : {}", orderInquiryDto);

        return orderInquiryDto;
    }

    public void cancelOrder(int orderId) {

    }

    public void cancelOrderProduct(long orderProductId) {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).get();
        Optional<Stock> byWarehouseAndProduct = stockRepository.findByWarehouseAndProduct(Warehouse.KR, orderProduct.getProduct());
        if (byWarehouseAndProduct.isPresent()) {
            Stock stock = byWarehouseAndProduct.get();
            // 상품에 대한 캔슬, 재고 up
            orderProduct.cancelOrderProduct(stock);
            Order order = orderProduct.getOrder();
            // 오더 헤더에 대한 부분취소/전체 취소 처리
            // A = 3, B = 2(취소) ==> 전체취소
            // A = 3, B = 2 ==> 부분취소
            List<OrderProduct> details = order.getOrderProducts();
            int cntCancel = 0;
            for (OrderProduct detail : details) {
                if(detail.getStatus() == OrderProductStatus.canceled) {
                    cntCancel++;
                }
            }
            if(details.size() - cntCancel > 1) {
                order.partialOrderCancel(orderProduct);
            } else {
                order.totalOrderCancel();
            }
            // order 가 저장될 때 delivery 와 orderProduct 은 영속성 전이에 의해 함께 저장된다.
            Order save = orderRepository.save(order);
        } else {
            throw new StockNotExistException("주문 취소할 수 없습니다.");
        }
    }
}
