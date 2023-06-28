import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;

public class StudentExporter {
    private DatabaseConnector databaseConnector;

    public StudentExporter(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void exportToCSV(String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            List<Student> students = databaseConnector.getAllStudents();

            // Écrire l'en-tête du fichier CSV
            String[] header = { "ID", "Prénom", "Nom", "Âge", "Notes" };
            writer.writeNext(header);

            // Écrire les données des étudiants
            for (Student student : students) {
                String[] data = { String.valueOf(student.getId()), student.getFirstName(), student.getLastName(),
                        String.valueOf(student.getAge()), student.getGrades() };
                writer.writeNext(data);
            }

            System.out.println("Export des données vers le fichier CSV réussi : " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
