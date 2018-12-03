package WebStore.utils.PageInfo;

import WebStore.bean.Product;

import java.util.HashMap;

public class SearchPageByPname extends SearchPage<Product>{
    String pname;
    public SearchPageByPname(int PAGE_COUNT, int currentPageNumber, SearchPageInfoDAO<Product> searchPageInfoDAO, HashMap<String, String> parameterMap) {
        super(PAGE_COUNT, currentPageNumber, searchPageInfoDAO, parameterMap);
        pname = parameterMap.get("likePname");
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
