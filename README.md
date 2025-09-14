ROTEIRO
Criar uma API Restful que consuma dados da API de Cep(https://viacep.com.br/) e salve em banco de dados utilizando kotlin com framework spring

1- A api deve expor endpoints para inserir, buscar, atualizar e deletar as informações baseados no código CEP. Configure swagger para utilização dos endpoints. Devolva os códigos https conforme padrão API rest.

2- Realize validações em backend.
- Não inserir um CEP já existente
- Deletar/Atualizar/Buscar apenas registros existentes.
OBS. Caso falhe em alguma validação lance uma exceção. Utilize o @RestControllerAdvice para capturar e devolver como retorno no endpoint. Utilize códigos https de devolução conforme erro ocasionado.

3- Utilize a biblioteca open feign para comunicação externa da aplicação.

4- As informações devem ser salvas em banco H2. Utilize migration para criar as estruturas de bancos

5- As propriedades da aplicação devem ficar no arquivo application.yml

6- Utilize o padrão MVC para estruturar o projeto.

7- Crie testes unitários para os serviços criados utilizando mockito.

8- Crie testes de integração para a camada de serviços criados utilizando mockmvc. Para chamada externa no caso do cep utilizar o WireMock.

9- Crie testes de integração para a camada de Controller dos endpoints expostos da aplicação com mockmvc. Para chamada externa no caso do cep utilizar o WireMock.
