package com.jt.cart.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.bjbj.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 根据用户id查询购物车信息 url:http://cart.jt.com/cart/query/{userId}
	 */
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable Long userId) {

		try {
			// 获取cartList数据
			List<Cart> cartList = cartService.findCartByUserId(userId);

			String JSONResult = objectMapper.writeValueAsString(cartList);
			return SysResult.oK(JSONResult);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "购物车信息查询失败");
		}

	}

	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num) {

		try {
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);

			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "修改数量失败");

		}
	}

	/**
	 * 编辑商品的删除
	 * 
	 */
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId, @PathVariable Long itemId) {
		try {
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);

			cartService.deleteCart(cart);

			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除失败");
		}
	}

	
	@RequestMapping(value = "/save" )
	@ResponseBody
	public SysResult addCart(Cart cart) {
		
		
		try {
			
			int status = cartService.saveCart(cart);
			return SysResult.build(status, "添加成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "添加失败");
		}
	}
}
