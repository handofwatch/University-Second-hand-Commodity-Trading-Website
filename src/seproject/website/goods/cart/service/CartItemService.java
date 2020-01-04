package seproject.website.goods.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import seproject.website.goods.cart.dao.CartItemDao;
import seproject.website.goods.cart.domain.CartItem;
import seproject.website.goods.goods.domain.Goods;

public class CartItemService {
	private CartItemDao cartItemDao = new CartItemDao();
	
	/*
	 * 加载多个CartItem
	 */
	public List<Goods> loadCartItems(String cartItemIds) {
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改购物车条目数量
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId, int quantity) {
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量删除功能
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 添加条目
	 * @param cartItem
	 */
	public String add(CartItem cartItem) {
		try {
			/*
			 * 1. 使用uid和gid去数据库中查询这个条目是否存在
			 */
			CartItem _cartItem = cartItemDao.findByUidAndGid(
					cartItem.getUser().getUid(),
					cartItem.getGoods().getGid());
			if(_cartItem == null) {//如果原来没有这个条目，那么添加条目
				cartItem.setCartItemId(CommonUtils.uuid());
				return cartItemDao.addCartItem(cartItem);
			}
			else {return cartItem.getCartItemId();}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 我的购物车功能
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid) {
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
