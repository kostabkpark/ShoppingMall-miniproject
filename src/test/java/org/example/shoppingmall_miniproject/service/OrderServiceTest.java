package org.example.shoppingmall_miniproject.service;

import org.assertj.core.api.Assertions;
import org.example.shoppingmall_miniproject.dto.StockCreateDto;
import org.example.shoppingmall_miniproject.dto.member.MemberCreateDto;
import org.example.shoppingmall_miniproject.dto.member.MemberInquiryDto;
import org.example.shoppingmall_miniproject.dto.order.OrderCreateDto;
import org.example.shoppingmall_miniproject.dto.order.OrderProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductCreateDto;
import org.example.shoppingmall_miniproject.dto.product.ProductInquiryDto;
import org.example.shoppingmall_miniproject.entity.Member;
import org.example.shoppingmall_miniproject.entity.Warehouse;
import org.example.shoppingmall_miniproject.repository.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@Commit
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

        @Test
    void 주문생성테스트(){
        // given (회원, 상품, 재고 가 주어졌을때)
        MemberCreateDto memberCreateDto = new MemberCreateDto(
                "test1", "aaa", "1111","a","1","a"
        );
        MemberInquiryDto memberDto = memberService.addMember(memberCreateDto);

        ProductCreateDto productDto = new ProductCreateDto(
                "test1", 100, 120
        );
        ProductInquiryDto newProduct = productService.addProduct(productDto);
        StockCreateDto stockDto = new StockCreateDto(
                Warehouse.KR, newProduct.getProductId(),10000
        );
        stockService.addStock(stockDto);
        // when (주문을 생성했더니)
        OrderCreateDto orderCreateDto = new OrderCreateDto(memberDto.getMemberId(), "test-address");
        OrderProductCreateDto orderProductDto = new OrderProductCreateDto(newProduct.getProductId(),100);
        orderService.createOrder(orderCreateDto, orderProductDto);
           /* 구글링 결과
             엔티티 클래스에 @Id를 부여한 필드에 @GeneratedValue를
            작성하여 AUTO, SEQUENCE, IDENTITY 전략 등 데이터베이스에게
            key 값을 자동 생성하도록 하는 전략을 선택하였으면서 엔티티 객체 생성 시
            Id에 해당하는 필드에 직접 값을 입력하면 detach persist 오류 발생함 (cascade된 객체에)
             나의 경우 Long 인데 delivery 생성시 0L 을 delivery_id 에 넣어서 오류가 나서
             null 로 변경해서 오류 해결됨. */

        // then (잘 만들어졌더라.)
        assertThat(orderRepository.findAll()).hasSize(1);
        assertThat(orderProductRepository.findAll()).hasSize(1);
    }
}