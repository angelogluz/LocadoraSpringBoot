# Trabalho 01 - Baseada no commit #fba7f3a459
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

# Exemplos de teste nas Classes do Spring Boot
## Teste na controller
```java
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioControllerExemploTeste {

        @Autowired
        UsuarioDAO usuarioRepository;

        @Mock
        Model model;
    
        UsuarioController controller;

        @Before
        public void setup(){
            controller = new UsuarioController(usuarioRepository);
        }
        @Test
        public void testControllerInserindoUsuario() {
            Usuario user = new Usuario();
            user.setNome("Isa");
            controller.save(user,model);
            Usuario u = usuarioRepository.findByNome("Isa");
            assertThat(u.getNome(),is(user.getNome()));
        }
}
}
```
## Teste no Repository
```java
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryExemploTeste {

    @Autowired
    private UsuarioDAO clientRepository;

    @Before
    public void setup() {
        
    }

    @Test
    public void testFindById() {
        Usuario usuario = new Usuario("Foo");
        Usuario u1 = clientRepository.save(usuario);
        Usuario u = clientRepository.findById(u1.getId()).get();
        assertThat(u.getNome(),is("Foo"));
    }
```
}