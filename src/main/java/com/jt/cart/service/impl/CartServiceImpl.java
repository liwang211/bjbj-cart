package com.jt.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.bjbj.common.service.BaseService;

import redis.clients.jedis.JedisCluster;

@Service
public class CartServiceImpl extends BaseService<Cart> implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private JedisCluster jedisCluster;

	@Override
	public List<Cart> findCartByUserId(Long userId) {

		Cart cart = new Cart();

		cart.setUserId(userId);
		// 通过通用mapper查询购物车信息
		List<Cart> cartList = cartMapper.select(cart);
		return cartList;
	}

	@Override
	public void updateCartNum(Cart cart) {

		// 添加修改时间
		cart.setUpdated(new Date());
		cartMapper.updateCartNum(cart);

	}

	@Override
	public void deleteCart(Cart cart) {

		super.deleteByWhere(cart);

	}

	@Override
	public int saveCart(Cart cart) {

		// 封装数据

		int num = cart.getNum();
		cart.setNum(null);
		cart.setUpdated(new Date());
		int oldNum = 0;
		try {
			// 先根据userId和itemId查询是否存在 如果返回购物车中商品的数量
			oldNum = cartMapper.selectNumByUserIdItemId(cart);
			cart.setNum(num + oldNum);
			// 更新数据
			cartMapper.updateCartNum(cart);
			return 202;
		} catch (Exception e) {
			if (oldNum == 0) {
				try {
					cart.setCreated(cart.getUpdated());
					cart.setNum(num);
					cartMapper.insert(cart);
					return 200;
				} catch (Exception e2) {
					e2.printStackTrace();
					return 201;
				}
			} else {
				return 201;
			}

		}
		
		

	}
}
