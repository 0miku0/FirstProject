package WebStore.service.implments;

import WebStore.DatabaseAccessObject.interfaces.ProductDAO;
import WebStore.DatabaseAccessObject.implments.MyProductDAO;
import WebStore.bean.Product;
import WebStore.service.interfaces.MainService;
import WebStore.service.interfaces.ProductService;
import WebStore.utils.PageInfo.SearchPageByCid;
import WebStore.utils.PageInfo.SearchPageByPname;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MyMainService implements MainService {
    ProductDAO productDAO=new MyProductDAO();
    private static  final int PAGE_COUNT= 6;
    ProductService productService=new MyProductService();
    public MyMainService() {
    }

    @Override
    public List<Product> topProductsInit(int topProductsAmount) throws SQLException {
        return productService.productsInit(topProductsAmount);
    }

    @Override
    public List<Product> hotProductsInit(int hotProductsAmount) throws SQLException {
        return productService.productsInit(hotProductsAmount);
    }

    @Override
    public Product findProductByPid(String pid) throws SQLException {
        ProductService productService = new MyProductService();
        Product productByPid = productService.findProductByPid(pid);
        return productByPid;
    }

    @Override
    public SearchPageByCid findProductByCid(String num, HashMap<String, String> parameterMap) throws SQLException {
        int currentPageNumber = Integer.parseInt(num);
        SearchPageByCid searchPageByCid = new SearchPageByCid(PAGE_COUNT, currentPageNumber, productDAO, parameterMap);
        searchPageByCid.setPageInfo();
        return searchPageByCid;
    }

    @Override
    public SearchPageByPname findProductsByName(String num, HashMap<String, String> parameterMap) throws SQLException {
        int currentPageNumber = Integer.parseInt(num);
        SearchPageByPname searchPageByPname = new SearchPageByPname(PAGE_COUNT, currentPageNumber, productDAO, parameterMap);
        searchPageByPname.setPageInfo();
        return searchPageByPname;
    }

}
