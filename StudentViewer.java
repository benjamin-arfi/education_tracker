import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentViewer {
    private DatabaseConnector databaseConnector;

    public StudentViewer(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void viewAllStudents() {
        try {
            Connection connection = databaseConnector.getConnection();

            // Préparer la requête pour récupérer tous les étudiants
            String sql = "SELECT * FROM students";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Exécuter la requête et obtenir le résultat
            ResultSet resultSet = statement.executeQuery();

            // Afficher les informations des étudiants
            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String grades = resultSet.getString("grades");

                System.out.println("ID : " + studentId);
                System.out.println("Prénom : " + firstName);
                System.out.println("Nom : " + lastName);
                System.out.println("Âge : " + age);
                System.out.println("Notes : " + grades);
                System.out.println("----------------------");
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

