import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentAdder {
    private DatabaseConnector databaseConnector;

    public StudentAdder(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void addStudent() {
        try {
            Connection connection = databaseConnector.getConnection();

            Scanner scanner = new Scanner(System.in);

            System.out.print("Nom de l'étudiant : ");
            String lastName = scanner.nextLine();
            System.out.print("Prénom de l'étudiant : ");
            String firstName = scanner.nextLine();
            int age = 0;
            boolean isValidInput = false;

            while (!isValidInput) {
            System.out.print("Veuillez saisir l'âge de l'étudiant : ");
            String input = scanner.nextLine();

            try {
                age = Integer.parseInt(input);
                isValidInput = true;
            } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez saisir un nombre valide.");
            }
            }
            System.out.print("Notes de l'étudiant (séparées par des virgules) : ");
            String grades = scanner.nextLine();

            String query = "INSERT INTO students (first_name, last_name, age, grades) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.setString(4, grades);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("L'étudiant a été ajouté avec succès à la base de données.");
            } else {
                System.out.println("Erreur lors de l'ajout de l'étudiant à la base de données.");
            }

            statement.close();
            // Fermez la connexion lorsque vous avez terminé
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
