package WebStore.service.implments;

import WebStore.DatabaseAccessObject.interfaces.ProductDAO;
import WebStore.DatabaseAccessObject.implments.MyProductDAO;
import WebStore.bean.Product;
import WebStore.service.interfaces.ProductService;
import WebStore.utils.PageInfo.PageInfo;
import WebStore.utils.PageInfo.SearchPageInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MyProductService implements ProductService {
    ProductDAO productDAO=new MyProductDAO();
    private static  final int PAGE_COUNT=3;
    public MyProductService() {
    }

    @Override
    public Boolean addProduct(Product product) throws SQLException {
       return productDAO.addProduct(product);
    }

    //数据库设计上有，没使用
    @Override
    public Boolean deleteProduct(Product product) {
        return null;
    }

    @Override
    public Boolean deleteProductByPid(String pid) throws SQLException {
        return productDAO.deleteProductByPid(pid);
    }

    @Override
    public Boolean updateProduct(Product product) throws SQLException {
       return productDAO.updateProduct(product);
    }

    //数据库设计上有，没使用
    @Override
    public Product findProduct(String name) {
        return null;
    }

    //数据库设计上有，没使用
    @Override
    public List<Product> findAllProduct() {
        return null;
    }

    @Override
    public PageInfo findPageProduct(String num) throws SQLException {
        int currentPageNumber = Integer.parseInt(num);
        PageInfo<Product> productPageInfo = new PageInfo<>(PAGE_COUNT, currentPageNumber, productDAO);
        productPageInfo.setPageInfo();
        return productPageInfo;
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        return productDAO.findProductByPid(pid);
    }

    @Override
    public SearchPageInfo multiConditionSearchProduct(String num,HashMap<String,String> parameterMap) throws SQLException {
        int currentPageNumber = Integer.parseInt(num);
        SearchPageInfo searchPageInfo = new SearchPageInfo(PAGE_COUNT, currentPageNumber, productDAO, parameterMap);
        searchPageInfo.setPageInfo();
        return searchPageInfo;
    }

    @Override
    public List<Product> productsInit(int productsAmount) throws SQLException {
        PageInfo<Product> productsInfo = new PageInfo<>(productsAmount, 1, productDAO);
        productsInfo.setPageInfo();
        List<Product> pageList = productsInfo.getPageList();
        return pageList;
    }
}
