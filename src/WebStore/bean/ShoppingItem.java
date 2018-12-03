package WebStore.bean;

import WebStore.DatabaseAccessObject.implments.MyProductDAO;
import WebStore.DatabaseAccessObject.interfaces.ProductDAO;

import java.sql.SQLException;

public class ShoppingItem {
    String uid;
    String pid;
    String snum;
    Product product;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ShoppingItem() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) throws SQLException {
        this.pid = pid;
        ProductDAO productDAO = new MyProductDAO();
        Product productByPid = productDAO.findProductByPid(pid);
        setProduct(productByPid);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSnum() {
        return snum;
    }

    public void setSnum(String snum) {
        this.snum = snum;
    }
}
