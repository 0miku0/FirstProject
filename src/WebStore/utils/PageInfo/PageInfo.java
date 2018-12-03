package WebStore.utils.PageInfo;
import java.sql.SQLException;
import java.util.List;

public  class PageInfo<T> {
    List<T> pageList;

    int totalRecordNumber;

    int totalPageNumber;

    int currentPageNumber;

    int previousPageNumber;

    int  nextPageNumber;

    int PAGE_COUNT;

    PageInfoDAO<T> pageInfoDAO;
    public PageInfo(int PAGE_COUNT,int currentPageNumber,PageInfoDAO<T> pageInfoDAO){
        this.PAGE_COUNT=PAGE_COUNT;
        this.currentPageNumber=currentPageNumber;
        this.pageInfoDAO=pageInfoDAO;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageList=" + pageList +
                ", totalRecordNumber=" + totalRecordNumber +
                ", totalPageNumber=" + totalPageNumber +
                ", currentPageNumber=" + currentPageNumber +
                ", previousPageNumber=" + previousPageNumber +
                ", nextPageNumber=" + nextPageNumber +
                '}';
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getTotalRecordNumber() {
        return totalRecordNumber;
    }

    public void setTotalRecordNumber(int totalRecordNumber) {
        this.totalRecordNumber = totalRecordNumber;
    }

    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getPreviousPageNumber() {
        return previousPageNumber;
    }

    public void setPreviousPageNumber(int previousPageNumber) {
        this.previousPageNumber = previousPageNumber;
    }

    public int getNextPageNumber() {
        return nextPageNumber;
    }

    public void setNextPageNumber(int nextPageNumber) {
        this.nextPageNumber = nextPageNumber;
    }

    public void setPageInfo() throws SQLException {
        int totalRecordNumber = pageInfoDAO.totalRecordNumber();
        int limit=PAGE_COUNT;
        int offset=(currentPageNumber-1)*limit;
        List pageObject = pageInfoDAO.findPageObject(limit, offset);
        //（A+B-1）/B把A/B的数学结果由默认的向下取整变为向上取整
        int totalPageNumber=(totalRecordNumber + PAGE_COUNT - 1) / PAGE_COUNT;
        if(totalPageNumber==0){
            totalPageNumber=1;
        }
        int previousPageNumber=1;
        int  nextPageNumber=totalPageNumber;
        if(currentPageNumber>1){
            previousPageNumber=currentPageNumber-1;
        }
        if(currentPageNumber<totalPageNumber){
            nextPageNumber=currentPageNumber+1;
        }
        setCurrentPageNumber(currentPageNumber);
        setPageList(pageObject);
        setNextPageNumber(nextPageNumber);
        setPreviousPageNumber(previousPageNumber);
        setTotalPageNumber(totalPageNumber);
        setTotalRecordNumber(totalRecordNumber);
    }
}
