import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderStudents {
    private DatabaseConnector databaseConnector;

    public OrderStudents(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void orderStudents() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez choisir le champ de tri :");
        System.out.println("1. Nom");
        System.out.println("2. Prénom");
        System.out.println("3. Âge");
        System.out.println("4. Moyenne des notes");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Vider la ligne vide restante après nextInt()

        String sortBy;
        switch (choice) {
            case 1:
                sortBy = "last_name";
                break;
            case 2:
                sortBy = "first_name";
                break;
            case 3:
                sortBy = "age";
                break;
            case 4:
                sortBy = "grades";
                break;
            default:
                System.out.println("Choix invalide.");
                return;
        }

        List<Map<String, Object>> sortedStudents = new ArrayList<>();
        try {
            String query = "SELECT * FROM students ORDER BY " + sortBy;
            PreparedStatement statement = databaseConnector.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> student = new HashMap<>();
                student.put("id", resultSet.getInt("id"));
                student.put("first_name", resultSet.getString("first_name"));
                student.put("last_name", resultSet.getString("last_name"));
                student.put("age", resultSet.getInt("age"));
                student.put("grades", resultSet.getString("grades"));

                sortedStudents.add(student);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (sortedStudents.isEmpty()) {
            System.out.println("Aucun étudiant trouvé dans la base de données.");
        } else {
            System.out.println("Liste des étudiants triés par " + sortBy + ":");
            for (Map<String, Object> student : sortedStudents) {
                System.out.println("ID: " + student.get("id"));
                System.out.println("Prénom: " + student.get("first_name"));
                System.out.println("Nom: " + student.get("last_name"));
                System.out.println("Âge: " + student.get("age"));
                System.out.println("Notes: " + student.get("grades"));
                System.out.println("-----------------------------------------");
            }
        }
    }
}
