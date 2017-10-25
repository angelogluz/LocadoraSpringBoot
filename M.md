# Prova ADS-M

**Para começar:** Tirar um fork do projeto ou atualizar o existente. 

**Entrega** Ao finalizar as tarefas, submeter um <code>pull request</code> com os testes gerados.

Criar os testes para validar os requisitos especificados na sequencia.

**Atenção!** Os testes devem ser realizados em **apenas uma das camadas,** preferencialmente nas entidades.

Um exemplo de teste que pode ser utilizado como template segue no projeto.

Nas entidades, cada validação retorna uma mensagem diferente.

Todo teste que não cumpre requisito de persistência, em caso de teste na controller ou dao, lança um <code> ConstraintViolationException </code> 

É competência avaliada a escolha de melhores práticas e diversidade dos teste.

Caso execute e queira logar no sistema, o usuário é **admin** e a senha **password**.

# Requisitos de teste

## Cliente
* O CPF do cliente não é obrigatório, mas caso preenchido precisa ser válido
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
