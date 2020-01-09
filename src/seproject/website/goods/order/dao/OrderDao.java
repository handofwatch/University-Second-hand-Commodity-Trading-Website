package seproject.website.goods.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.order.domain.Order;
import seproject.website.goods.order.domain.OrderItem;
import seproject.website.goods.pager.Expression;
import seproject.website.goods.pager.PageBean;
import seproject.website.goods.pager.PageConstants;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();

	public int findStatus(String orderitemid) throws SQLException {
		String sql = "select orderstatus from t_orderitem where orderItemId=?";
		int status  = (int) qr.query(sql, new ScalarHandler (), orderitemid);

		return status;
	}

	public void updateStatus(String orderItemId, int status) throws SQLException {
		String sql = "update t_orderitem set orderstatus=? where orderItemId=?";
		qr.update(sql, status, orderItemId);
	}

	public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid=?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		loadOrderItem(order);//为当前订单加载它的所有订单条目
		return order;
	}

	public void add(Order order) throws SQLException {

		String sql = "insert into t_order values(?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(), order.getOrdertime(),
				order.getTotal(),order.getAddress(),
				order.getOwner().getUid(),order.getBuyername(),order.getPhone()};
		qr.update(sql, params);

		sql = "insert into t_orderitem values(?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] objs = new Object[len][];
		for(int i = 0; i < len; i++){
			OrderItem item = order.getOrderItemList().get(i);
			objs[i] = new Object[]{item.getOrderItemId(),
					item.getPrice(),item.getGoods().getGid(),
					item.getGoods().getGname(),item.getGoods().getImage_b(),
					order.getOid(),item.getOrderstatus()};
		}
		qr.batch(sql, objs);
	}

	public PageBean<Order> findByUser(String uid, int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid", "=", uid));
		return findByCriteria(exprList, pc);
	}

	public PageBean<Order> findAll(int pc) throws SQLException {
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteria(exprList, pc);
	}

	private PageBean<Order> findByCriteria(List<Expression> exprList, int pc) throws SQLException {

		int ps = PageConstants.ORDER_PAGE_SIZE;//每页记录数

		StringBuilder whereSql = new StringBuilder(" where 1=1"); 
		List<Object> params = new ArrayList<Object>();//SQL中有问号，它是对应问号的值
		for(Expression expr : exprList) {

			whereSql.append(" and ").append(expr.getName())
				.append(" ").append(expr.getOperator()).append(" ");
			// where 1=1 and bid = ?
			if(!expr.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}

		String sql = "select count(*) from t_order" + whereSql;
		Number number = (Number)qr.query(sql, new ScalarHandler(), params.toArray());
		int tr = number.intValue();//得到了总记录数

		sql = "select * from t_order" + whereSql + " order by ordertime desc limit ?,?";
		params.add((pc-1) * ps);//当前页首行记录的下标
		params.add(ps);//一共查询几行，就是每页记录数
		
		List<Order> beanList = qr.query(sql, new BeanListHandler<Order>(Order.class), 
				params.toArray());
		// 虽然已经获取所有的订单，但每个订单中并没有订单条目。
		// 遍历每个订单，为其加载它的所有订单条目
		for(Order order : beanList) {
			loadOrderItem(order);
		}

		PageBean<Order> pb = new PageBean<Order>();

		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		
		return pb;
	}

	private void loadOrderItem(Order order) throws SQLException {

		String sql = "select * from t_orderitem where oid=?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		
		order.setOrderItemList(orderItemList);
	}

	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String,Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}

	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Goods goods = CommonUtils.toBean(map, Goods.class);
		orderItem.setGoods(goods);
		return orderItem;
	}

	public OrderItem findByGid(String gid) throws SQLException {
		String sql = "select * from t_orderitem where gid=?";
		OrderItem orderitem = qr.query(sql, new BeanHandler<OrderItem>(OrderItem.class), gid);
		return orderitem;
	}

	public String findGid(String orderitemid) throws SQLException {
		String sql = "select gid from t_orderitem where orderItemId=?";
		String gid = (String) qr.query(sql, new ScalarHandler(), orderitemid);
		return gid;
	}
}
