package seproject.website.goods.goods.service;

import java.sql.SQLException;

import seproject.website.goods.goods.dao.GoodsDao;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.pager.PageBean;

public class GoodsService {
	private GoodsDao goodsDao = new GoodsDao();
	
	/**
	 * 删除图书
	 * @param gid
	 */
	public void delete(String gid) {
		try {
			goodsDao.delete(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改图书
	 * @param goods
	 */
	public void edit(Goods goods) {
		try {
			goodsDao.edit(goods);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 返回当前分类下图书个数
	 * @param cid
	 * @return
	 */
	public int findGoodsCountByCategory(String cid) {
		try {
			return goodsDao.findGoodsCountByCategory(cid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载图书
	 * @param gid
	 * @return
	 */
	public Goods load(String gid) {
		try {
			return goodsDao.findByGid(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按分类查
	 * @param cid
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> findByCategory(String cid, int pc) {
		try {
			return goodsDao.findByCategory(cid, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按书名查
	 * @param gname
	 * @param pc
	 * @return
	 */
	public PageBean<Goods> findByGname(String gname, int pc) {
		try {
			return goodsDao.findByGname(gname, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加图书
	 * @param goods
	 */
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
}
