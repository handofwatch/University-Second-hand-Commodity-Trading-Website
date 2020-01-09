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

	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("category", categoryService.findAll());
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Category category= CommonUtils.toBean(req.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());//设置cid
		
		categoryService.add(category);
		return findAll(req, resp);
	}

	public String editPre(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cid = req.getParameter("cid");
		Category category= categoryService.load(cid);
		req.setAttribute("category", category);
		return "f:/adminjsps/admin/category/edit.jsp";
	}

	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Category category = CommonUtils.toBean(req.getParameterMap(), Category.class);
		categoryService.edit(category);
		return findAll(req, resp);
	}


	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String cid = req.getParameter("cid");
		categoryService.delete(cid);

		return findAll(req, resp);

	}
}
