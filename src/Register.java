import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.UserEntity;

@WebServlet(
        name = "Register",
        urlPatterns = {"/Register"}
)
public class Register extends HttpServlet {
    public Register() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        UserDao userDao = new UserDao();
        UserEntity newUser = new UserEntity();
        newUser.setName(name);
        newUser.setPassword(password);
        newUser.setEmail(email);
        userDao.saveUser(newUser);
        response.sendRedirect("welcome.jsp");
    }
}