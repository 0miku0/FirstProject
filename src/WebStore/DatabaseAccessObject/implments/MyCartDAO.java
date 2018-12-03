package WebStore.DatabaseAccessObject.implments;

import WebStore.DatabaseAccessObject.interfaces.CartDAO;
import WebStore.bean.ShoppingItem;
import WebStore.bean.User;
import WebStore.utils.C3P0Utils;
import WebStore.utils.TranscationUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class MyCartDAO implements CartDAO {
    private QueryRunner queryRunner=new QueryRunner(C3P0Utils.getCpds());
    QueryRunner transcationQueryRunner=new QueryRunner();
    private static  final String TABLE="`firstcarttable`";
    @Override
    public List<ShoppingItem> getShoppingItems(String uid) throws SQLException {
        List<ShoppingItem> ShoppingItems = queryRunner.query
                ("select uid,pid,snum from " +TABLE+ " where uid=?", new BeanListHandler<>(ShoppingItem.class), uid);
        return ShoppingItems;
    }

    @Override
    public boolean addCart(String uid, String pid,String snum) throws SQLException {
        String snumQuery = queryRunner.query
                ("select snum from " + TABLE + " where uid=? and pid=?;",new ScalarHandler(), uid, pid).toString();
        if(snumQuery==null){
            queryRunner.update("insert into "+TABLE+" values (?,?,?);",uid,pid,1);
            return true;
        }else {
            int update = queryRunner.update("update " + TABLE + " set snum=? where uid=? and pid=?;",
                    Integer.parseInt(snumQuery) + Integer.parseInt(snum), uid,pid);
            if(update!=0){
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean delOneItem(String uid, String pid) throws SQLException {
        int update = queryRunner.update("delete from " + TABLE + " where uid=? and pid=?", uid, pid);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }


    public boolean transcationDelOneItem(String uid, String pid,TranscationUtils transcationUtils) throws SQLException {
        int update = transcationQueryRunner.update(transcationUtils.getConnection(),"delete from " + TABLE +
                " where uid=? and pid=?", uid, pid);
        if(update!=0){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void updateCart(int uid, String[] pids, TranscationUtils transcationUtils) throws SQLException {
        for(int i=0;i<pids.length;i++){
            transcationDelOneItem(uid+"",pids[i],transcationUtils);
        }
    }
}
