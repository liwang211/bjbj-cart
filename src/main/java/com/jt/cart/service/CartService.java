package com.jt.cart.service;

import java.util.List;

import com.jt.cart.pojo.Cart;

public interface CartService {
	
	/**
	 * 根据用户Id查询购物车信息
	 * @param userId 用户id
	 * @return 购物车信息列表
	 */
	List<Cart> findCartByUserId(Long userId);
	/**
	 * 修改商品数量
	 * @param cart 内包含userId itemId num
	 */
	void updateCartNum(Cart cart);
	/**
	 * 删除商品从购物车
	 * @param cart
	 */
	void deleteCart(Cart cart);
	/**
	 * 新增商品到购物车
	 * @param userId 用户id
	 * @param itemId 商品id
	 * @param cart	 商品数量
	 * @return 
	 */
	int saveCart(Cart cart);

}
