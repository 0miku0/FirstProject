package WebStore.service.interfaces;

import java.sql.SQLException;

public interface ActiveUserService {
    Boolean userActive(String activeCode) throws SQLException;
}
