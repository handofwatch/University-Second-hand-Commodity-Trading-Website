package website.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.goods.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/*
	 * 把一个Map中的数据映射到Category中
	 */
	private Category toCategory(Map<String,Object> map) {
		/*
		 * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
		 * Category{cid:xx, cname:xx, parent:(cid=pid), desc:xx}
		 */
		Category category = CommonUtils.toBean(map, Category.class);
		return category;
	}
	
	/*
	 * 可以把多个Map(List<Map>)映射成多个Category(List<Category>)
	 */
	private List<Category> toCategoryList(List<Map<String,Object>> mapList) {
		List<Category> categoryList = new ArrayList<Category>();//创建一个空集合
		for(Map<String,Object> map : mapList) {//循环遍历每个Map
			Category c = toCategory(map);//把一个Map转换成一个Category
			categoryList.add(c);//添加到集合中
		}
		return categoryList;//返回集合
	}
	
	/**
	 * 查询所有分类
	 * @param
	 * @return
	 * @throws SQLException 
	 */
	public List<Category> findAll() throws SQLException {
		String sql = "select * from t_category order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		return toCategoryList(mapList);
	}
	
	/**
	 * 添加分类
	 * @param category
	 * @throws SQLException 
	 */
	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,`cdesc`) values(?,?,?)";
		Object[] params = {category.getCid(), category.getCname(), category.getCdesc()};
		qr.update(sql, params);
	}

	/**
	 * 加载分类
	 * @param cid
	 * @return
	 * @throws SQLException 
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(), cid));
	}
	
	/**
	 * 修改分类
	 * @param category
	 * @throws SQLException 
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?,cdesc=? where cid=?";
		Object[] params = {category.getCname(),category.getCdesc(), category.getCid()};
		qr.update(sql, params);
	}
	
	/**
	 * 删除分类
	 * @param cid
	 * @throws SQLException 
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
	}
}
