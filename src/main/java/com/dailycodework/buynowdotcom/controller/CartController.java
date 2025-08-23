package com.dailycodework.buynowdotcom.controller;

import com.dailycodework.buynowdotcom.model.Cart;
import com.dailycodework.buynowdotcom.model.User;
import com.dailycodework.buynowdotcom.response.ApiResponse;
import com.dailycodework.buynowdotcom.service.cart.ICartService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@RequestMapping("${api.prefix}/carts")
@RequestMapping("/api/v1/carts")
public class CartController {

    private final ICartService cartService;
    private final ModelMapper mapper;

    @GetMapping("/user/{userId}/cart")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("Success",cart));
    }

    @DeleteMapping("/cart/{cartId}/clear")
    public Void clearCart(@PathVariable Long cartId){
        cartService.clearCart(cartId);
        return null;
    }
}
