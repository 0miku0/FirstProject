package WebStore.DatabaseAccessObject.interfaces;

import WebStore.bean.Product;
import WebStore.utils.PageInfo.SearchPageInfoDAO;
import WebStore.utils.TranscationUtils;

import java.sql.SQLException;
import java.util.HashMap;


public interface ProductDAO extends SearchPageInfoDAO<Product> {
    //放在了WebStore.utils.PageInfoDAO接口
//    int totalRecordNumber() throws SQLException;
//    List<Product> findPageProduct(int limit, int offset) throws SQLException;

     Boolean updateProduct(Product product) throws SQLException;

     Boolean deleteProductByPid(String pid) throws SQLException;

    Boolean addProduct(Product product) throws SQLException;

     Product findProductByPid(String pid) throws SQLException;

    void updateMultiProductNum(HashMap<Integer, Integer> pidBuyNumMap, TranscationUtils transcationUtils) throws SQLException;

}
