package cn.itcast.goods.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.book.domain.Book;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.goods.pager.Expression;
import cn.itcast.goods.pager.PageBean;
import cn.itcast.goods.pager.PageConstants;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 删除商品
	 * @param gid
	 * @throws SQLException 
	 */
	public void delete(String gid) throws SQLException {
		String sql = "delete from t_goods where gid=?";
		qr.update(sql, gid);
	}
	
	/**
	 * 修改图书
	 * @throws SQLException 
	 */
	public void edit(Book goods) throws SQLException {
		String sql = "update t_goods set gname=?,price=?," +
				"gdesc=?image_w=?,image_w2=?,image_b=?" +
				"cid=? where gid=?";
		Object[] params = {goods.getGname(),
				goods.getPrice(),goods.getGdesc(),goods.getImage_w(),goods.getImage_w2(),
				goods.getImage_b(),goods.getCategory().getCid(),goods.getGid()};
		qr.update(sql, params);
	}
	
	/**
	 * 按gid查询
	 * @param bid
	 * @return
	 * @throws SQLException
	 */
	public Book findByGid(String bid) throws SQLException {
		String sql = "SELECT * FROM t_goods g, t_category c WHERE g.cid=c.cid AND g.gid=?";
		// 一行记录中，包含了很多的goods的属性，还有一个cid属性
		Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
		// 把Map中除了cid以外的其他属性映射到Book对象中
		Book goods = CommonUtils.toBean(map, Book.class);
		// 把Map中cid属性映射到Category中，即这个Category只有cid
		Category category = CommonUtils.toBean(map, Category.class);
		// 两者建立关系
		goods.setCategory(category);
		
//		// 把pid获取出来，创建一个Category parnet，把pid赋给它，然后再把parent赋给category
//		if(map.get("pid") != null) {
//			Category parent = new Category();
//			parent.setCid((String)map.get("pid"));
//			category.setParent(parent);
//		}
		return goods;
	}
	
	/**
	 * 查询指定分类下的个数
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public int findGoodsCountByCategory(String cid) throws SQLException {
		String sql = "select count(*) from t_goods where cid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), cid);
		return cnt == null ? 0 : cnt.intValue();
	}
	
	/**
	 * 按分类查询
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public PageBean<Book> findByCategory(String cid, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("cid", "=", cid));
		return findByCriteria(exprList, pc);
	}
	
	/**
	 * 按书名模糊查询
	 * @param gname
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByGname(String gname, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("gname", "like", "%" + gname + "%"));
		return findByCriteria(exprList, pc);
	}
	
	/**没看懂
	 * 通用的查询方法
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	private PageBean<Book> findByCriteria(List<Expression> exprList, int pc) throws SQLException {
		/*
		 * 1. 得到ps
		 * 2. 得到tr
		 * 3. 得到beanList
		 * 4. 创建PageBean，返回
		 */
		/*
		 * 1. 得到ps
		 */
		int ps = PageConstants.BOOK_PAGE_SIZE;//每页记录数
		/*
		 * 2. 通过exprList来生成where子句
		 */
		StringBuilder whereSql = new StringBuilder(" where 1=1"); 
		List<Object> params = new ArrayList<Object>();//SQL中有问号，它是对应问号的值
		for(Expression expr : exprList) {
			/*
			 * 添加一个条件上，
			 * 1) 以and开头
			 * 2) 条件的名称
			 * 3) 条件的运算符，可以是=、!=、>、< ... is null，is null没有值
			 * 4) 如果条件不是is null，再追加问号，然后再向params中添加一与问号对应的值
			 */
			whereSql.append(" and ").append(expr.getName())
				.append(" ").append(expr.getOperator()).append(" ");
			// where 1=1 and bid = ?
			if(!expr.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}

		/*
		 * 3. 总记录数 
		 */
		String sql = "select count(*) from t_goods" + whereSql;
		Number number = (Number)qr.query(sql, new ScalarHandler(), params.toArray());
		int tr = number.intValue();//得到了总记录数
		/*
		 * 4. 得到beanList，即当前页记录
		 */
		sql = "select * from t_goods" + whereSql + " order by orderBy limit ?,?";
		params.add((pc-1) * ps);//当前页首行记录的下标
		params.add(ps);//一共查询几行，就是每页记录数
		
		List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class), 
				params.toArray());
		
		/*
		 * 5. 创建PageBean，设置参数
		 */
		PageBean<Book> pb = new PageBean<Book>();
		/*
		 * 其中PageBean没有url，这个任务由Servlet完成
		 */
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		
		return pb;
	}

	/**
	 * 添加图书
	 * @param
	 * @throws SQLException 
	 */
	public void add(Book goods) throws SQLException {
		String sql = "insert into t_goods(gid,gname,price," +
				"gdesc,gstatus" +
				"cid,image_w,image_w2,image_b)" +
				" values(?,?,?,?,?,?,?,?,?)";
		Object[] params = {goods.getGid(),goods.getGname(),goods.getPrice(),
				goods.getGdesc(),goods.getGstatus(),goods.getCategory().getCid(),
				goods.getImage_w(),goods.getImage_w2(),goods.getImage_b()};
		qr.update(sql, params);
	}
}
