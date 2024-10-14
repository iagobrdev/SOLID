# Relatório de Produtos - Aplicação de Geração de Relatórios

## Descrição

Esta aplicação é responsável por gerar relatórios de produtos em diferentes formatos, como **csv**, **txt**, **xls** e **xlsx**. Ela foi construída seguindo os princípios de **SOLID** e **Domain-Driven Design (DDD)**, garantindo uma arquitetura robusta, modular e extensível.

## Funcionalidades

- Geração de relatórios de produtos nos formatos:
    - **csv** (Valores Separados por Vírgulas)
    - **txt** (Texto Simples Separado por `;`)
    - **xls / xlsx** (Excel)
- API para solicitar a geração de relatórios através de requisições HTTP.
- Flexibilidade para adicionar novos formatos de relatório facilmente.

Para gerar relatórios em outros formatos, basta trocar o valor da extensão no parâmetro `extension` na URL:

- **csv**: `?extension=csv`
- **txt**: `?extension=txt`
- **xls**: `?extension=xls`
- **xlsx**: `?extension=xlsx`

## Conceitos Seguidos

### Por que a aplicação segue SOLID?

1. **Single Responsibility Principle (SRP)**:
    - Cada classe da aplicação tem uma responsabilidade única. Por exemplo, `ReportCSV` lida exclusivamente com a geração de relatórios CSV, e `ReportTXT` cuida da geração de relatórios TXT. Isso evita acoplamento desnecessário entre funcionalidades diferentes, facilitando a manutenção e a evolução do código.

2. **Open/Closed Principle (OCP)**:
    - O código está **aberto para extensão** e **fechado para modificação**. Se você precisar adicionar um novo formato de relatório (como `ReportPDF`), basta criar uma nova classe sem alterar as classes existentes. Isso garante que o sistema seja extensível sem comprometer a estabilidade do código.

3. **Liskov Substitution Principle (LSP)**:
    - As subclasses (`ReportCSV`, `ReportTXT`) podem substituir a classe base (`AbstractReportTextGenerator`) sem alterar o comportamento esperado do sistema. Isso permite que o código que usa a abstração (`ReportGenerator`) funcione corretamente, independentemente da implementação concreta.

4. **Interface Segregation Principle (ISP)**:
    - A interface `ReportGenerator` define apenas os métodos necessários para a geração de relatórios, garantindo que as classes implementem apenas o que realmente precisam. Isso evita a criação de interfaces grandes e genéricas que forçam as classes a implementar métodos que não utilizam.

5. **Dependency Inversion Principle (DIP)**:
    - A aplicação depende de **abstrações** (interfaces), não de implementações concretas. O uso de `ReportFactory` para fornecer as implementações corretas de `ReportGenerator` permite que o código seja flexível e testável, pois as classes dependem de contratos e não de implementações específicas.

### Por que a aplicação segue DDD (Domain-Driven Design)?

1. **Separação de Camadas**:
    - A aplicação está estruturada em camadas claramente definidas, seguindo as boas práticas do DDD:
        - **Camada de Aplicação**: Contém o `ReportServiceImpl` e o `ReportController`, que orquestram as interações e casos de uso.
        - **Camada de Domínio**: Contém os objetos de domínio, como o `ProductDto`, que representa os dados que estão sendo manipulados.
        - **Camada de Infraestrutura**: Contém as implementações técnicas e específicas, como `ReportCSV`, `ReportTXT`, e o `ReportFactory`.

2. **Isolamento do Domínio**:
    - O domínio da aplicação, como os dados dos produtos e a definição dos DTOs, está separado das preocupações técnicas de geração de relatórios. Isso garante que as regras de negócio possam evoluir independentemente da implementação técnica de infraestrutura.

3. **Uso de Fábricas e Abstrações**:
    - O uso do padrão **Factory** (`ReportFactory`) permite que as dependências sejam resolvidas de forma centralizada e controlada, um conceito essencial em DDD. A fábrica cuida de fornecer a implementação correta de `ReportGenerator` com base na extensão do arquivo, mantendo o código desacoplado e extensível.


## Exemplo de Uso

Você pode gerar um relatório em diferentes formatos alterando a extensão no parâmetro da URL. Aqui está um exemplo utilizando o `curl` para gerar um relatório:

```bash
curl --location 'http://localhost:8080/generateReport?extension=xlsx' \
--header 'Content-Type: application/json' \
--data '[
    {
      "name": "Laptop",
      "price": 1500.00,
      "quantity": 10
    }
]
