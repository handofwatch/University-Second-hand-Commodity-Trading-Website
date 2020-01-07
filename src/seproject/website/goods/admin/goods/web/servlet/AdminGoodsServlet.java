package seproject.website.goods.admin.goods.web.servlet;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
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
import seproject.website.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;

public class AdminGoodsServlet extends BaseServlet {
	private GoodsService goodsService = new GoodsService();
	private CategoryService  categoryService = new CategoryService();
	
	/**
	 * 删除图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String gid = req.getParameter("gid");
		
		/*
		 * 删除图片
		 */
		Goods goods = goodsService.load(gid);
		String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
		new File(savepath, goods.getImage_w()).delete();//删除文件
		new File(savepath, goods.getImage_b()).delete();//删除文件
		new File(savepath, goods.getImage_w2()).delete();//删除文件
		
		goodsService.delete(gid);//删除数据库的记录

		String email = goods.getUser().getEmail();

		sendemail(email);
		
		req.setAttribute("msg", "删除商品成功！");
		return "f:/adminjsps/msg.jsp";
	}
	
	/**
	 * 修改图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 把表单数据封装到Book对象中
		 * 2. 封装cid到Category中
		 * 3. 把Category赋给Book
		 * 4. 调用service完成工作
		 * 5. 保存成功信息，转发到msg.jsp
		 */
		Map map = req.getParameterMap();
		Goods goods = CommonUtils.toBean(map, Goods.class);
		Category category = CommonUtils.toBean(map, Category.class);
		goods.setCategory(category);
		
		goodsService.edit(goods);
		req.setAttribute("msg", "修改图书成功！");
		return "f:/adminjsps/msg.jsp";
	}
	
	/**
	 * 加载图书
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取bid，得到Book对象，保存之
		 */
		String gid = req.getParameter("gid");
		Goods goods = goodsService.load(gid);
		req.setAttribute("goods", goods);
		

		req.setAttribute("category", categoryService.findAll());
		/*
		 * 3. 获取当前图书所属的一级分类下所有2级分类
		 */
		
		/*
		 * 4. 转发到desc.jsp显示
		 */
		return "f:/adminjsps/admin/goods/desc.jsp";
	}

	// {"cid":"fdsafdsa", "cname":"fdsafdas"}
	private String toJson(Category category) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"cid\"").append(":").append("\"").append(category.getCid()).append("\"");
		sb.append(",");
		sb.append("\"cname\"").append(":").append("\"").append(category.getCname()).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	// [{"cid":"fdsafdsa", "cname":"fdsafdas"}, {"cid":"fdsafdsa", "cname":"fdsafdas"}]
	private String toJson(List<Category> categoryList) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i < categoryList.size(); i++) {
			sb.append(toJson(categoryList.get(i)));
			if(i < categoryList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 显示所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findCategoryAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 通过service得到所有的分类
		 * 2. 保存到request中，转发到left.jsp
		 */
		List<Category> categoryList = categoryService.findAll();
		req.setAttribute("category", categoryList);
		return "f:/adminjsps/admin/goods/left.jsp";
	}

	
	
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
	 * 按分类查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String cid = req.getParameter("cid");


		PageBean<Goods> pb = goodsService.findByCategory(cid, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/goods/list.jsp";
	}
	

	

	/**
	 * 按图名查
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
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
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String bname = req.getParameter("gname");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Goods> pb = goodsService.findByGname(bname, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/adminjsps/admin/goods/list.jsp";
	}

//	public String editpre(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		String gid = req.getParameter("gid");
//		Goods goods = goodsService.load(gid);
//		req.setAttribute("goods", goods);
//		List<Category> category = categoryService.findAll();
//		req.setAttribute("category", category);
//		return "/adminjsps/admin/goods/edit.jsp";
//	}

	private void sendemail(String email){
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		/*
		 * 登录邮件服务器，得到session
		 */
		String host = prop.getProperty("host");//服务器主机名
		String name = prop.getProperty("username");//登录名
		String pass = prop.getProperty("password");//登录密码
		Session session = MailUtils.createSession(host, name, pass);

		/*
		 * 创建Mail对象
		 */
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
