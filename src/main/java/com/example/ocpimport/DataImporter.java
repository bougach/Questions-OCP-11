package com.example.ocpimport;

import com.example.ocpimport.model.Question;
import com.example.ocpimport.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class DataImporter implements CommandLineRunner {

    private final QuestionRepository repository;

    public DataImporter(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/questions.csv"))
        );

        String line;
        reader.readLine(); // skip header

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int number = Integer.parseInt(parts[0].trim());
            String link = parts[1].trim();
            String category = parts[2].trim();

            Question q = new Question();
            q.setQuestionNumber(number);
            q.setLink(link);
            q.setCategory(category);

            repository.save(q);
        }

        reader.close();
        System.out.println("✔️ Données insérées avec succès !");
    }
}
