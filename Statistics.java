import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Statistics {
    private DatabaseConnector databaseConnector;

    public Statistics(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void calculateAndDisplayStatistics() {
        try {
            Connection connection = databaseConnector.getConnection();

            // Calcul de la moyenne des notes de la classe
            double averageGrade = calculateAverageGrade(connection);
            System.out.println("Moyenne des notes de la classe : " + averageGrade);

            // Calcul du nombre d'étudiants par tranche d'âge
            calculateAndDisplayStudentCountByAgeRange(connection);
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private double calculateAverageGrade(Connection connection) throws SQLException {
        double averageGrade = 0.0;
        String sql = "SELECT AVG(grades) FROM students";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            averageGrade = resultSet.getDouble(1);
        }

        resultSet.close();
        statement.close();

        return averageGrade;
    }

    private void calculateAndDisplayStudentCountByAgeRange(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Âge minimum : ");
        int minAge = scanner.nextInt();
        System.out.print("Âge maximum : ");
        int maxAge = scanner.nextInt();
       String sql = "SELECT age, COUNT(*) as count FROM students WHERE age >= ? AND age <= ? GROUP BY age";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, minAge);
        statement.setInt(2, maxAge);

        // Exécuter la requête et obtenir le résultat
        ResultSet resultSet = statement.executeQuery();
        System.out.println("Nombre d'étudiants par tranche d'âge :");
        while (resultSet.next()) {
            int age = resultSet.getInt("age");
            int count = resultSet.getInt("count");
            System.out.println("Âge : " + age + ", Nombre d'étudiants : " + count);
        }

        resultSet.close();
        statement.close();
    }
}
