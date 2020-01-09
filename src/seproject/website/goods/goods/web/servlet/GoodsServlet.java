package seproject.website.goods.goods.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import seproject.website.goods.category.domain.Category;
import seproject.website.goods.category.service.CategoryService;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.goods.service.GoodsService;
import seproject.website.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;
import seproject.website.goods.user.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GoodsServlet extends BaseServlet {
    private GoodsService goodsService = new GoodsService();
    private CategoryService categoryService = new CategoryService();


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

    public String load(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String gid = req.getParameter("gid");//获取链接的参数bid
        Goods goods = goodsService.load(gid);//通过gid得到goods对象
        req.setAttribute("goods", goods);//保存到req中
        return "f:/jsps/goods/desc.jsp";//转发到desc.jsp
    }

    public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int pc = getPc(req);

        String url = getUrl(req);

        String cid = req.getParameter("cid");

        PageBean<Goods> pb = goodsService.findByCategory(cid, pc);

        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/goods/list.jsp";
    }


    public String findByBname(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int pc = getPc(req);

        String url = getUrl(req);

        String gname = req.getParameter("bname");

        PageBean<Goods> pb = goodsService.findByGname(gname, pc);

        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/goods/list.jsp";
    }
    public String addPre(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> category = categoryService.findAll();
        req.setAttribute("category", category);
        return "f:/jsps/goods/add.jsp";
    }

    public String myGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int pc = getPc(req);

        String url = getUrl(req);

        User user = (User)req.getSession().getAttribute("sessionUser");
        String uid = user.getUid();

        PageBean<Goods> goodsList = goodsService.myGoods(uid, pc);

        req.setAttribute("goodsList", goodsList);

        goodsList.setUrl(url);

        return "f:/jsps/goods/myGoods.jsp";
    }


    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String gid = req.getParameter("gid");

        Goods goods = goodsService.load(gid);
        String savepath = this.getServletContext().getRealPath("/");//获取真实的路径
        new File(savepath, goods.getImage_w()).delete();//删除文件
        new File(savepath, goods.getImage_w2()).delete();//删除文件
        new File(savepath, goods.getImage_b()).delete();//删除文件

        goodsService.delete(gid);//删除数据库的记录

        req.setAttribute("msg", "删除图书成功！");
        return "f:/jsps/goods/list.jsp";
    }

    public String editpre(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String gid = req.getParameter("gid");
        Goods goods = goodsService.load(gid);
        req.setAttribute("goods", goods);
        List<Category> category = categoryService.findAll();
        req.setAttribute("category", category);
        return "/jsps/goods/edit.jsp";
    }



}

