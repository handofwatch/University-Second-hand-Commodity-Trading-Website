package seproject.website.goods.goods.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

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

import java.awt.*;
import java.io.File;
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
        Goods goods = goodsService.load(gid);//通过gid得到book对象
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
        return "f:/adminjsps/admin/goods/add.jsp";
    }

    public void addGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        /*
         * 1. commons-fileupload的上传三步
         */
        // 创建工具
        FileItemFactory factory = new DiskFileItemFactory();
        /*
         * 2. 创建解析器对象
         */
        ServletFileUpload sfu = new ServletFileUpload(factory);
//		sfu.setFileSizeMax(80 * 1024);//设置单个上传的文件上限为80KB
        /*
         * 3. 解析request得到List<FileItem>
         */
        List<FileItem> fileItemList = null;
        try {
            fileItemList = sfu.parseRequest(req);
        } catch (FileUploadException e) {
            // 如果出现这个异步，说明单个文件超出了80KB
            error("上传的文件过大", req, resp);
            return;
        }

        /*
         * 4. 把List<FileItem>封装到Book对象中
         * 4.1 首先把“普通表单字段”放到一个Map中，再把Map转换成Book和Category对象，再建立两者的关系
         */
        Map<String,Object> map = new HashMap<String,Object>();
        for(FileItem fileItem : fileItemList) {
            if(fileItem.isFormField()) {//如果是普通表单字段
                map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
            }
        }
        Goods goods = CommonUtils.toBean(map, Goods.class);//把Map中大部分数据封装到Book对象中
        Category category = CommonUtils.toBean(map, Category.class);//把Map中cid封装到Category中
        goods.setCategory(category);

        /*
         * 4.2 把上传的图片保存起来
         *   > 获取文件名：截取之
         *   > 给文件添加前缀：使用uuid前缀，为也避免文件同名现象
         *   > 校验文件的扩展名：只能是jpg
         *   > 校验图片的尺寸
         *   > 指定图片的保存路径，这需要使用ServletContext#getRealPath()
         *   > 保存之
         *   > 把图片的路径设置给Book对象
         */
        // 获取文件名
        FileItem fileItem = fileItemList.get(1);//获取大图
        String filename = fileItem.getName();
        // 截取文件名，因为部分浏览器上传的绝对路径
        int index = filename.lastIndexOf("\\");
        if(index != -1) {
            filename = filename.substring(index + 1);
        }
        // 给文件名添加uuid前缀，避免文件同名现象
        filename = CommonUtils.uuid() + "_" + filename;
        // 校验文件名称的扩展名
        if(!filename.toLowerCase().endsWith(".jpg")) {
            error("上传的图片扩展名必须是JPG", req, resp);
            return;
        }
        // 校验图片的尺寸
        // 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
        /*
         * 保存图片：
         * 1. 获取真实路径
         */
        String savepath = this.getServletContext().getRealPath("/book_img");
        /*
         * 2. 创建目标文件
         */
        File destFile = new File(savepath, filename);
        /*
         * 3. 保存文件
         */
        try {
            fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 校验尺寸
        // 1. 使用文件路径创建ImageIcon
        ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
        // 2. 通过ImageIcon得到Image对象
        Image image = icon.getImage();
        // 3. 获取宽高来进行校验
        if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
            error("您上传的图片尺寸超出了350*350！", req, resp);
            destFile.delete();//删除图片
            return;
        }

        // 把图片的路径设置给book对象
        goods.setImage_w("book_img/" + filename);




        // 获取文件名
        fileItem = fileItemList.get(2);//获取小图
        filename = fileItem.getName();
        // 截取文件名，因为部分浏览器上传的绝对路径
        index = filename.lastIndexOf("\\");
        if(index != -1) {
            filename = filename.substring(index + 1);
        }
        // 给文件名添加uuid前缀，避免文件同名现象
        filename = CommonUtils.uuid() + "_" + filename;
        // 校验文件名称的扩展名
        if(!filename.toLowerCase().endsWith(".jpg")) {
            error("上传的图片扩展名必须是JPG", req, resp);
            return;
        }
        // 校验图片的尺寸
        // 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
        /*
         * 保存图片：
         * 1. 获取真实路径
         */
        savepath = this.getServletContext().getRealPath("/book_img");
        /*
         * 2. 创建目标文件
         */
        destFile = new File(savepath, filename);
        /*
         * 3. 保存文件
         */
        try {
            fileItem.write(destFile);//它会把临时文件重定向到指定的路径，再删除临时文件
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 校验尺寸
        // 1. 使用文件路径创建ImageIcon
        icon = new ImageIcon(destFile.getAbsolutePath());
        // 2. 通过ImageIcon得到Image对象
        image = icon.getImage();
        // 3. 获取宽高来进行校验
        if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
            error("您上传的图片尺寸超出了350*350！", req, resp);
            destFile.delete();//删除图片
            return;
        }

        // 把图片的路径设置给book对象
        goods.setImage_b("book_img/" + filename);




        // 调用service完成保存
        goods.setGid(CommonUtils.uuid());
        GoodsService goodsService = new GoodsService();
        goodsService.add(goods);

        // 保存成功信息转发到msg.jsp
        req.setAttribute("msg", "添加图书成功！");
        req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, resp);
    }
    private void error(String msg, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("msg", msg);
        request.setAttribute("parents", new CategoryService().findAll());//所有一级分类
        request.getRequestDispatcher("/adminjsps/admin/goods/add.jsp").
                forward(request, response);
    }

}

