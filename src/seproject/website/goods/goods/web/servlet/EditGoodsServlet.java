package seproject.website.goods.goods.web.servlet;

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
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditGoodsServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        GoodsService goodsService = new GoodsService();

        FileItemFactory factory = new DiskFileItemFactory();

        ServletFileUpload sfu = new ServletFileUpload(factory);

        List<FileItem> fileItemList = null;
        try {
            fileItemList = sfu.parseRequest(req);
        } catch (FileUploadException e) {

            error("上传的文件过大", req, resp);
            return;
        }

        Map<String,Object> map = new HashMap<String,Object>();
        for(FileItem fileItem : fileItemList) {
            if(fileItem.isFormField()) {//如果是普通表单字段
                map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
            }
        }
        Goods goods = CommonUtils.toBean(map, Goods.class);//把Map中大部分数据封装到Book对象中
        Category category = CommonUtils.toBean(map, Category.class);//把Map中cid封装到Category中
        goods.setCategory(category);


        Goods oldgoods = goodsService.load(goods.getGid());

        FileItem fileItem = fileItemList.get(3);//获取大图
        if(fileItem.getSize()>0){
            String temporyname = CommonUtils.uuid() + ".jpg";//临时名字
            String filename = CommonUtils.uuid() + ".jpg";//真正名字

            String savepath = this.getServletContext().getRealPath("/goods_img");

            File file = new File(savepath, temporyname);

            try {
                fileItem.write(file);//它会把临时文件重定向到指定的路径，再删除临时文件
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!modifyPic(savepath, temporyname, filename, 350, 350)) {
                error("您的图片修改尺寸失败", req, resp);

                return;
            }

            // 把图片的路径设置给Goods对象
            goods.setImage_w("goods_img/" + filename);

            //将第一张图片缩小作为缩略图
            filename = CommonUtils.uuid() + ".jpg";

            if (!modifyPic(savepath, temporyname, filename, 200, 200)) {
                error("您的图片修改尺寸失败", req, resp);
            }

            goods.setImage_b("goods_img/" + filename);

            file.delete();
        }
        else {
            goods.setImage_w(oldgoods.getImage_w());
            goods.setImage_b(oldgoods.getImage_b());
        }
// 获取文件名
        fileItem = fileItemList.get(2);//获取大图2
        if(fileItem.getSize() >0) {
            String temporyname1 = CommonUtils.uuid() + ".jpg";//临时名字
            String filename1 = CommonUtils.uuid() + ".jpg";//真正名字
            String savepath1 = this.getServletContext().getRealPath("/goods_img");

            File file1 = new File(savepath1, temporyname1);

            try {
                fileItem.write(file1);//它会把临时文件重定向到指定的路径，再删除临时文件
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!modifyPic(savepath1, temporyname1, filename1, 350, 350)) {
                error("您的图片修改尺寸失败", req, resp);
            }



            //删除文件
            file1.delete();
            // 把图片的路径设置给Goods对象
            goods.setImage_w2("goods_img/" + filename1);
        }
        else{
            goods.setImage_w2(oldgoods.getImage_w2());
        }




        // 调用service完成保存
        goodsService.edit(goods);

        // 保存成功信息转发到msg.jsp，要提示成功
        req.setAttribute("msg", "编辑商品成功！");

    }

    private void error(String msg, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("msg", msg);
        request.setAttribute("category", new CategoryService().findAll());//所有一级分类
        request.getRequestDispatcher("/jsps/goods/add.jsp").
                forward(request, response);
    }

    private boolean modifyPic(String savepath, String temporaryname,String filename, int width, int height){
        temporaryname = savepath + "/" + temporaryname;
        System.out.println(temporaryname);
        File oldFile = new File(temporaryname);
        filename = savepath + "/" + filename;
        try{
            Image srcFile = ImageIO.read(oldFile);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height,null);
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
            ImageIO.write(tag, "jpg", out);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return  true;

    }
}
