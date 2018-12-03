package WebStore.utils.PageInfo;

import WebStore.bean.Product;

import java.util.HashMap;

public class SearchPageByCid extends SearchPage<Product> {
    String cid;
    public SearchPageByCid(int PAGE_COUNT, int currentPageNumber, SearchPageInfoDAO<Product> searchPageInfoDAO, HashMap<String, String> parameterMap) {
        super(PAGE_COUNT, currentPageNumber, searchPageInfoDAO, parameterMap);
        cid = parameterMap.get("cid");
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
