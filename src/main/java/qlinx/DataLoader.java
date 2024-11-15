package qlinx;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class DataLoader {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Bean
    CommandLineRunner loadData() {
        return args -> transactionTemplate.execute(status -> {
            insertData();
            return null;
        });
    }

    public void insertData() {
        for (int i = 1; i <= 100000; i++) {
            entityManager.createNativeQuery("INSERT INTO translation (msg, lang, translation_text) VALUES (?1, ?2, ?3)")
                    .setParameter(1, "MSG_" + i)
                    .setParameter(2, i % 2 == 0 ? "en" : "ko")
                    .setParameter(3, "Translation Text " + i)
                    .executeUpdate();
        }
    }
}
