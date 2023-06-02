package Repository.JDBC;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    //private Properties jdbcProps;
//    private static final Logger logger = LogManager.getLogger();

    //public JdbcUtils(Properties properties) {
        //jdbcProps = properties;
    //}
    private Connection instance = null;

    private Connection getNewConnection() {
        //logger.traceEntry();
        String url = "jdbc:sqlite:C:\\Users\\flale\\Desktop\\ISS\\ISS\\SGCM.sqlite";
        //logger.info("Connecting to database: {}", url);
        //logger.info("user: {}", user);
        //logger.info("pass: {}", pass);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //logger.traceExit(connection);
        return connection;
    }

    public Connection getConnection(){
        //logger.traceEntry();
        try{
            if(instance==null||instance.isClosed()){
                instance=getNewConnection();
            }
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB "+e);
        }
        //logger.traceExit(instance);
        return instance;
    }
}
