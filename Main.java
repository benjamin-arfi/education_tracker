import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_database";
        String username = "root";
        String password = "binyaminarfi26+";

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
                        studentViewer.viewAllStudents();
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
