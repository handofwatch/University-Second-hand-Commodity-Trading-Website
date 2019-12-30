package cn.itcast.goods.book.service;

import java.sql.SQLException;

import cn.itcast.goods.book.dao.BookDao;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.pager.PageBean;

public class BookService {
	private BookDao bookDao = new BookDao();
	
	/**
	 * 删除图书
	 * @param gid
	 */
	public void delete(String gid) {
		try {
			bookDao.delete(gid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改图书
	 * @param goods
	 */
	public void edit(Book goods) {
		try {
			bookDao.edit(goods);
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
			return bookDao.findGoodsCountByCategory(cid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载图书
	 * @param gid
	 * @return
	 */
	public Book load(String gid) {
		try {
			return bookDao.findByGid(gid);
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
	public PageBean<Book> findByCategory(String cid, int pc) {
		try {
			return bookDao.findByCategory(cid, pc);
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
	public PageBean<Book> findByGname(String gname, int pc) {
		try {
			return bookDao.findByGname(gname, pc);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加图书
	 * @param goods
	 */
	public void add(Book goods) {
		try {
			bookDao.add(goods);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
