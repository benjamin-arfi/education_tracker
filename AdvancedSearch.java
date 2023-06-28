import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdvancedSearch {
    private DatabaseConnector databaseConnector;

    public AdvancedSearch(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void performAdvancedSearch() {
        try {
            Connection connection = databaseConnector.getConnection();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Recherche avancée d'étudiants");
            System.out.println("Veuillez sélectionner les critères de recherche :");

            System.out.print("Âge minimum : ");
            int minAge = scanner.nextInt();

            System.out.print("Âge maximum : ");
            int maxAge = scanner.nextInt();

            System.out.print("Moyenne minimum des notes : ");
            double minAverageGrade = scanner.nextDouble();

            // Préparer la requête de recherche avancée avec des paramètres
            String sql = "SELECT * FROM students WHERE age >= ? AND age <= ? AND grades >= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, minAge);
            statement.setInt(2, maxAge);
            statement.setDouble(3, minAverageGrade);

            // Exécuter la requête et obtenir le résultat
            ResultSet resultSet = statement.executeQuery();

            // Afficher les résultats de la recherche
            List<Map<String, Object>> searchResults = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> student = new HashMap<>();
                student.put("id", resultSet.getInt("id"));
                student.put("first_name", resultSet.getString("first_name"));
                student.put("last_name", resultSet.getString("last_name"));
                student.put("age", resultSet.getInt("age"));
                student.put("grades", resultSet.getString("grades"));

                searchResults.add(student);
            }

            if (searchResults.isEmpty()) {
                System.out.println("Aucun étudiant ne correspond aux critères de recherche.");
            } else {
                System.out.println("Résultats de la recherche :");
                for (Map<String, Object> student : searchResults) {
                System.out.println("ID: " + student.get("id"));
                System.out.println("Prénom: " + student.get("first_name"));
                System.out.println("Nom: " + student.get("last_name"));
                System.out.println("Âge: " + student.get("age"));
                System.out.println("Notes: " + student.get("grades"));
                System.out.println("-----------------------------------------");
            }

            // Fermer les ressources
            resultSet.close();
            statement.close();
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
