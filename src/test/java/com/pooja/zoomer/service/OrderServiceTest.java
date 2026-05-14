package com.pooja.zoomer.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import com.pooja.zoomer.entity.Cart;
import com.pooja.zoomer.entity.enums.PaymentMethod;
import com.pooja.zoomer.repository.AddressRepository;
import com.pooja.zoomer.repository.CartRepository;
import com.pooja.zoomer.repository.OrderRepository;
import com.pooja.zoomer.repository.PaymentRepository;
import com.pooja.zoomer.repository.OrderItemRepository;
import java.math.BigDecimal;
import java.util.List;

import org.mockito.ArgumentCaptor;

import com.pooja.zoomer.entity.Address;
import com.pooja.zoomer.entity.CartItem;
import com.pooja.zoomer.entity.MenuItem;
import com.pooja.zoomer.entity.Order;
import com.pooja.zoomer.entity.OrderItem;
import com.pooja.zoomer.entity.User;
import org.junit.jupiter.api.Test;
import com.pooja.zoomer.entity.Restaurant;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AddressRepository addressRepository;
    
    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldThrowExceptionWhenCartIsEmpty() {

        // Arrange
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());

        when(cartRepository.findByUser_UserId(1L))
                .thenReturn(Optional.of(cart));

        // Act + Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderService.placeOrder(1L, 1L, PaymentMethod.COD)
        );

        // Verify message
        assertEquals("Cart is empty", exception.getMessage());
    }
    
    @Test
    void shouldStorePriceSnapshotCorrectly() {

        // =========================
        // Arrange
        // =========================

    	// Fake user
    	User user = new User();
    	user.setUserId(1L);

    	// Fake address
    	Address address = new Address();
    	address.setUser(user);

        // Fake menu item
        MenuItem burger = new MenuItem();
        burger.setMenuItemId(1L);
        burger.setName("Burger");
        burger.setPrice(BigDecimal.valueOf(150));

     // Fake restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(1L);
        
        // Fake cart item
        CartItem cartItem = new CartItem();
        cartItem.setMenuItem(burger);
        cartItem.setQuantity(2);
        
        // Fake cart
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setRestaurant(restaurant);
        //cart.setCartItems(List.of(cartItem));
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);

        cart.setCartItems(cartItems);
        
        // Mock repository calls
        when(cartRepository.findByUser_UserId(1L))
                .thenReturn(Optional.of(cart));

        when(addressRepository.findById(1L))
                .thenReturn(Optional.of(address));

        // Save returns same order
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // =========================
        // Act
        // =========================

        orderService.placeOrder(1L, 1L, PaymentMethod.COD);

        // =========================
        // Assert
        // =========================

        // Capture saved order
        ArgumentCaptor<OrderItem> orderItemCaptor =
                ArgumentCaptor.forClass(OrderItem.class);

        verify(orderItemRepository).save(orderItemCaptor.capture());

        OrderItem savedOrderItem =
                orderItemCaptor.getValue();

        // Verify snapshot stored correctly
        assertEquals(
                0,
                BigDecimal.valueOf(150)
                        .compareTo(savedOrderItem.getItemPrice())
        );
    }
}