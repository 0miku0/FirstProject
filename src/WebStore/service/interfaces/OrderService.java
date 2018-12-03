package WebStore.service.interfaces;

import WebStore.bean.Order;
import WebStore.bean.OrderProduct;
import WebStore.utils.PageInfo.PageInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface OrderService {
    boolean addOrder(Order order, HashMap<Integer, Integer> pidBuyNumMap) throws SQLException;

    List<Order> getOrderByUid(String uid) throws SQLException;

    void cancelOrder(String oid, String state) throws SQLException;

    PageInfo findPageOrder(String num) throws SQLException;

    List<OrderProduct> getOrderProductByOid(String oid) throws SQLException;

    void delOrderByOid(String oid) throws SQLException;

    void deleteOrderProduct(String opid) throws SQLException;
}
