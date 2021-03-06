package seproject.website.goods.admin.admin.service;

import java.sql.SQLException;
import cn.itcast.commons.CommonUtils;
import seproject.website.goods.admin.admin.dao.AdminDao;
import seproject.website.goods.admin.admin.domain.Admin;

public class AdminService {
	private AdminDao adminDao = new AdminDao();

	public Admin login(Admin admin) {
		try {
			return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    public boolean ValidateLoginname(String loginname) {
		try {
			return adminDao.ValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

    }

	public void regist(Admin admin) {
		admin.setAdminId(CommonUtils.uuid());
		try {
			adminDao.add(admin);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
