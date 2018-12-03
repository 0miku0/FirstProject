package WebStore.service.interfaces;

import java.sql.SQLException;

public interface AjaxService {
    boolean isUserUsernameAvailable(String username) throws SQLException;

}
