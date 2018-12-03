package WebStore.service.implments;

import WebStore.DatabaseAccessObject.implments.MyCartDAO;
import WebStore.DatabaseAccessObject.implments.MyOrderProductDAO;
import WebStore.DatabaseAccessObject.implments.MyProductDAO;
import WebStore.DatabaseAccessObject.interfaces.CartDAO;
import WebStore.DatabaseAccessObject.interfaces.OrderDAO;
import WebStore.DatabaseAccessObject.implments.MyOrderDAO;
import WebStore.DatabaseAccessObject.interfaces.OrderProductDAO;
import WebStore.DatabaseAccessObject.interfaces.ProductDAO;
import WebStore.bean.Order;
import WebStore.bean.OrderProduct;
import WebStore.service.interfaces.OrderService;
import WebStore.utils.PageInfo.PageInfo;
import WebStore.utils.TranscationUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MyOrderService implements OrderService {
    private static  final int PAGE_COUNT=3;
    OrderDAO orderDAO=new MyOrderDAO();
    OrderProductDAO orderProductDAO=new MyOrderProductDAO();
    CartDAO cartDAO=new MyCartDAO();
    ProductDAO productDAO=new MyProductDAO();
    @Override
    public boolean addOrder(Order order, HashMap<Integer, Integer> pidBuyNumMap) throws SQLException {
        TranscationUtils transcationUtils = new TranscationUtils();
        try {
            transcationUtils.beganTranscation();
            String oid=orderDAO.addOrder(order,transcationUtils);
            if(oid!=null){
                if(orderProductDAO.addOrderProducts(oid,pidBuyNumMap,transcationUtils)) {
                    Object[] objects = pidBuyNumMap.keySet().toArray();
                    String[] pids = new String[objects.length];
                    for (int i = 0; i < objects.length; i++) {
                        pids[i] = objects[i].toString();
                    }
                    cartDAO.updateCart(order.getUid(), pids, transcationUtils);
                    productDAO.updateMultiProductNum(pidBuyNumMap, transcationUtils);
                    transcationUtils.commit();
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            transcationUtils.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> getOrderByUid(String uid) throws SQLException {
        return orderDAO.getOrderByUid(uid);
    }

    @Override
    public void cancelOrder(String oid, String state) throws SQLException {
        orderDAO.changeOrderState(oid,state);
    }

    @Override
    public PageInfo findPageOrder(String num) throws SQLException {
        int currentPageNumber = Integer.parseInt(num);
        PageInfo<Order> orderPageInfo = new PageInfo<>(PAGE_COUNT, currentPageNumber, orderDAO);
        orderPageInfo.setPageInfo();
        return orderPageInfo;
    }

    @Override
    public List<OrderProduct> getOrderProductByOid(String oid) throws SQLException {
        return orderProductDAO.getOrderProductByOid(oid);
    }

    @Override
    public void delOrderByOid(String oid) throws SQLException {
        OrderProductDAO orderProductDAO = new MyOrderProductDAO();
        List<OrderProduct> orderProductByOid = orderProductDAO.getOrderProductByOid(oid);
        for(int i=0;i<orderProductByOid.size();i++){
            orderProductDAO.deleteOrderProductByOpid(orderProductByOid.get(i).getOpid()+"");
        }
        orderDAO.delOrderByOid(oid);
    }

    @Override
    public void deleteOrderProduct(String opid) throws SQLException {
        orderProductDAO.deleteOrderProductByOpid(opid);
    }
}
