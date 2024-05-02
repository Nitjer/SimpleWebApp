import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UserEntity;

public class UserDao {

    // Метод для получения соединения с базой данных
    private Connection getConnection() throws NamingException, SQLException {
        // Получаем контекст JNDI
        InitialContext initialContext = new InitialContext();
        // Получаем ссылку на ресурс данных JNDI
        DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/webapp");

        // Выводим информацию о подключенной базе данных
        System.out.println("Connected to database: " + dataSource.getConnection().getCatalog());

        // Получаем соединение с базой данных из ресурса данных
        return dataSource.getConnection();
    }
    // Метод для проверки логина и пароля пользователя
    public boolean checkLogin(String name, String password) {
        try (Connection connection = getConnection()) {
            // Подготавливаем запрос к базе данных
            String sql = "SELECT * FROM users";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Выполняем запрос
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Перебираем все комбинации имен и паролей
                    while (resultSet.next()) {
                        String storedName = resultSet.getString("name");
                        String storedPassword = resultSet.getString("password");
                        // Если найдено совпадение, возвращаем true
                        if (name.equals(storedName) && password.equals(storedPassword)) {
                            return true;
                        }
                    }
                }
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace(); // Обработка ошибок доступа к базе данных
        }
        // Если не найдено совпадение, возвращаем false
        return false;
    }

    public void saveUser(UserEntity user) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO new_table (idUser, name, password, email) VALUES (null, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getPassword());
                statement.setString(3, user.getEmail());
                statement.executeUpdate();
            }
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

}
