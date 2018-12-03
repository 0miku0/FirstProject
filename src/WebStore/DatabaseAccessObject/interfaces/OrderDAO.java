package WebStore.DatabaseAccessObject.interfaces;

import WebStore.bean.Order;
import WebStore.utils.PageInfo.PageInfoDAO;
import WebStore.utils.TranscationUtils;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO extends PageInfoDAO<Order> {
    String addOrder(Order order, TranscationUtils transcationUtils) throws SQLException;

    List<Order> getOrderByUid(String uid) throws SQLException;

    void changeOrderState(String oid, String state) throws SQLException;

    @Override
    int totalRecordNumber() throws SQLException;

    @Override
    List<Order> findPageObject(int limit, int offset) throws SQLException;

    void delOrderByOid(String oid) throws SQLException;
}
