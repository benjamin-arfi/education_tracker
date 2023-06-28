import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentModifier {
    private DatabaseConnector databaseConnector;

    public StudentModifier(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void modifyStudent() {
        try {
            Connection connection = databaseConnector.getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.print("ID de l'étudiant à modifier : ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Vider la ligne vide restante après nextInt()

            String selectQuery = "SELECT * FROM students WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, studentId);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                System.out.print("Nouveau nom de l'étudiant : ");
                String lastName = scanner.nextLine();
                System.out.print("Nouveau prénom de l'étudiant : ");
                String firstName = scanner.nextLine();
                System.out.print("Nouvel âge de l'étudiant : ");
                int age = scanner.nextInt();
                scanner.nextLine(); // Vider la ligne vide restante après nextInt()
                System.out.print("Nouvelles notes de l'étudiant (séparées par des virgules) : ");
                int grades = scanner.nextInt();
                scanner.nextLine();

                String updateQuery = "UPDATE students SET first_name = ?, last_name = ?, age = ?, grades = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, firstName);
                updateStatement.setString(2, lastName);
                updateStatement.setInt(3, age);
                updateStatement.setInt(4, grades);
                updateStatement.setInt(5, studentId);

                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Les informations de l'étudiant ont été mises à jour avec succès.");
                } else {
                    System.out.println("Erreur lors de la mise à jour des informations de l'étudiant.");
                }

                updateStatement.close();
            } else {
                System.out.println("Aucun étudiant trouvé avec l'ID spécifié.");
            }

            resultSet.close();
            selectStatement.close();

            // Fermez la connexion lorsque vous avez terminé
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

