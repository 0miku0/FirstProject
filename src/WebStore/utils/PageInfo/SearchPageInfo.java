package WebStore.utils.PageInfo;

import WebStore.bean.Product;

import java.util.HashMap;

public class SearchPageInfo extends SearchPage<Product> {
    String pid;
    String cid;
    String pname;
    String minPrice;
    String maxPrice;
    public SearchPageInfo(int PAGE_COUNT, int currentPageNumber, SearchPageInfoDAO <Product> searchPageInfoDAO, HashMap<String,String> parameterMap) {
        super(PAGE_COUNT,currentPageNumber,searchPageInfoDAO,parameterMap);
        pid = parameterMap.get("pid");
        cid = parameterMap.get("cid");
        pname = parameterMap.get("pname");
        minPrice = parameterMap.get("minPrice");
        maxPrice = parameterMap.get("maxPrice");
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
}
