# POTTER Challenge

Este repositório tem como implementação o desafio https://github.com/dextra/challenges/blob/master/backend/MAKE-MAGIC-PT.md

---

Execute o comando abaixo na raiz do projeto para criar a imagem Docker da aplicação:
```
docker build -t potter/web .
```
O código fonte e os arquivos .pom serão copiados para e todo o download de dependências e build da aplicação será realizado na geração da imagem.

Para rodar a aplicação, utilize o comando abaixo (passar a API_KEY como variável de ambiente do container):
```
docker run -it -p 8080:8080 -e API_KEY=<inserir api key aqui> potter/web
```

Após a aplicação iniciar, acesse a documentação da API em http://localhost:8080/challenge/swagger-ui.html#/
Será possível testar a API a partir do próprio swagger.

---

## Considerações

- Na persistência de novos personagens foi considerada como chave única o nome do personagem.
- Foi utilizado o H2 como base de dados local para persistência dos dados.

---

## TODO list

Pontos que ficaram em aberto para serem implementados

- Tratamento de estado da potterApi (API utilizada para buscar as casas)
- Testes de integração
- Uso do Spring Cache