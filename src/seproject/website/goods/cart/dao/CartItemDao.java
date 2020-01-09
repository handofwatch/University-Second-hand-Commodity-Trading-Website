package seproject.website.goods.cart.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import cn.itcast.commons.CommonUtils;
import seproject.website.goods.goods.domain.Goods;
import seproject.website.goods.cart.domain.CartItem;
import seproject.website.goods.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class CartItemDao {
	private QueryRunner qr = new TxQueryRunner();

	private String toWhereSql(int len) {
		StringBuilder sb = new StringBuilder("cartItemId in(");
		for(int i = 0; i < len; i++) {
			sb.append("?");
			if(i < len - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	public List<Goods> loadCartItems(String cartItemIds) throws SQLException {

		Object[] cartItemIdArray = cartItemIds.split(",");

		String whereSql = toWhereSql(cartItemIdArray.length);

		String sql = "select * from t_cartitem c, t_goods g where c.gid=g.gid and " + whereSql;

		return toGoodsList(qr.query(sql, new MapListHandler(), cartItemIdArray));
	}

	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		String sql = "select * from t_cartItem c, t_goods g where c.gid=g.gid and c.cartItemId=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), cartItemId);
		return toCartItem(map);
	}

	public void batchDelete(String cartItemIds) throws SQLException {

		Object[] cartItemIdArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdArray.length);
		String sql = "delete from t_cartitem where " + whereSql;
		qr.update(sql, cartItemIdArray);//其中cartItemIdArray必须是Object类型的数组！
	}

	public CartItem findByUidAndGid(String uid, String bid) throws SQLException {
		String sql = "select * from t_cartitem where uid=? and gid=?";
		Map<String,Object> map = qr.query(sql, new MapHandler(), uid, bid);
		CartItem cartItem = toCartItem(map);
		return cartItem;
	}

	public void updateQuantity(String cartItemId, int quantity) throws SQLException {
		String sql = "update t_cartitem set quantity=? where cartItemId=?";
		qr.update(sql, quantity, cartItemId);
	}

	public String addCartItem(CartItem cartItem) throws SQLException {
		String sql = "insert into t_cartitem(cartItemId, gid, uid)" +
				" values(?,?,?)";
		Object[] params = {cartItem.getCartItemId(),
				cartItem.getGoods().getGid(), cartItem.getUser().getUid()};
		qr.update(sql, params);
        return cartItem.getCartItemId();
    }

	private CartItem toCartItem(Map<String,Object> map) {
		if(map == null || map.size() == 0) return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Goods goods = CommonUtils.toBean(map, Goods.class);
		User user = CommonUtils.toBean(map, User.class);
		cartItem.setGoods(goods);
		cartItem.setUser(user);
		return cartItem;
	}

	private List<CartItem> toCartItemList(List<Map<String,Object>> mapList) {
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		for(Map<String,Object> map : mapList) {
			CartItem cartItem = toCartItem(map);
			cartItemList.add(cartItem);
		}
		return cartItemList;
	}

	public List<CartItem> findByUser(String uid) throws SQLException {
		String sql = "select * from t_cartitem c, t_goods g where c.gid=g.gid and uid=? order by c.orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), uid);
		return toCartItemList(mapList);
	}

	private List<Goods> toGoodsList(List<Map<String,Object>> mapList) {
		List<Goods> GoodsList = new ArrayList<Goods>();
		for(Map<String,Object> map : mapList) {
			CartItem cartItem = toCartItem(map);
			GoodsList.add(cartItem.getGoods());
		}
		return GoodsList;
	}
}
