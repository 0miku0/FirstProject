package WebStore.service.interfaces;

import WebStore.bean.Product;
import WebStore.utils.PageInfo.PageInfo;
import WebStore.utils.PageInfo.SearchPageInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface ProductService {
    //增加商品
    Boolean addProduct(Product product) throws SQLException;
    //删除商品
    Boolean deleteProduct(Product product);

    Boolean deleteProductByPid(String pid) throws SQLException;
    //修改商品
    Boolean updateProduct(Product product) throws SQLException;
    //根据商品名称查询商品
    Product findProduct(String name);
    //查询所有商品
    List<Product> findAllProduct();

    //分页查询
    PageInfo findPageProduct(String num) throws SQLException;
    //根据商品pid查询商品
    Product findProductByPid(String pid) throws SQLException;


    SearchPageInfo multiConditionSearchProduct(String num,HashMap<String,String> parameterMap) throws SQLException;

    List<Product> productsInit(int productsAmount) throws SQLException;
}
