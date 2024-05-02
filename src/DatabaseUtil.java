import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String RESOURCE_NAME = "java:comp/env/jdbc/webapp";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws NamingException, SQLException {
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup(RESOURCE_NAME);
        System.out.println("Connected to database: " + dataSource.getConnection().getCatalog());
        return dataSource.getConnection();
    }
}