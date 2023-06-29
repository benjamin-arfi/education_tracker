import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentViewer {
    private DatabaseConnector databaseConnector;

    public StudentViewer(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void viewAllStudentsWithPagination() {
    int pageSize = 10; // Nombre d'étudiants à afficher par page

    try {
        Connection connection = databaseConnector.getConnection();

        // Préparer la requête pour récupérer le nombre total d'étudiants
        String countQuery = "SELECT COUNT(*) as total FROM students";
        PreparedStatement countStatement = connection.prepareStatement(countQuery);
        ResultSet countResultSet = countStatement.executeQuery();
        countResultSet.next();
        int totalStudents = countResultSet.getInt("total");

        // Calculer le nombre total de pages en fonction de la taille de la page
        int totalPages = (int) Math.ceil((double) totalStudents / pageSize);

        // Demander à l'utilisateur de saisir le numéro de page
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer le numéro de page (1-" + totalPages + "): ");
        int page = scanner.nextInt();

        // Vérifier la validité du numéro de page saisi
        if (page < 1 || page > totalPages) {
            System.out.println("Numéro de page invalide.");
            return;
        }

        // Calculer l'offset pour la requête SQL en fonction du numéro de page
        int offset = (page - 1) * pageSize;

        // Préparer la requête pour récupérer les étudiants avec la pagination
        String query = "SELECT * FROM students LIMIT ? OFFSET ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, pageSize);
        statement.setInt(2, offset);
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
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}

