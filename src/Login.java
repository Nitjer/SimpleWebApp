import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "/Login", urlPatterns = "/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        DatabaseUtil databaseUtil = new DatabaseUtil();
        try {
            // Получаем соединение с базой данных
            Connection connection = databaseUtil.getConnection();

            // Создаем SQL запрос для проверки наличия пользователя с заданным именем и паролем
            String sql = "SELECT * FROM new_table WHERE name=? AND password=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, pass);
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Если результат запроса содержит хотя бы одну запись, значит пользователь существует
                    if (resultSet.next()) {
                        // Перенаправляем пользователя на страницу welcome.jsp
                        response.sendRedirect("welcome.jsp");
                    } else {
                        // Пользователь не найден, остаемся на странице index.jsp
                        response.sendRedirect("index.jsp");
                    }
                }
            }

        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Данный метод используется для обработки GET запросов
    }
}

