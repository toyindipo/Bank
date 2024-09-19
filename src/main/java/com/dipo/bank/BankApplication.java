package com.dipo.bank;

import com.dipo.bank.datastore.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankApplication {
    private Database database;

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }

    @Autowired
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
           database.seedDatabase();
        };
    }
}
