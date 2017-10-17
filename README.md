# LocadoraSpringBoot
Projeto Spring Boot para criação dos testes unitários e bug report

# Instruções para execução
Crie um banco de dados mysql com o nome de ```locadora```

Rotas:
``` 
localhost:8080
localhost:8080/usuario
localhost:8080/filme
localhost:8080/locacao
```

# API REST para teste

**GET** requisições para ```/api/usuario``` retorna uma lista de usuários em formato JSON

**GET** requisições para ```/api/usuario/1``` retorna o usuário com o ID 1

**POST** requisições para ```/api/usuario``` com um objeto Usuario JSON cria um novo usuário

**PUT** requisições para ```/api/usuario/1``` com um objeto Usuario JSON atualiza o usuário com ID 1

**DELETE** requisições para ```/api/usuario/1``` deleta o usuário com ID 1

**DELETE** requisições para ```/api/usuario``` deleta todos os usuários

**As mesmas rotas estão disponíveis para ```Filme``` e ```Locacao```.**



# Atividade
* Trabalhe em grupo (até 5 integrantes)
* Tire um fork do projeto e analise o código
* Desenvolva classes e métodos de teste para a aplicação
* Todos os testes devem estar documentados com javadoc
* Para cada bug encontrado deve ser aberta uma issue no github, relatando o problema encontrado
* Os bugs podem ser de qualquer natureza
* Os testes podem envolver mais ferramentas além do JUnit
* Os grupos devem apresentar o código gerado no seu fork na aula seguinte, para depois realizar o pull request
* **Bugs já reportados não devem ser duplicados. Podem ser adicionadas novas informações nos comentários**
