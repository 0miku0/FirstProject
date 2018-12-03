package WebStore.utils.PageInfo;

import java.sql.SQLException;
import java.util.List;

public interface PageInfoDAO<T> {
    int totalRecordNumber() throws SQLException;
    List<T> findPageObject(int limit, int offset)  throws SQLException;
}