package com.dailycodework.buynowdotcom.controller;

import com.dailycodework.buynowdotcom.dtos.UserDto;
import com.dailycodework.buynowdotcom.model.Cart;
import com.dailycodework.buynowdotcom.model.User;
import com.dailycodework.buynowdotcom.response.ApiResponse;
import com.dailycodework.buynowdotcom.service.cart.ICartItemService;
import com.dailycodework.buynowdotcom.service.cart.ICartService;
import com.dailycodework.buynowdotcom.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final IUserService userService;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @PostMapping("/item/add/{userId}")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @PathVariable Long userId,
                                                     @RequestParam Long productId,
                                                     @RequestParam int quantity ){

        UserDto userDto = userService.getUserById(userId);
        User user = modelMapper.map(userDto, User.class);
        Cart cart = cartService.initializeNewCartForUser(user);
        cartItemService.addItemToCart(cart.getId(),productId,quantity);
        return ResponseEntity.ok(new ApiResponse("item added successfully", null));
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long itemId){

        cartItemService.removeItemFromCart(cartId,itemId);
        return ResponseEntity.ok(new ApiResponse("item removed!",null));
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId,
                                                      @PathVariable Long itemId,
                                                      @RequestParam int quantity){

        cartItemService.updateItemQuantity(cartId,itemId,quantity);
        return ResponseEntity.ok(new ApiResponse("item updated successfully!",null));
    }
}