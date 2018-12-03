package WebStore.DatabaseAccessObject.implments;

import WebStore.DatabaseAccessObject.interfaces.OrderProductDAO;
import WebStore.bean.Order;
import WebStore.bean.OrderProduct;
import WebStore.utils.C3P0Utils;
import WebStore.utils.TranscationUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MyOrderProductDAO implements OrderProductDAO {
    private QueryRunner queryRunner=new QueryRunner(C3P0Utils.getCpds());
    QueryRunner transcationQueryRunner=new QueryRunner();
    private static  final String TABLE="`firstorderproductstable`";
    @Override
    public boolean addOrderProducts(String oid, HashMap<Integer, Integer> pidBuyNumMap, TranscationUtils transcationUtils) throws SQLException {

        Object[] pids = pidBuyNumMap.keySet().toArray();
        //Integer[] pids = (Integer[])objects;
        for(int i=0;i<pids.length;i++){
            transcationQueryRunner.update(transcationUtils.getConnection(),"insert into "+TABLE+
                    " values (null,?,?,?);",oid,pids[i],pidBuyNumMap.get(pids[i]));
            if(i==(pids.length-1)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<OrderProduct> getOrderProductByOid(String oid) throws SQLException {
        return queryRunner.query("select * from "+TABLE+" where oid=?",new BeanListHandler<>(OrderProduct.class),oid);
    }

    @Override
    public void deleteOrderProductByOpid(String opid) throws SQLException {
        queryRunner.update("delete from "+TABLE+" where opid=?;",opid);
    }
}
