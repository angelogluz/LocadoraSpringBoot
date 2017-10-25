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

# Falha ao rodar o Maven?

Em caso de falha ao executar o maven, é provável que o motivo seja o Cache do Netbeans. 

Neste caso, remova todo conteúdo da pasta <code> \~/AppData/Local/Netbeans/Cache</code>
e também do diretório <code> \~/AppData/Roaming/Netbeans/8.2/var</code>. Onde "8.2" representa a versão da IDE instalada.


