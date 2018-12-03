package WebStore.DatabaseAccessObject.implments;

import WebStore.DatabaseAccessObject.interfaces.ProductDAO;
import WebStore.bean.Product;
import WebStore.utils.C3P0Utils;
import WebStore.utils.TranscationUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;

public class MyProductDAO implements ProductDAO {
    private QueryRunner queryRunner=new QueryRunner(C3P0Utils.getCpds());
    QueryRunner transcationQueryRunner=new QueryRunner();
    private static  final String TABLE="`firstproducttable`";
    public MyProductDAO() {
    }

    @Override
    public int totalRecordNumber() throws SQLException {
        String totalRecordNumber= queryRunner.query
                ("select count(*) as totalRecord from" +TABLE+";", new ResultSetHandler<String>() {
                    @Override
                    public String handle(ResultSet resultSet) throws SQLException {
                        resultSet.next();
                        return resultSet.getInt(1)+"";
                    }
                });
        return Integer.parseInt(totalRecordNumber);
    }

    @Override
    public List<Product> findPageObject(int limit, int offset) throws SQLException {
        List<Product> productList = queryRunner.query
                ("select * from "+TABLE+" limit ? offset ?;",
                        new BeanListHandler<>(Product.class),limit,offset);
        return productList;
    }

    @Override
    public Boolean updateProduct(Product product) throws SQLException {
        int pid = product.getPid();
        String pname = product.getPname();
        Double estoreprice = product.getEstoreprice();
        Double markprice = product.getMarkprice();
        int pnum = product.getPnum();
        int cid = product.getCid();
        String imgurl = product.getImgurl();
        String desc = product.getDesc();
        int update = queryRunner.update("update "+TABLE+" set pname=?,estoreprice=?,markprice=?,pnum=?," +
                "cid=?,imgurl=?,`desc`=?" + " where pid=?;", pname, estoreprice, markprice, pnum, cid, imgurl, desc, pid);
        if(update!=0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteProductByPid(String pid) throws SQLException {
        int update = queryRunner.update("delete from "+TABLE+" where pid=?;", pid);
        if(update!=0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean addProduct(Product product) throws SQLException {
        String pname = product.getPname();
        Double estoreprice = product.getEstoreprice();
        Double markprice = product.getMarkprice();
        int pnum = product.getPnum();
        int cid = product.getCid();
        String imgurl = product.getImgurl();
        String desc = product.getDesc();
        int update = queryRunner.update("insert into "+TABLE+
                " values (null,?,?,?,?,?,?,?);", pname, estoreprice, markprice, pnum, cid, imgurl, desc);
        if(update!=0){
            return true;
        }
        return false;
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        Product product = queryRunner.query
                ("select * from "+TABLE+" where pid=?;",
                        new BeanHandler<>(Product.class),pid);
        return product;
    }

    @Override
    public int totalRecordNumberMultiCondition(HashMap<String,String> parameterMap) throws SQLException {
        String pid = parameterMap.get("pid");
        String cid = parameterMap.get("cid");
        String pname = parameterMap.get("pname");
        String minPrice = parameterMap.get("minPrice");
        String maxPrice = parameterMap.get("maxPrice");
        String likePname = parameterMap.get("likePname");
        String s="select count(pid) from "+TABLE+" where 1=1";
        if(pid!=null&&!pid.isEmpty()){
            s=s+" and pid="+pid;
        }
        if(cid!=null&&!cid.isEmpty()){
            s=s+" and cid="+cid;
        }
        if(pname!=null&&!pname.isEmpty()){
            s=s+" and pname='"+pname+"'";
        }
        if(minPrice!=null&&!minPrice.isEmpty()){
            s=s+" and markprice>="+minPrice;
        }
        if(maxPrice!=null&&!maxPrice.isEmpty()){
            s=s+" and markprice<="+maxPrice;
        }
        if(likePname!=null&&!likePname.isEmpty()) {
            s = s + " and pname like '" + likePname + "%'";
        }
        s=s+";";
        String query = queryRunner.query(s, new ScalarHandler()).toString();
        return Integer.parseInt(query);
    }

    @Override
    public List<Product> findPageObjectMultiCondition
            (int limit, int offset,HashMap<String,String> parameterMap) throws SQLException {
        String pid = parameterMap.get("pid");
        String cid = parameterMap.get("cid");
        String pname = parameterMap.get("pname");
        String minPrice = parameterMap.get("minPrice");
        String maxPrice = parameterMap.get("maxPrice");
        String likePname = parameterMap.get("likePname");
        String s="select * from "+TABLE+" where 1=1";
        if(pid!=null&&!pid.isEmpty()){
            s=s+" and pid="+pid;
        }
        if(cid!=null&&!cid.isEmpty()){
            s=s+" and cid="+cid;
        }
        if(pname!=null&&!pname.isEmpty()){
            s=s+" and pname='"+pname+"'";
        }
        if(minPrice!=null&&!minPrice.isEmpty()){
            s=s+" and markprice>="+minPrice;
        }
        if(maxPrice!=null&&!maxPrice.isEmpty()){
            s=s+" and markprice<="+maxPrice;
        }
        if(pname!=null&&!pname.isEmpty()) {
            s = s + " and pname like '" + pname + "%'";
        }
        //num=0的bug
        if(offset<0){
            offset=0;
        }
        s=s+" limit "+limit;
        s=s+" offset "+offset+";";
        List<Product> productList = queryRunner.query(s,new BeanListHandler<>(Product.class));
        return productList;
    }

    @Override
    public void updateMultiProductNum(HashMap<Integer, Integer> pidBuyNumMap, TranscationUtils transcationUtils) throws SQLException {
        Object[] pids = pidBuyNumMap.keySet().toArray();
        for(int i=0;i<pids.length;i++){
            updateProductNum(pids[i]+"",pidBuyNumMap.get(pids[i])+"",transcationUtils);
        }
    }

    public void updateProductNum(String pid,String buyNum,TranscationUtils transcationUtils) throws SQLException {
        Product productByPid = findProductByPid(pid);
        int balance=productByPid.getPnum()-Integer.parseInt(buyNum);
        transcationQueryRunner.update(transcationUtils.getConnection(),"update "+TABLE+" set pnum=? where pid=?;",balance, pid);
    }
}
