# Relatório de Design Arquitetural e Padrões de Projeto - MyFood

## 1. Descrição Geral do Design Arquitetural do Sistema

O sistema MyFood adota a **Arquitetura em Camadas (Layered Architecture)**. Este modelo organiza o código em pacotes horizontais, onde o fluxo de controle é estritamente unidirecional (de cima para baixo), isolando a lógica de negócios das preocupações de infraestrutura e interface 

[Image of Layered Architecture Diagram]
.

A camada mais externa (Apresentação) é implementada utilizando o **Padrão de Projeto Facade**, que serve como o único ponto de entrada para o sistema, encapsulando sua complexidade interna.

***

## 2. Principais Componentes e Suas Interações

| Componente/Camada | Pacote/Classe | Responsabilidade Principal | Interage com |
| :--- | :--- | :--- | :--- |
| **Apresentação/Interface** | `Facade.java` | Recebe comandos, gerencia o ciclo de vida do sistema e delega a execução. | Serviços |
| **Serviços (Lógica de Negócios)** | `br.ufal.ic.p2.myfood.service` | Contém toda a Lógica de Negócios, regras de validação e coordenação de ações. | Modelos, Utilitários, Outros Serviços |
| **Modelos (Domínio)** | `br.ufal.ic.p2.myfood.model` | Armazena o estado e a estrutura de dados (Entidades). | Serviços |
| **Infraestrutura (Persistência)** | `SistemaService.java` | Lida com a serialização e desserialização de dados via XML. | Modelos |

### 2.1. Modelagem de Entidades (Domínio)

| Classe | Tipo | Atributos Principais | Métodos Chave |
| :--- | :--- | :--- | :--- |
| **`Usuario`** | **Abstrata** | `idUsuario` (int), `nome`, `email`, `senha`, `endereco` (String) | Construtor para inicialização e geração de ID, Getters/Setters. |
| **`Cliente`** | **Concreta** | *Herda de Usuario* | Construtores. |
| **`Empresario`** | **Concreta** | *Herda de Usuario*, `cpf` (String) | `getCpf()`, `setCpf()`. |
| **`Empresa`** | **Abstrata** | `idEmpresa` (int), `idDono` (int), `nome`, `endereco` (String) | Construtor para geração de ID, Getters/Setters, `toString()` (para `getEmpresasDoUsuario`). |
| **`Restaurante`** | **Concreta** | *Herda de Empresa*, `tipoCozinha` (String) | `getTipoCozinha()`, `setTipoCozinha()`. |
| **`Produto`** | **Entidade** | `idProduto` (int), `idEmpresa` (int), `nome` (String), `valor` (float), `categoria` (String) | Construtor para geração de ID, Getters/Setters, `toString()` (para `listarProdutos`). |
| **`Pedido`** | **Entidade** | `idPedido` (int), `idCliente` (int), `idEmpresa` (int), `estado` (String), `itens` (List<ItemPedido>), `valorTotal` (float) | Construtor, `getItens()`, `setValorTotal()` (com lógica de ajuste de valor). |
| **`ItemPedido`** | **Value Object** | `idProduto` (int), `quantidade` (int), `valorUnitario` (float) | Construtores, Getters/Setters. |
***

## 3. Padrões de Projeto Adotados

### A. Nome do Padrão de Projeto: Facade (Fachada)

#### Descrição Geral
O padrão Facade pertence à categoria **Estrutural**. Ele define uma interface simplificada e de alto nível para um subsistema complexo.

#### Problema Resolvido
**Reduzir o Acoplamento e a Complexidade de Uso.** Garante que o cliente do sistema (o *driver* de teste) não precise orquestrar chamadas a múltiplos serviços e classes de utilidade.

#### Identificação da Oportunidade
A necessidade de expor comandos (ex: `criarUsuario`, `login`) de forma coesa e centralizada, e de isolar o carregamento/salvamento do estado do sistema.

#### Aplicação no Projeto
A classe `Facade.java` atua como o único ponto de contato do sistema. Ela centraliza a gestão das listas estáticas de dados e delega a execução para as classes de Serviço apropriadas, como `ProdutoService.criarProduto(...)`.

---

### B. Nome do Padrão de Projeto: Template Method (Implícito)

#### Descrição Geral
O Template Method pertence à categoria **Comportamental**. Ele define o esqueleto de um algoritmo, deixando que partes específicas (os "ganchos" ou *hooks*) sejam implementadas em blocos de código flexíveis, mantendo a estrutura geral imutável.

#### Problema Resolvido
**Duplicação de Código em Fluxos Padronizados.** Garante que um processo com etapas fixas (como a obtenção de atributos) seja executado de forma consistente, permitindo variação apenas na etapa de mapeamento.

#### Identificação da Oportunidade
O fluxo repetitivo de obtenção de atributos (`getAtributoEmpresa`, `getAtributoProdudo`) que segue a estrutura: 1) Busca do objeto, 2) Mapeamento do atributo, 3) Lançamento de exceção padronizada.

#### Aplicação no Projeto
Nos métodos como `ProdutoService.getAtributoProdudo`, o *template* é definido pelo `stream().filter().findFirst()` seguido por `.orElseThrow()`.

A etapa do Template Method é o bloco `.map()`, onde o `switch` (que seleciona a estratégia) é o *hook* que define qual lógica de recuperação de dado deve ser executada.

---

### C. Nome do Padrão de Projeto: Strategy (Estratégia)

#### Descrição Geral
O Strategy pertence à categoria **Comportamental**. Ele define uma família de algoritmos, encapsula cada um deles (as Estratégias) e os torna intercambiáveis.

#### Problema Resolvido
**Isolar a Lógica de Variação de Comportamento.** Evita que o código fique cheio de condicionais complexas para selecionar qual ação tomar com base em um parâmetro de entrada.

#### Identificação da Oportunidade
A necessidade de responder diferentemente dependendo da `String atributo` de entrada (ex: retornar um valor diferente para `"nome"`, `"endereco"` ou `"dono"`).

#### Aplicação no Projeto
Os métodos `EmpresaService.getAtributoEmpresa` e `ProdutoService.getAtributoProdudo` utilizam o `switch` expression para selecionar a estratégia de obtenção de dados. Cada `case` (ex: `case "dono"`, `case "valor"`) representa uma estratégia distinta para extrair e formatar a informação solicitada.

---

### D. Princípio de Design: Liskov Substitution Principle (LSP)

#### Descrição Geral
O LSP (Princípio da Substituição de Liskov), um princípio **SOLID**, estabelece que objetos de uma superclasse devem ser substituíveis por objetos de suas subclasses sem afetar a corretude do programa.

#### Problema Resolvido
**Promover o Polimorfismo Seguro.** Permite que o sistema trate usuários de diferentes tipos de forma uniforme.

#### Identificação da Oportunidade
A hierarquia de classes no domínio, principalmente `Usuario` (base) com as subclasses `Cliente` e `Empresario`, e `Empresa` com `Restaurante`.

#### Aplicação no Projeto
* As listas de dados são declaradas usando os tipos base (ex: `List<Usuario>`).
* O método `UsuarioService.getAtributo` aceita qualquer `Usuario` e utiliza `instanceof Empresario` para acessar o atributo exclusivo `cpf`, garantindo que o tipo pai possa ser substituído pelos filhos.
