package seproject.website.goods.admin.goods.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.goods.service.GoodsService;
import seproject.website.goods.category.domain.Category;
import seproject.website.goods.category.service.CategoryService;
import seproject.website.goods.order.domain.OrderItem;
import seproject.website.goods.order.service.OrderService;
import seproject.website.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

public class AdminGoodsServlet extends BaseServlet {
	private GoodsService goodsService = new GoodsService();
	private CategoryService  categoryService = new CategoryService();

	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String gid = req.getParameter("gid");

		Goods goods = goodsService.load(gid);
		String savepath = this.getServletContext().getRealPath("/");//获取路径
		new File(savepath, goods.getImage_w()).delete();//删除文件
		new File(savepath, goods.getImage_b()).delete();//删除文件
		new File(savepath, goods.getImage_w2()).delete();//删除文件
		
		goodsService.delete(gid);//删除数据库的记录

		String email = goods.getUser().getEmail();

		sendemail(email);
		OrderService orderService = new OrderService();
		OrderItem orderitem = orderService.findByGid(gid);
		orderService.updateStatus(orderitem.getOrderItemId(),5);

		
		req.setAttribute("msg", "删除商品成功！");
		return "f:/adminjsps/msg.jsp";
	}

	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map map = req.getParameterMap();
		Goods goods = CommonUtils.toBean(map, Goods.class);
		Category category = CommonUtils.toBean(map, Category.class);
		goods.setCategory(category);
		
		goodsService.edit(goods);
		req.setAttribute("msg", "修改图书成功！");
		return "f:/adminjsps/msg.jsp";
	}

	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String gid = req.getParameter("gid");
		Goods goods = goodsService.load(gid);
		req.setAttribute("goods", goods);
		

		req.setAttribute("category", categoryService.findAll());
		return "f:/adminjsps/admin/goods/desc.jsp";
	}

	public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Category> categoryList = categoryService.findAll();
		req.setAttribute("category", categoryList);
		return "f:/adminjsps/admin/goods/left.jsp";
	}

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

	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int pc = getPc(req);

		String url = getUrl(req);

		String cid = req.getParameter("cid");


		PageBean<Goods> pb = goodsService.findByCategory(cid, pc);

		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/goods/list.jsp";
	}
	


	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int pc = getPc(req);

		String url = getUrl(req);

		String bname = req.getParameter("gname");

		PageBean<Goods> pb = goodsService.findByGname(bname, pc);

		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/goods/list.jsp";
	}


	private void sendemail(String email){
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}

		String host = prop.getProperty("host");//服务器主机名
		String name = prop.getProperty("username");//登录名
		String pass = prop.getProperty("password");//登录密码
		Session session = MailUtils.createSession(host, name, pass);

		String from = prop.getProperty("from");
		String subject = prop.getProperty("subjectdelete");
		String content = prop.getProperty("contentdelete");
		Mail mail = new Mail(from, email, subject, content);

		try {
			MailUtils.send(session, mail);
		} catch (MessagingException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
