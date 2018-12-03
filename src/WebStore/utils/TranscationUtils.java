package WebStore.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TranscationUtils {
    ThreadLocal<Connection> threadLocal;

    public TranscationUtils() throws SQLException {
        threadLocal=new ThreadLocal<>();
        threadLocal.set(C3P0Utils.getConnection());
    }

     public  Connection getConnection(){
        return threadLocal.get();
     }

     public void beganTranscation() throws SQLException {
        getConnection().setAutoCommit(false);
     }

     public void commit() throws SQLException {
        getConnection().commit();
     }

     public void rollback() throws SQLException {
        getConnection().rollback();
     }
}
