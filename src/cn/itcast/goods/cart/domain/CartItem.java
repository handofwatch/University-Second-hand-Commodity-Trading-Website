package cn.itcast.goods.cart.domain;

import java.math.BigDecimal;

import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.user.domain.User;

public class CartItem {
	private String cartItemId;// 主键
	private Book goods;// 条目对应的图书
	private User user;// 所属用户

	// 添加小计方法
	public double getSubtotal() {
		/*
		 * 使用BigDecimal不会有误差
		 * 要求必须使用String类型构造器
		 */
		BigDecimal b1 = new BigDecimal(goods.getPrice() + "");
		BigDecimal b2 = new BigDecimal(1 + "");
		BigDecimal b3 = b1.multiply(b2);
		return b3.doubleValue();
	}

	public Book getGoods() {
		return goods;
	}

	public void setGoods(Book goods) {
		this.goods = goods;
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public static void main(String[] args) {
		System.out.println(2.0-1.1);//0.8999999999999999
	}//?
}
