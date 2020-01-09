package seproject.website.goods.cart.domain;

import java.math.BigDecimal;

import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.user.domain.User;

public class CartItem {
	private String cartItemId;// 主键
	private Goods goods;// 条目对应的图书
	private User user;// 所属用户

	// 添加小计方法
	public double getSubtotal() {

		BigDecimal b1 = new BigDecimal(goods.getPrice() + "");
		BigDecimal b2 = new BigDecimal(1 + "");
		BigDecimal b3 = b1.multiply(b2);
		return b3.doubleValue();
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
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
		System.out.println(2.0-1.1);
}}
