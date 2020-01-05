package seproject.website.goods.admin.category.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import seproject.website.goods.goods.service.GoodsService;
import seproject.website.goods.category.domain.Category;
import seproject.website.goods.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	private GoodsService goodsService = new GoodsService();
	
	/**
	 * 查询所有分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("category", categoryService.findAll());
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Category中
		 * 2. 需要手动的把表单中的pid映射到child对象中
		 * 2. 调用service的add()方法完成添加
		 * 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		Category category= CommonUtils.toBean(req.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());//设置cid
		
		categoryService.add(category);
		return findAll(req, resp);
	}
	
	/**
	 * 修改分类：第一步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取链接中的cid
		 * 2. 使用cid加载Category
		 * 3. 保存Category
		 * 4. 转发到edit.jsp页面显示Category
		 */
		String cid = req.getParameter("cid");
		Category category= categoryService.load(cid);
		req.setAttribute("category", category);
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	
	/**
	 * 修改分类：第二步
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Category中
		 * 2. 调用service方法完成修改
		 * 3. 转发到list.jsp显示所有分类（return findAll();）
		 */
		Category category = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(category);
		return findAll(req, resp);
	}

	/**
	 * 删除分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cid = req.getParameter("cid");
		categoryService.delete(cid);

		return findAll(req, resp);
//		int cnt = goodsService.findBookCountByCategory(cid);
//		if(cnt > 0) {
//			req.setAttribute("msg", "该分类下还存在图书，不能删除！");
//			return "f:/adminjsps/msg.jsp";

	}
}
