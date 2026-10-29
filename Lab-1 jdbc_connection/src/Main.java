import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/StudentsDB";
        String username = "root";
        String password = "passwords";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection =
                    DriverManager.getConnection(url, username, password);

            System.out.println("Established Connection");

            Statement statement = connection.createStatement();

            String sql =
                    "SELECT AVG(grade) AS average_grade FROM students";

            ResultSet resultSet =
                    statement.executeQuery(sql);

            if (resultSet.next()) {

                double averageGrade =
                        resultSet.getDouble("average_grade");

                System.out.println(
                        "Average Grade: " + averageGrade
                );
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}