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
        /*
         * 2. 创建解析器对象
         */
        ServletFileUpload sfu = new ServletFileUpload(factory);
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


        Goods oldgoods = goodsService.load(goods.getGid());
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

        FileItem fileItem = fileItemList.get(3);//获取大图
        if(fileItem.getSize()>0){
            String temporyname = CommonUtils.uuid() + ".jpg";//临时名字
            String filename = CommonUtils.uuid() + ".jpg";//真正名字
            // 校验文件名称的扩展名
//        if(!filename.toLowerCase().endsWith(".jpg")) {
//            error("上传的图片扩展名必须是JPG", req, resp);
//            return;
//        }
            // 校验图片的尺寸
            // 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
            /*
             * 保存图片：
             * 1. 获取真实路径
             */
            String savepath = this.getServletContext().getRealPath("/goods_img");
            /*
             * 2. 创建临时文件（未压缩）
             */
            File file = new File(savepath, temporyname);
            /*
             * 3. 保存文件
             */
            try {
                fileItem.write(file);//它会把临时文件重定向到指定的路径，再删除临时文件
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!modifyPic(savepath, temporyname, filename, 350, 350)) {
                error("您的图片修改尺寸失败", req, resp);

                return;
            }
//        // 校验尺寸
//        // 1. 使用文件路径创建ImageIcon
//        ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
//        // 2. 通过ImageIcon得到Image对象
//        Image image = icon.getImage();
//        // 3. 获取宽高来进行校验
//        if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
//            error("您上传的图片尺寸超出了350*350！", req, resp);
//            destFile.delete();//删除图片
//            return;
//        }

            // 把图片的路径设置给book对象
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
            // 校验文件名称的扩展名
//        if(!filename.toLowerCase().endsWith(".jpg")) {
//            error("上传的图片扩展名必须是JPG", req, resp);
//            return;
//        }
            // 校验图片的尺寸
            // 保存上传的图片，把图片new成图片对象：Image、Icon、ImageIcon、BufferedImage、ImageIO
            /*
             * 保存图片：
             * 1. 获取真实路径
             */

            String savepath1 = this.getServletContext().getRealPath("/goods_img");
            /*
             * 2. 创建临时文件（未压缩）
             */
            File file1 = new File(savepath1, temporyname1);
            /*
             * 3. 保存文件
             */
            try {
                fileItem.write(file1);//它会把临时文件重定向到指定的路径，再删除临时文件
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (!modifyPic(savepath1, temporyname1, filename1, 350, 350)) {
                error("您的图片修改尺寸失败", req, resp);
            }
//        // 校验尺寸
//        // 1. 使用文件路径创建ImageIcon
//        ImageIcon icon = new ImageIcon(destFile.getAbsolutePath());
//        // 2. 通过ImageIcon得到Image对象
//        Image image = icon.getImage();
//        // 3. 获取宽高来进行校验
//        if(image.getWidth(null) > 350 || image.getHeight(null) > 350) {
//            error("您上传的图片尺寸超出了350*350！", req, resp);
//            destFile.delete();//删除图片
//            return;
//        }


            //删除文件
            file1.delete();
            // 把图片的路径设置给book对象
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
