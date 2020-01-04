package seproject.website.goods.order.web.servlet;



import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import seproject.website.goods.cart.domain.CartItem;
import seproject.website.goods.cart.service.CartItemService;
import seproject.website.goods.goods.service.GoodsService;
import seproject.website.goods.order.domain.Order;
import seproject.website.goods.order.domain.OrderItem;
import seproject.website.goods.order.service.OrderService;
import seproject.website.goods.pager.PageBean;
import seproject.website.goods.user.domain.User;
import cn.itcast.servlet.BaseServlet;
import seproject.website.goods.goods.domain.Goods;

public class OrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	private CartItemService cartItemService = new CartItemService();
	private GoodsService goodsService = new GoodsService();

	/**
	 * 获取当前页码
	 * @param req
	 * @return
	 */
	private int getPc(HttpServletRequest req) {
		int pc = 1;
		String param = req.getParameter("pc");
		if(param != null && !param.trim().isEmpty()) {
			try {
				pc = Integer.parseInt(param);
			} catch(RuntimeException e) {}
		}
		return pc;
	}
	
	/**
	 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=3
	 * /goods/GoodsServlet + methed=findByCategory&cid=xxx&pc=3
	 */
	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();
		/*
		 * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
		 */
		int index = url.lastIndexOf("&pc=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}
	


	/**
	 * 支付
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String payment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		for(OrderItem orderitem : order.getOrderItemList() ) {
			String itemId = orderitem.getOrderItemId();
			String gid = orderService.findGid(orderitem.getOrderItemId());
			/*
			 * 校验订单状态
			 */
			int status = orderService.findStatus(itemId);
			if (status != 1) {
				req.setAttribute("code", "error");
				req.setAttribute("msg", "状态不对，不能支付！");
				return "f:/jsps/msg.jsp";
			}
			orderService.updateStatus(itemId, 2);//设置状态为等待卖家发货！
			goodsService.upgstatus(gid, 2);
		}
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已成功支付！");
		return "f:/jsps/msg.jsp";

	}
	
	/**
	 * 取消订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String cancel(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String itemId = req.getParameter("orderItemId");
		String gid = orderService.findGid(itemId);
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(itemId);
		if(status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(itemId, 5);//设置状态为取消！
		goodsService.upgstatus(gid, 5);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "您的订单已取消！");
		return "f:/jsps/msg.jsp";		
	}
	
	/**
	 * 确认收货
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String itemId = req.getParameter("orderItemId");
		String gid = orderService.findGid(itemId);
		/*
		 * 校验订单状态
		 */
		System.out.println(itemId);
		int status = orderService.findStatus(itemId);
		System.out.println(status);
		if(status != 3) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能确认收货！");
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(itemId, 4);//设置状态为交易成功！
		goodsService.upgstatus(gid, 4);
		req.setAttribute("code", "success");
		req.setAttribute("msg", "恭喜，交易成功！");
		return "f:/jsps/msg.jsp";		
	}
	
	/**
	 * 加载订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");//btn说明了用户点击哪个超链接来访问本方法的
		req.setAttribute("btn", btn);
		return "/jsps/order/desc.jsp";
	}
	
	/**
	 * 生成订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String createOrder(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取所有购物车条目的id，查询之
		 */
		String cartItemIds = req.getParameter("cartitemids");
		List<Goods> GoodsList = cartItemService.loadCartItems(cartItemIds);
		/*
		 * 2. 创建Order
		 */
		Order order = new Order();
		order.setOid(CommonUtils.uuid());//设置主键
		order.setOrdertime(String.format("%tF %<tT", new Date()));//下单时间
		order.setAddress(req.getParameter("address"));//设置收货地址
		order.setPhone(req.getParameter("phone"));//设置手机号
		order.setBuyername(req.getParameter("buyername"));
		User owner = (User)req.getSession().getAttribute("sessionUser");
		order.setOwner(owner);//设置订单所有者
		
		double total = 0;
		for(Goods goods : GoodsList) {
			total = goods.getPrice();
		}
		order.setTotal(total);//设置总计
		
		/*
		 * 3. 创建List<OrderItem>
		 * 一个CartItem对应一个OrderItem
		 */
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Goods goods : GoodsList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(CommonUtils.uuid());//设置主键
			orderItem.setPrice(goods.getPrice());
			orderItem.setGoods(goods);
			orderItem.setOrder(order);
			orderItem.setAddress(order.getAddress());
			orderItem.setBuyername(order.getBuyername());
			orderItem.setPhone(order.getPhone());
			orderItem.setOrderstatus(1);
			orderItemList.add(orderItem);
			goodsService.upgstatus(goods.getGid(), 1 );
		}
		order.setOrderItemList(orderItemList);
		
		/*
		 * 4. 调用service完成添加
		 */
		orderService.createOrder(order);
		
		// 删除购物车条目
		cartItemService.batchDelete(cartItemIds);
		/*
		 * 5. 保存订单，转发到ordersucc.jsp
		 */
		req.setAttribute("order", order);
		return "f:/jsps/order/ordersucc.jsp";
	}
	
	/**
	 * 我的订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrders(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 从当前session中获取User
		 */
		User user = (User)req.getSession().getAttribute("sessionUser");
		
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/order/list.jsp";
	}
	public String buyNow(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map map = req.getParameterMap();
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		String gid = req.getParameter("gid");
		User user = (User)req.getSession().getAttribute("sessionUser");
		Goods goods = goodsService.findByGid(gid);
		cartItem.setGoods(goods);
		cartItem.setUser(user);
		String cartItemId = cartItemService.add(cartItem);
		List<Goods> goodsList = new ArrayList<>();
		goodsList.add(cartItem.getGoods());
		req.setAttribute("GoodsList", goodsList);
		req.setAttribute("cartItemIds",cartItemId);

		return "f:/jsps/cart/showitem.jsp";
	}

	public String sendout(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String gid = req.getParameter("gid");
		Goods goods = goodsService.findByGid(gid);
		OrderItem orderitem = orderService.findByGid(gid);
		orderService.updateStatus(orderitem.getOrderItemId(), 3);
		goodsService.upgstatus(gid, 3);
		req.setAttribute("msg", "您已成功修改状态");

		return "f:/jsps/msg.jsp";
	}
}
