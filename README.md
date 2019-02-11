Api desenvolvida para integrar unidades de transportes de poa com possíveis clientes, possibilitando buscas regionalizadas por unidade de transporte ou ponto específico. Podendo ser utilizado em aplicativos mobile para auxiliar o usuário a encontrar facilmente uma unidade de transporte próxima ou busca-las e exibir sua tragetória.

Tecnologias Utilizadas
Java 8
Spring Boot( MVC, Data, Security)
JPA
Hibernate
Banco de dados Postgres 11
JUnit4
Plugin POSTGIS

Escolhido framework Spring Boot para auxiliar na configuração do ambiente do projeto, utilizando hibernate e jpa para gerenciar a camada de dados, com repositórios e consultas por convenção maximizando a legibilidade e portabilidade.
Utilizado banco postgres com plugin postgis para tratar as requisições com coordenadas de maneira eficiente sem algoritimos complexos no backend.
Teste unitário com JUnit4
Arquitetura de Serviços

BACKUP DENTRO DA PASTA DO PROJETO.
- CASO TENHA DIFICULDADES EM IMPORTAR ALTERE O DDL-AUTO NAS PROPRIEDADES DO PROJETO e execute a instrução sql: CREATE EXTENSION postgis;.
Postman Collection disponível dentro da pastas do projeto.

Security configurado como basic auth:
usuario: admin, senha: 1234567890 
ou 
headers: Key = "Authorization", Value = "Basic YWRtaW46MTIzNDU2Nzg5MA==" 


-------------------------------------------------------------------------------------------
1) Parte 1 

Integração com api http://www.datapoa.com.br/dataset/poatransporte/resource/bc69456c-1fff-4e6f-875b-c3e6d7163c3c, neste site realizar a integração com as operações de linhas de ônibus e itinerário, quando realizar a consulta deve se verificar se a linha já esta cadastrada ou se tem alguma diferença de dados (Linha ou itinerario), caso tenha deve ser atualizada ou caso não tenha deve ser cadastrada no sistema. 

Operações (Integração) 

- Listar as linhas de ônibus - http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o 

- Listar itinerário de uma determinada unidade de transporte - http://www.poatransporte.com.br/php/facades/process.php?a=il&p=5566 

 

2) Parte 2 

Criar Api para ir filtrando as linhas de ônibus por nome. 

 

3) Parte 3 

Criar um CRUD de cliente, onde consiga cadastrar suas linhas de Ônibus. 

 

4) Parte 4 

Criar uma operação que faça o filtro de raio de uma determinada unidade de transporte trazendo somente as latitudes que se encaixam nessa busca. Exemplo na request conterá a unidade de transporte a latitude e longitude e o raio que desejo filtrar. 

 

5) Parte 5 

Criar uma operação que receba uma latitude, longitude e raio, deverá retornar quais unidades de transporte se encontraram nessa faixa de busca. 

 

6) Documentação da API. 

