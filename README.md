# UpRef - Sistema de Gerenciamento de Referências Científicas


## Autores



Este projeto foi desenvolvido por Lucas M. Sobrinho e Luiza B. R. Soezima como parte do curso de MAC0350 - Introdução ao Desenvolvimento de Software.





## Escopo
O UpRef é um sistema desenvolvido para facilitar a busca, organização e monitoramento de referências científicas. Esse sistema permite aos usuários encontrar artigos relevantes, armazenar suas referências e configurar alertas para novas publicações dentro de suas áreas de interesse.


## Funcionalidades Principais:


### 1. Busca de Referências
   - Os usuários podem buscar referências no banco de dados utilizando keywords, título ou autor como critérios de pesquisa.
   - A busca é robusta, capaz de lidar com consultas complexas e retornar resultados relevantes.


### 2. Armazenamento de Referências
   - Capacidade de armazenar um conjunto de referências para cada usuário.
   - As referências podem ser adicionadas manualmente ou encontradas através das buscas realizadas.


### 3. Monitoramento/Alerta de Artigos
   - Implementação de um sistema de monitoramento que permite aos usuários configurar alertas para novas publicações.
   - Os alertas são personalizáveis e baseados em keywords, título ou autor especificados pelos usuários.


## Requisitos funcionais:
- **Buscar**: Os usuários devem poder buscar referências no banco de dados utilizando keyword, título ou autor como parâmetros de busca. Há referências no banco de dados por keyword, título, autor;
- **Armazenar**: O sistema deve ser capaz de armazenar um conjunto de referências para cada usuário. Há um conjunto de referências de dado usuário;
- **Criar Monitor/Alerta de Artigos**: Deve ser possível configurar alertas de novos artigos baseados em keyword, título ou autor. Há um monitor/alerta de artigos baseado em keyword, título, autor.


## Requisitos Técnicos


- **Banco de Dados:** Utiliza-se um banco de dados para armazenar as referências e realizar as buscas.
- **Frontend:** Desenvolvimento de uma interface de usuário (UI) responsiva e intuitiva para facilitar a interação.
- **Backend:** Implementação da lógica de negócios, funcionalidades de busca e armazenamento.  O backend é desenvolvido em Kotlin utilizando o framework Ktor. O ambiente de desenvolvimento recomendado é o IntelliJ IDEA.


## Licença


Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT) - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
