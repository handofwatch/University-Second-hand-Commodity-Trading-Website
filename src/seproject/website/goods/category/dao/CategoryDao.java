package seproject.website.goods.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import seproject.website.goods.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();

	private Category toCategory(Map<String,Object> map) {

		Category category = CommonUtils.toBean(map, Category.class);
		return category;
	}

	private List<Category> toCategoryList(List<Map<String,Object>> mapList) {
		List<Category> categoryList = new ArrayList<Category>();//创建一个空集合
		for(Map<String,Object> map : mapList) {//循环遍历每个Map
			Category c = toCategory(map);//把一个Map转换成一个Category
			categoryList.add(c);//添加到集合中
		}
		return categoryList;//返回集合
	}

	public List<Category> findAll() throws SQLException {
		String sql = "select * from t_category order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
		return toCategoryList(mapList);
	}

	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,`cdesc`) values(?,?,?)";
		Object[] params = {category.getCid(), category.getCname(), category.getCdesc()};
		qr.update(sql, params);
	}

	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid=?";
		return toCategory(qr.query(sql, new MapHandler(), cid));
	}

	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=?,cdesc=? where cid=?";
		Object[] params = {category.getCname(),category.getCdesc(), category.getCid()};
		qr.update(sql, params);
	}

	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid=?";
		qr.update(sql, cid);
	}
}
