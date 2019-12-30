package cn.itcast.goods.category.domain;

import java.util.List;

public class Category {
	private String cid;// 主键
	private String cname;// 分类名称
	private String cdesc;// 分类描述

	public String getCid() {
		return cid;
	}

	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
