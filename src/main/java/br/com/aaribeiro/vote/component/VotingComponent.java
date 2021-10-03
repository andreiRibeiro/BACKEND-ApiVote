package br.com.aaribeiro.vote.component;

import br.com.aaribeiro.vote.config.HerokuappConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Slf4j
@Component
public class VotingComponent {
    private final HerokuappConfig herokuappConfig;

    public boolean checkCpf(String cpf){
        try {
            ResponseEntity<Object> response = new RestTemplate().exchange(
                    String.format("%s/users/%s", herokuappConfig.getUrl(), cpf),
                    HttpMethod.GET,
                    new HttpEntity<>(null),
                    Object.class
            );
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
