package br.com.estudos.api.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() { //configuração padrao do ModelMapper
        return new ModelMapper();
    }
}
