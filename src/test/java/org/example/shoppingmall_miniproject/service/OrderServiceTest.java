package org.example.shoppingmall_miniproject.service;

import org.example.shoppingmall_miniproject.dto.product.StockCreateDto;
import org.example.shoppingmall_miniproject.dto.member.MemberCreateDto;
import org.example.shoppingmall_miniproject.dto.member.MemberInquiryDto;
import org.example.shoppingmall_miniproject.dto.order.OrderCreateDto;
import org.example.shoppingmall_miniproject.dto.order.OrderInquiryDto;
import org.example.shoppingmall_miniproject.dto.order.OrderProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductInquiryDto;
import org.example.shoppingmall_miniproject.entity.*;
import org.example.shoppingmall_miniproject.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
//@Commit
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 주문생성테스트(){
        // given (회원, 상품, 재고 가 주어졌을때)
        MemberCreateDto memberCreateDto = new MemberCreateDto(
                "testbbb", "bbb", "1111","a","1","a"
        );
        MemberInquiryDto memberDto = memberService.addMember(memberCreateDto);
        Member member = memberRepository.findByUserId(memberDto.getUserId()).get();

        ProductCreateDto productDto = new ProductCreateDto(
                "testA", 200, 220
        );
        ProductInquiryDto newProduct1 = productService.addProduct(productDto);
        productDto = new ProductCreateDto(
                "testB", 300, 330
        );
        ProductInquiryDto newProduct2 = productService.addProduct(productDto);
        StockCreateDto stockDto = new StockCreateDto(
                Warehouse.KR, newProduct1.getProductId(),2000
        );
        stockService.addStock(stockDto);
        stockDto = new StockCreateDto(
                Warehouse.KR, newProduct2.getProductId(),3000
        );
        stockService.addStock(stockDto);
        // when (주문을 생성했더니)
        OrderCreateDto orderCreateDto = new OrderCreateDto(memberDto.getMemberId(), "test-address");
        OrderProductCreateDto orderProductDto1 = new OrderProductCreateDto(newProduct1.getProductId(),100);
            OrderProductCreateDto orderProductDto2 = new OrderProductCreateDto(newProduct2.getProductId(),100);
        OrderInquiryDto orderDto = orderService.createOrder(orderCreateDto, orderProductDto1, orderProductDto2);
        Order order = orderRepository.findById(orderDto.getOrderId()).get();
        // then (잘 만들어졌더라.)
        assertThat(orderRepository.findAllByMember(member)).hasSize(1);
        assertThat(orderProductRepository.findAllByOrder(order)).hasSize(2);
    }

    @Test
    void 전체주문취소테스트(){
        //given - aaa 가 주문한 등록된 order
        MemberInquiryDto aaa = memberService.getOneMember("aaa");
        OrderInquiryDto orderDto = orderService.getAllOrdersByMember(aaa.getUserId()).get(0);
        Order order = null;
        OrderProduct orderProduct = null;
        Stock stock = null;
        //when
        if(orderDto != null) {
            order = orderRepository.findById(orderDto.getOrderId()).get();
            orderProduct =  order.getOrderProducts().get(0);
            orderService.cancelOrderProduct(orderProduct.getOrderProductId());
            stock = stockRepository.findByWarehouseAndProduct(Warehouse.KR, orderProduct.getProduct()).get();
        }
        //then
        assertThat(order).isNotNull();
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.totalCancelled);
        assertThat(order.getTotalQuantity()).isEqualTo(0L);
        assertThat(orderProduct).isNotNull();
        assertThat(stock.getQuantity()).isEqualTo(10000L);
    }
}