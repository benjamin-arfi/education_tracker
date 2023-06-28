import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class StudentDeleter {
    private DatabaseConnector databaseConnector;

    public StudentDeleter(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void deleteStudent() {
        try {
            Connection connection = databaseConnector.getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.print("Veuillez entrer l'ID de l'étudiant à supprimer :");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            // Préparer la requête de suppression de l'étudiant
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            // Exécuter la requête de suppression

            if (resultSet.next()) {
                String deleteQuery = "DELETE FROM students WHERE id = ?";
                PreparedStatement Deletestatement = connection.prepareStatement(deleteQuery);
                Deletestatement.setInt(1, studentId);
                int rowsUpdated = Deletestatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("L'étudiant a été supprimé avec succès de la base de données.");
                } else {
                    System.out.println("Erreur lors de la supression des informations de l'étudiant.");
                }

                statement.close();
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID spécifié.");
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
