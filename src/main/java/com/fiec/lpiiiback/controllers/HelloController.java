package com.fiec.lpiiiback.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public List<Teste> sayHello(){
        List<Teste> testes = new ArrayList<>();
        for(int i=0; i<10; i++) {
            testes.add(Teste.builder()
                    .preco(1299.00)
                    .nome("Notebook Positivo")
                    .email("suporte@loja.com")
                    .descricao("Notebook Positivo com Windows 11")
                    .imagem("https://m.media-amazon.com/images/I/51oAl9k6BML._AC_SY450_.jpg")
                    .build());
            testes.add(Teste.builder()
                    .preco(1800.00)
                    .nome("Notebook Positivo II")
                    .email("suporte@loja.com")
                    .descricao("Notebook Positivo com Windows 11 e ram de 6GB")
                    .imagem("https://fujiokadistribuidor.vteximg.com.br/arquivos/ids/215535")
                    .build());
            testes.add(Teste.builder()
                    .preco(2399.00)
                    .nome("Notebook Positivo III")
                    .email("suporte@loja.com")
                    .descricao("Notebook Positivo com Windows 11 e ram de 8GB")
                    .imagem("https://lojapositivo.vteximg.com.br/arquivos/ids/158127-800-800/1000x1000_Positivo_N14Z97_Ang-2_Final_vermelho.jpg?v=637503207181300000")
                    .build());
        }
        return testes;
    }

    @GetMapping("/{someone}")
    public String sayHelloToSomeone(@PathVariable("someone") String someone){
        return "Hello World to " + someone;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Teste {
    private String nome;
    private String email;
    private Double preco;
    private String descricao;
    private String imagem;
}
