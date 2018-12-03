package WebStore.DatabaseAccessObject.interfaces;

import WebStore.bean.Order;
import WebStore.bean.OrderProduct;
import WebStore.utils.TranscationUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface OrderProductDAO {
    boolean addOrderProducts(String oid, HashMap<Integer, Integer> pidBuyNumMap, TranscationUtils transcationUtils) throws SQLException;

    List<OrderProduct> getOrderProductByOid(String oid) throws SQLException;

    void deleteOrderProductByOpid(String opid) throws SQLException;
}
