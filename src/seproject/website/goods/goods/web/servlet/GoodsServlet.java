package seproject.website.goods.goods.web.servlet;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import seproject.website.goods.category.domain.Category;
import seproject.website.goods.category.service.CategoryService;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.goods.service.GoodsService;
import seproject.website.goods.pager.PageBean;
import cn.itcast.servlet.BaseServlet;
import seproject.website.goods.user.domain.User;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsServlet extends BaseServlet {
    private GoodsService goodsService = new GoodsService();
    private CategoryService categoryService = new CategoryService();

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
     * 按bid查询
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String gid = req.getParameter("gid");//获取链接的参数bid
        Goods goods = goodsService.load(gid);//通过gid得到goods对象
        req.setAttribute("goods", goods);//保存到req中
        return "f:/jsps/goods/desc.jsp";//转发到desc.jsp
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
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Goods> pb = goodsService.findByCategory(cid, pc);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/goods/list.jsp";
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
        String gname = req.getParameter("bname");
        /*
         * 4. 使用pc和cid调用service#findByCategory得到PageBean
         */
        PageBean<Goods> pb = goodsService.findByGname(gname, pc);
        /*
         * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
         */
        pb.setUrl(url);
        req.setAttribute("pb", pb);
        return "f:/jsps/goods/list.jsp";
    }
    public String addPre(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. 获取所有分类
         * 2. 转发到add.jsp，该页面会在下拉列表中显示所有分类
         */
        List<Category> category = categoryService.findAll();
        req.setAttribute("category", category);
        return "f:/jsps/goods/add.jsp";
    }

    public String myGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int pc = getPc(req);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        /*
         * 1. 得到uid
         */
        User user = (User)req.getSession().getAttribute("sessionUser");
        String uid = user.getUid();
        /*
         * 2. 通过service得到当前用户的所有购物车条目
         */
        PageBean<Goods> goodsList = goodsService.myGoods(uid, pc);
        /*
         * 3. 保存起来，转发到/cart/list.jsp
         */
        req.setAttribute("goodsList", goodsList);

        goodsList.setUrl(url);

        return "f:/jsps/goods/myGoods.jsp";
    }


    public String delete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String gid = req.getParameter("gid");

        /*
         * 删除图片
         */
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

