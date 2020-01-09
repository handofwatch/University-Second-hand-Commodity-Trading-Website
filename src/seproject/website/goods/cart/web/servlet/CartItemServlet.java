package seproject.website.goods.cart.web.servlet;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.cart.domain.CartItem;
import seproject.website.goods.cart.service.CartItemService;
import seproject.website.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;

public class CartItemServlet extends BaseServlet {
	private CartItemService cartItemService = new CartItemService();

	public String loadCartItems(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cartItemIds = req.getParameter("cartItemIds");
		double total = Double.parseDouble(req.getParameter("total"));

		List<Goods> GoodsList = cartItemService.loadCartItems(cartItemIds);

		req.setAttribute("GoodsList", GoodsList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsps/cart/showitem.jsp";
	}

	public String batchDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取cartItemIds参数
		 * 2. 调用service方法完成工作
		 * 3. 返回到list.jsp
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds);
		return myCart(req, resp);
	}

	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map map = req.getParameterMap();
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Goods goods = CommonUtils.toBean(map, Goods.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		cartItem.setGoods(goods);
		cartItem.setUser(user);

		cartItemService.add(cartItem);

		return myCart(req, resp);
	}

	public String myCart(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		User user = (User)req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();

		List<CartItem> cartItemLIst = cartItemService.myCart(uid);

		req.setAttribute("cartItemList", cartItemLIst);
		return "f:/jsps/cart/list.jsp";
	}
}
