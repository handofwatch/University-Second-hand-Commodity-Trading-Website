package seproject.website.goods.goods.domain;

import seproject.website.goods.category.domain.Category;
import seproject.website.goods.user.domain.User;
public class Goods {
	private String gid;//主键
	private String gname;//图名
	private int gstatus;//商品状态，与订单项状态相一致
	private String image_w2;
	private Category category;//所属分类
	private String image_w;//大图路径
	private String image_b;//小图路径
	private String gdesc;
	private double price;;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGdesc() {
		return gdesc;
	}

	public void setGdesc(String gdesc) {
		this.gdesc = gdesc;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public int getGstatus() {
		return gstatus;
	}

	public void setGstatus(int gstatus) {
		this.gstatus = gstatus;
	}

	public String getImage_w() {
		return image_w;
	}

	public void setImage_w(String image_w) {
		this.image_w = image_w;
	}

	public String getImage_w2() {
		return image_w2;
	}

	public void setImage_w2(String image_w2) {
		this.image_w2 = image_w2;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getImage_b() {
		return image_b;
	}

	public void setImage_b(String image_b) {
		this.image_b = image_b;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
