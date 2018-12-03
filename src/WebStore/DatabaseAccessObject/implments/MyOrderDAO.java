package WebStore.DatabaseAccessObject.implments;

import WebStore.DatabaseAccessObject.interfaces.OrderDAO;
import WebStore.bean.Order;
import WebStore.utils.C3P0Utils;
import WebStore.utils.TranscationUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class MyOrderDAO implements OrderDAO {
    private QueryRunner queryRunner=new QueryRunner(C3P0Utils.getCpds());
    QueryRunner transcationQueryRunner=new QueryRunner();
    private static  final String TABLE="`firstordertable`";
    @Override
    public String addOrder(Order order, TranscationUtils transcationUtils) throws SQLException {
        int update = transcationQueryRunner.update(transcationUtils.getConnection(),"insert into " + TABLE +
                        "values (null,?,?,?,?,?,?,?);", order.getMoney(), order.getRecipients(), order.getTel(),
                order.getAddress(), order.getState(), order.getOrderTime(), order.getUid());
        if(update!=0){
            String oid = transcationQueryRunner.query(transcationUtils.getConnection(),"select oid from " + TABLE +
                    " where orderTime=? and uid=?;", new ScalarHandler(), order.getOrderTime(), order.getUid()).toString();
            return oid;
        }
        return null;
    }

    @Override
    public List<Order> getOrderByUid(String uid) throws SQLException {
        List<Order> orderList = queryRunner.query
                ("select * from " + TABLE + " where uid=?;", new BeanListHandler<>(Order.class), uid);
        return orderList;
    }

    @Override
    public void changeOrderState(String oid, String state) throws SQLException {
        queryRunner.update("update "+TABLE+" set state=? where oid=?",state,oid);
    }

    @Override
    public int totalRecordNumber() throws SQLException {
        String totalRecordNumber= queryRunner.query
                ("select count(*) from  "+TABLE+" ;",new ScalarHandler()).toString();
        return Integer.parseInt(totalRecordNumber);
    }

    @Override
    public List<Order> findPageObject(int limit, int offset) throws SQLException {
        List<Order> orderList = queryRunner.query
                ("select * from  "+TABLE+"  limit ? offset ?;",
                        new BeanListHandler<>(Order.class),limit,offset);
        return orderList;
    }

    @Override
    public void delOrderByOid(String oid) throws SQLException {
        queryRunner.update("delete from "+TABLE+" where oid=?;",oid);
    }
}
