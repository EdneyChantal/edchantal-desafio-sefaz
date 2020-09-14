Prezados 

  Segue neste pacote a implementação do Desafio sefaz do Candidato Edney Chantal.
  Descreveremos abaixo um descritivo da arquitetura
  
  O projeto foi criado pelo jhispter com a arquitetura de MicroService . 
  
  Microservice Rest Api 
  
  a pasta apibank é um microservico Rest api com security jwt key usando banco de dados h2 gravação em disco 
     para executar , basta executar mvnw dentro da raiz da pasta
	 
  MicroService Service Discovery
  a pasta jhipster_registry é um microservico de Service Discovery , que faz a integração entre os microservicos, ele deve ser executado primeiro
     para executar , basta executar o arquivo bat exec_jhipster_registry
	 
  Microservice Gateway Front 
  a pasta gateway é um microservico de gateway , que funciona tambem como front-end da aplicação para levantar o servico , basta executar mvnw
  na raiz da pasta gateway
  
  
  A aplicação não pode ser recriado os entity , pois fiz alguns ajustes manuais que podem ser sobrescritos.

  
  