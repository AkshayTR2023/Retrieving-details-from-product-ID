//ProductServlet.java
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ecom.DatabaseConfig;
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String dbUsername;
    private String dbPassword;
    private String dbUrl;

    public void init() {
        // Load database credentials from a separate class or configuration file
        DatabaseConfig config = new DatabaseConfig();
        dbUsername = config.getUsername();
        dbPassword = config.getPassword();
        dbUrl = config.getUrl();

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");

        // Database connection and query
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM products WHERE productId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productId);
            ResultSet resultSet = statement.executeQuery();

            // Check if the product exists
            if (resultSet.next()) {
                String name = resultSet.getString("productName");
                double price = resultSet.getDouble("productPrice");

                // Display product details
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h2>Product Details:</h2>");
                out.println("<p><b>ID:</b> " + productId + "</p>");
                out.println("<p><b>Name:</b> " + name + "</p>");
                out.println("<p><b>Price:</b> " + price + "</p>");
                out.println("</body></html>");
            } else {
                // Product not found
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h2>Error:</h2>");
                out.println("<p>Product not found</p>");
                out.println("</body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//---------------------------------------------------------------------------------//
