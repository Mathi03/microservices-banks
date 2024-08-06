package com.dev.bankservicemexico.dataseed;

import com.dev.bankservicemexico.entity.Account;
import com.dev.bankservicemexico.entity.Client;
import com.dev.bankservicemexico.repository.AccountRepository;
import com.dev.bankservicemexico.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;

    @Value("${application.code}")
    protected String codeCountry;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        // accountRepository.deleteAll();
        // clientRepository.deleteAll();
        if (clientRepository.count() == 0) {
            Client user1 = new Client();
            user1.setName("George Miguel");
            user1.setSurname("Puma Salcedo");
            user1.setDni("74867621");
            user1.setEmail("george@gmail.com");
            user1.setAddress("address 123");
            user1.setPhone(969266730);
            user1.setNationality("peruana");
            Account account1 = new Account();
            account1.setClient(user1);
            account1.setBalance(BigDecimal.valueOf(500.50));
            account1.setNumberAccount(codeCountry+"123456");
            clientRepository.save(user1);
            accountRepository.save(account1);
        }
        System.out.println(clientRepository.count());
    }
}
