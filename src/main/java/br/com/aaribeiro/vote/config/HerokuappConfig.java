package br.com.aaribeiro.vote.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class HerokuappConfig {

    @Value("${api.herokuapp}")
    private String url;
}
