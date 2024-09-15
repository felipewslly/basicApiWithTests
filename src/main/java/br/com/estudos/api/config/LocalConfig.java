package br.com.estudos.api.config;


import br.com.estudos.api.domain.Users;
import br.com.estudos.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;


    @PostConstruct
    public void startDb(){
        Users userNews = new Users(1, "felipe", "felippe@gmai.com", "1234");
        Users userNews1 = new Users(2, "fernando", "fer@gmai.com", "1234");

            userRepository.saveAll(List.of(userNews, userNews1));
    }
}
