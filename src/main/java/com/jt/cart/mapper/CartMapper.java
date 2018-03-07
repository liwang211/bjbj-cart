package com.jt.cart.mapper;

import java.util.List;

import com.jt.cart.pojo.Cart;
import com.bjbj.common.mapper.SysMapper;

public interface CartMapper extends SysMapper<Cart>{
	// 根据用户id查询购物车
	List<Cart> selectCartByUserId(Long userId);
	// 修改商品数量
	void updateCartNum(Cart cart);
	// 根据用户id和商品id 查询商品数量
	int selectNumByUserIdItemId(Cart cart);

}
