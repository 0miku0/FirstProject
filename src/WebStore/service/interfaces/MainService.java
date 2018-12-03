package WebStore.service.interfaces;

import WebStore.bean.Product;
import WebStore.utils.PageInfo.SearchPageByCid;
import WebStore.utils.PageInfo.SearchPageByPname;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface MainService {

    List<Product> topProductsInit(int topProductsAmount) throws SQLException;

    List<Product> hotProductsInit(int hotProductsAmount) throws SQLException;

    Product findProductByPid(String pid) throws SQLException;


    SearchPageByCid findProductByCid(String num, HashMap<String, String> parameterMap) throws SQLException;

    SearchPageByPname findProductsByName(String num, HashMap<String, String> parameterMap) throws SQLException;
}
