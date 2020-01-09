package seproject.website.goods.order.service;

import java.sql.SQLException;

import seproject.website.goods.order.dao.OrderDao;
import seproject.website.goods.order.domain.Order;
import seproject.website.goods.order.domain.OrderItem;
import seproject.website.goods.pager.PageBean;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();

	public void updateStatus(String orderItemId, int status) {
		try {
			orderDao.updateStatus(orderItemId, status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Order load(String oid) {
		try {
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	public void createOrder(Order order) {
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	public PageBean<Order> myOrders(String uid, int pc) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}
	

	public PageBean<Order> findAll(int pc) {
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findAll(pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {}
			throw new RuntimeException(e);
		}
	}

	public OrderItem findByGid(String gid) {
		try {
			OrderItem orderItem = orderDao.findByGid(gid);
			return orderItem;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String findGid(String orderitemid) {
		try {
			String gid = orderDao.findGid(orderitemid);
			return gid;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
