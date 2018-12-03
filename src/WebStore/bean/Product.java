package WebStore.bean;

import WebStore.DatabaseAccessObject.interfaces.CategoryDAO;
import WebStore.DatabaseAccessObject.implments.MyCategoryDAO;

import java.sql.SQLException;

public class Product  {
    //主键，商品id
    int pid;
    //商品名称
    String pname;
    //商城价格
    Double estoreprice;
    //市场价格
    Double markprice;
    //库存
    int pnum;
    //外键，类别id
    int cid;
    //商品图片
    String imgurl;
    //商品描述
    String desc;

    Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", estoreprice=" + estoreprice +
                ", markprice=" + markprice +
                ", pnum=" + pnum +
                ", cid=" + cid +
                ", imgurl='" + imgurl + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Double getEstoreprice() {
        return estoreprice;
    }

    public void setEstoreprice(Double estoreprice) {
        this.estoreprice = estoreprice;
    }

    public Double getMarkprice() {
        return markprice;
    }

    public void setMarkprice(Double markprice) {
        this.markprice = markprice;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) throws SQLException {
        this.cid = cid;
        CategoryDAO categoryDAO =new MyCategoryDAO();
        String cname = categoryDAO.findCategoryByCid(cid);
        Category category = new Category();
        category.setCname(cname);
        category.setCid(cid);
        setCategory(category);
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
