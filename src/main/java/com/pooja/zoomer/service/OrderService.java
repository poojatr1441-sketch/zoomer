package com.pooja.zoomer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pooja.zoomer.dto.OrderItemDTO;
import com.pooja.zoomer.dto.OrderResponseDTO;
import com.pooja.zoomer.entity.*;
import com.pooja.zoomer.entity.enums.*;
import com.pooja.zoomer.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemAddonRepository orderItemAddonRepository;
    private final PaymentRepository paymentRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    
    // 🔥 STEP 1–7 → PLACE ORDER
    @Transactional
    public void placeOrder(Long userId, Long addressId, PaymentMethod method) {

        // 🔹 STEP 1 — Get Cart
        Cart cart = cartRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 🔹 STEP 2 — Get Address
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        // 🔴 VALIDATION — Address belongs to user
        if (!address.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user");
        }

        // 🔴 VALIDATION — Cart must have restaurant
        if (cart.getRestaurant() == null) {
            throw new RuntimeException("Cart is not linked to any restaurant");
        }

        // 🔹 STEP 3 — Create Order
        Order order = Order.builder()
                .user(cart.getUser())
                .restaurant(cart.getRestaurant())
                .deliveryAddress(address)
                .orderStatus(OrderStatus.PENDING)
                .createdTime(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .build();

        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        // 🔹 STEP 4 — Convert Cart → OrderItems
        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .itemName(cartItem.getMenuItem().getName())
                    .itemPrice(cartItem.getMenuItem().getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItem = orderItemRepository.save(orderItem);

            BigDecimal itemTotal = cartItem.getMenuItem().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            // 🔹 ADDONS
            if (cartItem.getCartItemAddons() != null && !cartItem.getCartItemAddons().isEmpty()) {

                for (CartItemAddon cartAddon : cartItem.getCartItemAddons()) {

                    OrderItemAddon orderAddon = OrderItemAddon.builder()
                            .orderItem(orderItem)
                            .addonName(cartAddon.getAddon().getName())
                            .addonPrice(cartAddon.getAddon().getPrice())
                            .build();

                    orderItemAddonRepository.save(orderAddon);

                    BigDecimal addonTotal = cartAddon.getAddon().getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

                    itemTotal = itemTotal.add(addonTotal);
                }
            }

            total = total.add(itemTotal);
        }

        // 🔹 STEP 5 — Payment
        Payment payment = Payment.builder()
                .order(order)
                .totalCost(total)
                .method(method)
                .paymentStatus(PaymentStatus.COMPLETED)
                .build();

        paymentRepository.save(payment);

        // 🔹 STEP 6 — Update total
        order.setTotalAmount(total);

        // 🔹 STEP 7 — Clear Cart
        cart.getCartItems().clear();
        cart.setRestaurant(null);
        cartRepository.save(cart);
    }

    // 🔥 TRACK ORDER
    public OrderResponseDTO getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToDTO(order);
    }

    // 🔥 CANCEL ORDER
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Only pending orders can be cancelled"
            );
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        // 🔥 REFUND
        processRefund(order);
    }
    
    //Refund
    private void processRefund(Order order) {

        Payment payment = paymentRepository.findByOrder_OrderId(order.getOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getPaymentStatus() == PaymentStatus.COMPLETED) {
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
        }
    }
    
    //RESTAURANT OWNER
    //accept order
    @Transactional
    public void acceptOrder(Long orderId, String email) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 SECURITY CHECK
        if (!order.getRestaurant()
                .getOwner()
                .getUserId()
                .equals(owner.getUserId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another owner's order"
            );
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only pending orders can be accepted"
            );
        }

        order.setOrderStatus(OrderStatus.ACCEPTED);
    }
    
    //restaurant cancels the order
    @Transactional
    public void rejectOrder(Long orderId, String email) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 SECURITY CHECK
        if (!order.getRestaurant()
                .getOwner()
                .getUserId()
                .equals(owner.getUserId())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot access another owner's order"
            );
        }

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only pending orders can be rejected"
            );
        }

        order.setOrderStatus(OrderStatus.REJECTED);

        processRefund(order);
    }
    
    //Assign agent
    @Transactional
    public void assignAgent(Long orderId, Long agentId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        User agent = new User();
        agent.setUserId(agentId); // simple reference

        order.setDeliveryAgent(agent);
        order.setOrderStatus(OrderStatus.ACCEPTED);
    }
    
    //pickup
    @Transactional
    public void pickupOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.ACCEPTED) {
            throw new RuntimeException("Order not ready for pickup");
        }

        order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
    }
    
    //deliver
    @Transactional
    public void deliverOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.OUT_FOR_DELIVERY) {
            throw new RuntimeException("Order not out for delivery");
        }

        order.setOrderStatus(OrderStatus.DELIVERED);
    }
    
    public OrderResponseDTO convertToDTO(Order order) {

        return OrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .status(order.getOrderStatus().name())
                .totalAmount(order.getTotalAmount())
                .restaurantName(order.getRestaurant().getName())
                .items(
                    order.getOrderItems().stream().map(item ->
                            OrderItemDTO.builder()
                                    .itemName(item.getItemName())
                                    .quantity(item.getQuantity())
                                    .price(item.getItemPrice())
                                    .build()
                    ).toList()
                )
                .build();
    }
    
}