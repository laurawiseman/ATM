package det.atm;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
// import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@Configuration
class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean 
    CommandLineRunner initDatabase(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        Transaction t1 = new Transaction(4L, Transaction.Type.DEPOSIT, 100D);
        Transaction t2 = new Transaction(4L, Transaction.Type.DEPOSIT, 50D);
        Transaction t3 = new Transaction(5L, Transaction.Type.DEPOSIT, 200D);

        ArrayList<Long> lst = new ArrayList<Long>();
        ArrayList<Long> lst2 = new ArrayList<Long>();

        return args -> {
            transactionRepository.save(t1);
            transactionRepository.save(t2);
            transactionRepository.save(t3);

            transactionRepository.findAll().forEach(transaction -> log.info("Preloaded transaction: " + transaction));

            lst.add(t1.getNum());
            lst.add(t2.getNum());
            lst2.add(t3.getNum());

            accountRepository.save(new Account("Laura Wiseman", 150D, lst));
            accountRepository.save(new Account("Nathan Vella", 200D, lst2));

            accountRepository.findAll().forEach(account -> log.info("Preloaded account: " + account));
        };
    }
}
