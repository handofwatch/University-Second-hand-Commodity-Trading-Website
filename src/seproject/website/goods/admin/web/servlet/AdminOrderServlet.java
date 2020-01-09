package seproject.website.goods.admin.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import seproject.website.goods.goods.service.GoodsService;
import seproject.website.goods.order.domain.Order;
import seproject.website.goods.order.service.OrderService;
import seproject.website.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();

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

	private String getUrl(HttpServletRequest req) {
		String url = req.getRequestURI() + "?" + req.getQueryString();

		int index = url.lastIndexOf("&pc=");
		if(index != -1) {
			url = url.substring(0, index);
		}
		return url;
	}

	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int pc = getPc(req);

		String url = getUrl(req);

		PageBean<Order> pb = orderService.findAll(pc);
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/list.jsp";
	}

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
