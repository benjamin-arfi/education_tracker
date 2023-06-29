
import java.sql.SQLException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        
        DatabaseConnector databaseConnector = new DatabaseConnector(url, username, password);
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = -1;

            while (choice != 0) {
                System.out.println("Que souhaitez-vous faire ?");
                System.out.println("1. Ajouter un étudiant");
                System.out.println("2. Modifier un étudiant");
                System.out.println("3. Supprimer un étudiant");
                System.out.println("4. Afficher tous les étudiants");
                System.out.println("5. Rechercher un étudiant");
                System.out.println("6. trier la liste des étudiants");
                System.out.println("7. Rechercher un étudiant ");
                System.out.println("8. Moyenne du nombre d'étudiant ");
                System.out.println("9. Exporter les donnes ");
                System.out.println("0. Quitter");

                choice = scanner.nextInt();
                scanner.nextLine(); // Vider la ligne vide restante après nextInt()

                switch (choice) {
                    case 1:
                        StudentAdder studentAdder = new StudentAdder(databaseConnector);
                        studentAdder.addStudent();
                        break;
                    case 2:
                        StudentModifier studentModifier = new StudentModifier(databaseConnector);
                        studentModifier.modifyStudent();
                        break;
                    case 3:
                        StudentDeleter studentDeleter = new StudentDeleter(databaseConnector);
                        studentDeleter.deleteStudent();
                        break;
                    case 4:
                        StudentViewer studentViewer = new StudentViewer(databaseConnector);
                        studentViewer.viewAllStudentsWithPagination();
                        break;
                    case 5:
                        StudentSearcher studentSearcher = new StudentSearcher(databaseConnector);
                        studentSearcher.searchStudentById();
                        break;
                    case 6:
                        OrderStudents orderStudents = new OrderStudents(databaseConnector);
                        orderStudents.orderStudents();
                        break;
                    case 7:
                        AdvancedSearch advancedSearch = new AdvancedSearch(databaseConnector);
                        advancedSearch.performAdvancedSearch();
                        break;
                    case 8:
                        Statistics statistics = new Statistics(databaseConnector);
                        statistics.calculateAndDisplayStatistics();
                        break;
                    case 9:
                        StudentExporter studentExporter = new StudentExporter(databaseConnector);
                        String filePath = "etudiants.csv"; // Remplacez par le chemin complet de votre fichier
                        studentExporter.exportToCSV(filePath);
                        break;
                    case 0:
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide.");
                        break;
                }
            }
        }

        try {
            databaseConnector.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
