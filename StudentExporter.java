import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentExporter {
    private DatabaseConnector databaseConnector;

    public StudentExporter(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void exportToCSV(String filePath) {
        try (Connection connection = databaseConnector.getConnection();
             FileWriter writer = new FileWriter(filePath)) {
            // Préparer la requête pour récupérer tous les étudiants
            String sql = "SELECT * FROM students";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Exécuter la requête et obtenir le résultat
            ResultSet resultSet = statement.executeQuery();

            // Écrire l'en-tête du fichier CSV
            writer.append("ID,First Name,Last Name,Age,Grades\n");

            // Parcourir les résultats et écrire chaque ligne dans le fichier CSV
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String grades = resultSet.getString("grades");

                writer.append(id + ",");
                writer.append(firstName + ",");
                writer.append(lastName + ",");
                writer.append(age + ",");
                writer.append(grades + "\n");
            }

            System.out.println("Les données des étudiants ont été exportées avec succès dans le fichier : " + filePath);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
