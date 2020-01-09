package seproject.website.goods.goods.service;

import java.sql.SQLException;
import java.util.List;

import seproject.website.goods.goods.dao.GoodsDao;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.pager.PageBean;

public class GoodsService {
	private GoodsDao goodsDao = new GoodsDao();
	

	public void delete(String gid) {
		try {
			goodsDao.delete(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public void edit(Goods goods) {
		try {
			goodsDao.edit(goods);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Goods load(String gid) {
		try {
			return goodsDao.findByGid(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public PageBean<Goods> findByCategory(String cid, int pc) {
		try {
			return goodsDao.findByCategory(cid, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Goods> findByGname(String gname, int pc) {
		try {
			return goodsDao.findByGname(gname, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Goods goods) {
		try {
			goodsDao.add(goods);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Goods findByGid(String gid) {
		try {
			return goodsDao.findByGid(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public PageBean<Goods> myGoods(String userId, int pc){
		try {
			return goodsDao.findByUid(userId, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void upgstatus(String gid, int gstatus){
		try{
			goodsDao.updategstatus(gid, gstatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
