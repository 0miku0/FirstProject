package WebStore.utils.PageInfo;

import WebStore.utils.PageInfo.PageInfoDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface SearchPageInfoDAO<T> extends PageInfoDAO {
    int totalRecordNumberMultiCondition(HashMap<String,String> parameterMap) throws SQLException;
    List<T> findPageObjectMultiCondition(int limit, int offset,HashMap<String,String> parameterMap)  throws SQLException;

}
