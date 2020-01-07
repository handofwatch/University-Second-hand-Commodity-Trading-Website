package seproject.website.goods.admin.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.goods.service.GoodsService;
import seproject.website.goods.order.domain.Order;
import seproject.website.goods.order.service.OrderService;
import seproject.website.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	
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
	 * 查看所有订单
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
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
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Order> pb = orderService.findAll(pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/list.jsp";
	}
	
//	/**
//	 * 按状态查询
//	 * @param req
//	 * @param resp
//	 * @return
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	public String findByStatus(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		/*
//		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
//		 */
//		int pc = getPc(req);
//		/*
//		 * 2. 得到url：...
//		 */
//		String url = getUrl(req);
//		/*
//		 * 3. 获取链接参数：status
//		 */
//		int status = Integer.parseInt(req.getParameter("status"));
//		/*
//		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
//		 */
//		PageBean<Order> pb = orderService.findByStatus(status, pc);
//		/*
//		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
//		 */
//		pb.setUrl(url);
//		req.setAttribute("pb", pb);
//		return "f:/adminjsps/admin/order/list.jsp";
//	}
//

	public String updateOrderStatus(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String itemid = req.getParameter("orderItemId");
		orderService.updateStatus(itemid,5);
		String gid = orderService.findGid(itemid);
		GoodsService goodsService = new GoodsService();
		goodsService.upgstatus(gid, 5);
		req.setAttribute("msg", "修改状态成功！");
		return "f:/adminjsps/msg.jsp";
	}

}
