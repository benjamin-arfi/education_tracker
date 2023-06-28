import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentSearcher {
    private DatabaseConnector databaseConnector;

    public StudentSearcher(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void searchStudentById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez entrer l'ID de l'étudiant à rechercher :");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Vider la ligne vide restante après nextInt()

        try {
            Connection connection = databaseConnector.getConnection();

            // Préparer la requête pour rechercher l'étudiant par ID
            String sql = "SELECT * FROM students WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);

            // Exécuter la requête et obtenir le résultat
            ResultSet resultSet = statement.executeQuery();

            // Vérifier si un étudiant a été trouvé
            if (resultSet.next()) {
                // Extraire les informations de l'étudiant
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                String grades = resultSet.getString("grades");

                // Afficher les informations de l'étudiant
                System.out.println("ID : " + studentId);
                System.out.println("Prénom : " + firstName);
                System.out.println("Nom : " + lastName);
                System.out.println("Âge : " + age);
                System.out.println("Notes : " + grades);
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID spécifié.");
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

