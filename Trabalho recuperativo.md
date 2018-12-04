# Trabalho recuperativo - Teste de Software

## Atente para os requisitos, datas e regras para todas as ações

**Para começar:** Tirar um fork do projeto. 

**Entrega** Ao finalizar as tarefas, submeter um <code>pull request</code> com os testes gerados. O pull request deverá ser enviado no dia 10, entre as 18h e 19h. **Nem antes, nem depois**.

**Atenção!** Para cada bug detectado deverá ser aberta uma Issue com o devido relato (a qualquer momento a Issue pode ser aberta). No caso do bug já ter sido relatado por um colega, o relato não deve se repetir, mas pode receber adendos nos comentários. **Leia o [Guia de Contribuição](https://github.com/angelogluz/LocadoraSpringBoot/blob/master/CONTRIBUTING.md) antes**.

Criar os testes para validar os requisitos especificados na sequencia.

**Atenção!** Os testes podem ser realizados em **apenas uma das camadas,** recomendado que seja realizado nas entidades.

Um exemplo de teste que pode ser utilizado como template segue no projeto.

Nas entidades, cada validação retorna uma mensagem diferente.

Todo teste que não cumpre requisito de persistência, em caso de teste na controller ou dao, lança um <code> ConstraintViolationException </code> 

É competência avaliada a escolha de melhores práticas e diversidade dos teste e a variedade de estratégias.

Caso execute e queira logar no sistema, o usuário é **admin** e a senha **password**.

# Requisitos de teste

## Cliente
* O CPF do cliente não é obrigatório, mas caso preenchido precisa ser válido, não apenas em formato mas também matematicamente
**Mensagem de validação:** "O CPF não é válido";

* O CPF deve ser persistido no banco sem separadores
**Mensagem de validação:** Nenhuma;

* O campo nome deve ser um valor entre 4 e 50, inclusive
**Mensagem de validação:** "Um nome deve possuir entre 4 e 50 caracteres";

* O nome do cliente não deve aceitar caracteres especiais, nem números
**Mensagem de validação:** "O nome não deve possuir simbolos ou números";

* O nome do cliente deverá ser um campo único
**Mensagem de validação:** Não possui. Lançará uma Exception;

* O nome não deverá aceitar espaços em branco no início e no fim
**Mensagem de validação:** Não possui. A aplicação deve elimiar os espaços;

* Independente de como digitado, o nome do cliente deverá ser armazenado com a primeira letra do nome/sobrenome maiúscula
**Mensagem de validação:** Não possui. A aplicação deverá fazer as correções;


## Filme
* O nome deverá ser um campo único
**Mensagem de validação:** Não possui. Lançará uma Exception;;

* O nome não deverá aceitar espaços em branco no início e no fim
**Mensagem de validação:** Não possui. A aplicação deve elimiar os espaços;

* O nome deve possuir entre 2 e 100 caracteres, inclusive.
**Mensagem de validação:** "Um filme deve possuir entre 2 e 100 caracteres";

* O estoque do filme não pode ser negativo
**Mensagem de validação:** "O Estoque deve ser positivo";

* O valor da locação não deverá ultrapassar dois dígitos e o número de casas após a vírgula deverá ser dois.
**Mensagem de validação:** "O Preço deve ter no máximo dois dígitos";

* O valor da locação do filme deverá ser positivo
**Mensagem de validação:** "O Valor da locação deve ser positivo";

## Locação
* Uma locação não deverá ser realizada sem um cliente
**Mensagem de validação:** "Um cliente deve ser selecionado";

* Uma locação deverá possuir pelo menos 1 filme
**Mensagem de validação:** "Pelo menos um filme deve ser selecionado";

* Uma locação de filme sem estoque não poderá ser realizada
**Mensagem de validação:** Sem mensagem. Uma Exception deverá ser lançada;

* Uma locação não pode ser realizada sem data de locação
**Mensagem de validação:** "A data de locação não deve ser nula";

* Uma locação não pode ser realizada sem data de devolução
**Mensagem de validação:** "A data de retorno não deve ser nula";

* A data de devolução do filme não pode ser uma data no passado
**Mensagem de validação:** "A data deve retorno deve ser futura";

* O valor da locação deve possuir no máximo dois dígitos antes e depois da vírgula
**Mensagem de validação:** "O Preço deve ter no máximo dois dígitos";

* O valor da locação deve ser sempre positivo
**Mensagem de validação:** "O valor da locação deve ser positivo";

* Caso um filme tenha sua entrega prevista para domingo, deverá registrada para segunda-feira.
**Mensagem de validação:** Nenhuma. Uma Exception deverá lançada;

* Ao alugar um filme a data de entrega deve ter o número de dias incrementado de forma proporcional ao número de filmes
alugados. 
**Mensagem de validação:** Nenhuma. Uma Exception deverá lançada;

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
}
```
## Teste na Entity
```java
@Test
    public void naoDeveValidarUmNomeComDoisCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
```
