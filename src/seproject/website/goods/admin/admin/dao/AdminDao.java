package seproject.website.goods.admin.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import org.apache.commons.dbutils.handlers.ScalarHandler;
import seproject.website.goods.admin.admin.domain.Admin;
import cn.itcast.jdbc.TxQueryRunner;

public class AdminDao {
	private QueryRunner qr = new TxQueryRunner();

	public Admin find(String adminname, String adminpwd) throws SQLException {
		String sql = "select * from t_admin where adminname=? and adminpwd=?";
		return qr.query(sql, new BeanHandler<Admin>(Admin.class), adminname, adminpwd);
	}

	public boolean ValidateLoginname(String loginname) throws SQLException {

		String sql = "select count(1) from t_admin where adminname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}

	public void add(Admin admin) throws SQLException {
		String sql = "insert into t_admin values(?,?,?)";
		Object[] params = {admin.getAdminId(), admin.getAdminname(), admin.getAdminpwd(),};

		qr.update(sql, params);
	}
}
