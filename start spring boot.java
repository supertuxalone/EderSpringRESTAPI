***********************************************
**********SPRING-BOOT-COM-API-REST*************
***********************************************

#Critérios minimos 
	- Java JDK	
	- Java JRE
	- Configuração de Variavel de Ambiente no PATH 
	
	- IDE - sts-4.15.3.RELEASE - Spring Tool Suite 4
		- Check for Updates
	
		- Marketplace - Instalados
			- https://marketplace.eclipse.org/content/eclipse-enterprise-java-and-web-developer-tools
			- https://marketplace.eclipse.org/content/eclipse-web-developer-tools-0/promo
			- https://marketplace.eclipse.org/content/spring-tools-4-aka-spring-tool-suite-4
	
	Acessar 
		- https://start.spring.io/
			- Informar com os dados os campos disponiveis
			- Dependencias 
				-Spring Web WEB
					Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
				-Spring Data JPA SQL
					Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
				-Rest Repositories WEB
					Exposing Spring Data repositories over REST via Spring Data REST.
				-MySQL Driver SQL
					MySQL JDBC and R2DBC driver.
				-PostgreSQL Driver SQL
					A JDBC and R2DBC driver that allows Java programs to connect to a PostgreSQL database using standard, database independent Java code.	
					
		- Generate

 **** Iniciando o Projeto ****
	
	1 - ]
	Na IDE - Spring Tool Suite 4
		File > Import > General > Archive File [Selecione o local onde o Download do projeto foi gerado] > Finish
		
	2 - ]
	localizar caminho/arquivo: 
	//esse arquivo é onde devemos configuração de conexão com o Banco de Dados
		ProjetoSpring\src\main\resources\application.properties
			spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
			spring.datasource.url=jdbc:mysql://localhost:3306/curso-API-Rest
			spring.datasource.username=root
			spring.datasource.password=Tel#452567

			spring.jpa.show-sql=true
			spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect

			#responsavel por criar as tabelas no banco
			spring.jpa.hibernate.ddl-auto=update

			spring.sql.init.mode=always
				
		//nesse caso foi configuração para o Banco de Dados Mysql
		
	3 - ]
	em <algumacoisa>SpringApplication.java
		//é classe principal que recebe todas anotações e compartamentos que o Spring tera ao ter essa classe compilada 
		atualizar 
		
			//pra ser que é spring
			@SpringBootApplication
			@EntityScan(basePackages = {"eder.springProject.ProjetoSpring.model"})
			@ComponentScan(basePackages = {"eder.*"})
			//qual a pasta que vai ficar as interface
			@EnableJpaRepositories(basePackages = {"eder.springProject.ProjetoSpring.repository"})
			@EnableTransactionManagement
			@EnableWebMvc
			@RestController
			@EnableAutoConfiguration
			public class ProjetoSpringApplication {

				public static void main(String[] args) {
					SpringApplication.run(ProjetoSpringApplication.class, args);
				}

			}
			
		3.1 - Criar Classe ServletInitializer.java
				public class ServletInitializer extends SpringBootServletInitializer {

					@Override
					protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
						return application.sources(ProjetoSpringApplication.class);
					}

				}
	
	Start atraves do Run As > Java Application a Aplicação afim de verificar a conexão "ok" estabelecida com o banco de Dados !
			no Console.log
			
	2022-09-13 18:14:47.467  INFO 14016 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-09-13 18:14:47.475  INFO 14016 --- [           main] e.s.P.ProjetoSpringApplication           : Started ProjetoSpringApplication in 4.24 seconds (JVM running for 5.122)

	Acesse no Navegador e acesssar o localhost:8080
		
4 - ]
	* Criar pacotes	
		4.1 - Na pasta src/main/java	
				com o botão direto New > Package e informar o nome do novo Package
					Criar Package eder.springProject.ProjetoSpring.model, eder.springProject.ProjetoSpring.repository, eder.springProject.ProjetoSpring.controller
				
			4.1.2 - em Model 
				//pacotes que deve conter as classes (que vão svirar tabelas ) devem conter os atrbibutos
				1° classe = user.class
					
					//anotação que determina que essa classe deverá gerar uma tabela no banco
					@Entity
					public class user implements Serializable {

						//anotação que criar um id auto incrementeação no caso usando o atributo Id
						private static final long serialVersionUID = 1L;
						@Id
						//como ele vai gerar o auto incremental
						@GeneratedValue(strategy = GenerationType.AUTO)

						private Long id;
						
						@Column(unique = true)
						private String login;
						
						private String senha;
						private String nome;
						
						
					//criar os metodos getters e setters
					
					//criar o hashCode baseado no atributo Id afim de diferenciar para posteriores mestodos de busca ou comparação
					
		4.2 - Restartar o servidor ele deve criar a(s) tabelas anotadas. 					
		
5 - ] no Package Controller
		New > class > IndexController.java	
			Atualizar
				
		//para que esse classe de controller possa trabalhar com o Rest Full		
		@RestController
		@RequestMapping(value = "/usuario")
		public class IndexController {

			//Serviço REST FULL
			//ao acessar o endereço localhost raiz o esse metodo GetMapping é invocado e executa oque for informado	
			@GetMapping(value = "/", produces = "application/json")
			public ResponseEntity init() {
				return new ResponseEntity("Olá Usuario Gato Aqui REST Full Com Spring Boot !", HttpStatus.OK);
				
			}

		}
		
	5.1 - ]
		Informando com Parametros 
		Atualizar 
			@GetMapping(value = "/", produces = "application/json")
			//na url localhost da forma que esta abaixo ele pode e quer receber parametros de nome e salario 
			// porem não é requerido e em caso de informar ele mostra um valor default editado ou null
			public ResponseEntity init(@RequestParam(value = "nome", required = false, defaultValue = "Nome não Informado" ) String nome,
			@RequestParam (value = "salario", required = false) Long salario) {
		
				System.out.println("Parametro Recebido " + nome +  " e " + salario);
		
				return new ResponseEntity("Olá Usuario Gato Aqui REST Full Com Spring Boot ! : " + nome  + " Salário é : " + salario , HttpStatus.OK);
		
	}
	
6 - ]Retornar para tela de Usuario com os dados dentro
	//como não tem tela front a reposta iniciamente vai vir em Json, porem qualquer framework pode 
	//receber esse resposta e usar para exibir na pagina.
	
	atualizar
		public ResponseEntity<user> init() {
		
		user Usuario = new user();
		Usuario.setId(15L);
		Usuario.setLogin("admin");
		Usuario.setNome("Administrador");
		Usuario.setSenha("123456");

		user Usuario2= new user();
		Usuario2.setId(25L);
		Usuario2.setLogin("lucas");
		Usuario2.setNome("Lucas Silva");
		Usuario2.setSenha("123456");
		
		List<user> usuarios = new ArrayList<user>();
		usuarios.add(Usuario);
		usuarios.add(Usuario2);		
		
		return new ResponseEntity(usuarios, HttpStatus.OK);
		
	//o ResponseEntity recebe a classe usuario, seta os valores manualemente + lista<usuarios> e retorna via http.
	
 7 - ]
	criar a Persistencia 
		no Package > eder.springProject.ProjetoSpring.repository > New > Interface > UserRepository
			
			//anotar com @Repository
			@Repository
			public interface UserRepository extends JpaRepository<user, Long>{
	
			}
			
		//extends JPARepository ou CrudRepository
	
	7.1 - ]
		atualizar
			@Autowired /* de CDI seria @Inject*/
			private UserRepository userRepository;
			
			//get Mapping que retona um registro ao se passar o id especifico http://localhost:8080/usuario/15
			@GetMapping(value = "/{id}", produces = "application/json")	
			public ResponseEntity<user> userparams(@PathVariable (value = "id") Long id) {
				
				/*injentando o UserRepository é possivel usar qualquer metodo pronto que a interface disponibiliza*/	
				Optional<user> usuario = userRepository.findById(id);
						
				return new ResponseEntity<user>(usuario.get(), HttpStatus.OK);
				
			}
			//get Mappign que retona todos os registro http://localhost:8080/usuario/
			@GetMapping(value = "/", produces = "application/json")	
			public ResponseEntity<List<user>> userall () {
				
				/*injentando o UserRepository é possivel usar qualquer metodo pronto que a interface disponibiliza*/	
				List<user> list = (List<user>) userRepository.findAll();
						
				return new ResponseEntity<List<user>>(list, HttpStatus.OK);
				
			}
			
	7.2 - ]
		atualizar
			"cadastro de novo" regitros usando PostMan
			atualizar
				// POST Mappign que grava um novo regsitro na tabela
				// http://localhost:8080/caduser/
				@PostMapping(value = "/caduser", produces = "application/json")
				public ResponseEntity<user> caduser(@RequestBody user users) {

					user usersave = userRepository.save(users);

					return new ResponseEntity<user>(usersave, HttpStatus.OK);

				}
				
	7.3 - ]
		atualizar	
			"atualiza" um registro existente usando Postman
				// PUT Mappign que grava um novo regsitro na tabela
				// http://localhost:8080/caduser/
				@PutMapping(value = "/caduser", produces = "application/json")
				public ResponseEntity<user> update (@RequestBody user users) {

					/*incluir outra rotinas antes de atualizar*/
					
					user usersave = userRepository.save(users);

					return new ResponseEntity<user>(usersave, HttpStatus.OK);

				}
			
			
	7.4 - ]
		atualizar
		//Delete deltanando uma resgitro passando somente o id http://localhost:8080/16
		@DeleteMapping(value = "/{id}", produces = "application/text")
		public String deleuser (@PathVariable("id") Long id) {
			
			userRepository.deleteById(id);

			return "ok";

		}
				
8 - ]
	Instalação do Maven
		o site	https://maven.apache.org/download.cgi
		fazer download de
		https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip

		- Baixar e descompactar em c:\
		- Se precisar criar variavel de ambiente MAVEN_HOME e apontar o caminho descompactado
		- No Path informar o caminho do Past /bin
		- Testar no PowerShell 
			#mvn 
				[INFO] Scanning for projects...
				[INFO] ------------------------------------------------------------------------
				[INFO] BUILD FAILURE
				[INFO] ------------------------------------------------------------------------
				[INFO] Total time:  0.077 s
				[INFO] Finished at: 2022-09-14T22:51:12-03:00
				[INFO] ------------------------------------------------------------------------
				[ERROR] No goals have been specified for this build. You must specify a valid lifecycle phase or a goal in the format <plugin-prefix>:<goal> or <plugin-group-id>:<plugin-artifact-id>[:<plugin-version>]:<goal>. Available lifecycle phases are: validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy, pre-clean, clean, post-clean, pre-site, site, post-site, site-deploy. -> [Help 1]
				[ERROR]
				[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
				[ERROR] Re-run Maven using the -X switch to enable full debug logging.
				[ERROR]
				[ERROR] For more information about the errors and possible solutions, please read the following articles:
				[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/NoGoalSpecifiedException
							
	
9 - ] 
	Contexto
		em application.properties
		atualizar 
			server.servlet.context-path=/springProjectRestAPI
		
		//fazendo isso o acesso ao sistema passa e ser atavaes do URL http://localhost:8080/springProjectRestAPI/ não somente http://localhost:8080/
		
	9.1 - ]
		Revisão do JDK, JRE e Compilação de Projeto.
			https://projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/473240914/idcurso/1/idvideoaula/754
		
	9.2 - ]
		Gerando Jar executável da Aplicação Spring Boot
			Incialmente o maven reclamou que na versão 17 do java, e apresentou alguma problema com esse versão.
			- alterado 
				no pom.xml
				add - 
					<version>0.0.1-SNAPSHOT</version>
					<packaging>jar</packaging>
					<properties>
						<java.version>1.8</java.version>
						<maven.test.skip>true</maven.test.skip>
					</properties>
			
			- alterar
				 - em Windows > Preferences > JDK > 1.8
				 - em Project > Properties > Java Build Path  Order and Export > 1.8
				 - em Project > Properties > Project Facets > Java > 1.8
				 
		9.2.1 - 
			Em  C:\Users\z0167207\git\EderSpringRESTAPI\ProjetoSpring 
				executar no powershell
					#mvn package -e
					
				na pasta \target o mvn deverá criar todos pacotes necessários para a fim de criar o projeto
					[INFO] Replacing main artifact with repackaged archive
					[INFO] ------------------------------------------------------------------------
					[INFO] BUILD SUCCESS
					[INFO] ------------------------------------------------------------------------
					[INFO] Total time:  48.114 s
					[INFO] Finished at: 2022-09-16T17:16:05-03:00
					[INFO] ------------------------------------------------------------------------
					
				ao final na pasta na pasta \target terá um arquivo 
					ex: ProjetoSpring-0.0.1-SNAPSHOT.jar
				
				que pode ser executado no cmd com seguinte comando:
					 java -jar .\ProjetoSpring-0.0.1-SNAPSHOT.jar
					 
		- Assim já dá ter acesso de qualquer dispositivo na rede ao sistema sem usar a IDE.
		
	9.3 - ]
		Gerando WAR e Implantando no Servidor
			- alterado 
				no pom.xml
				add - 
					<version>0.0.1-SNAPSHOT</version>
					<packaging>war</packaging>
		9.3.1 - 
			Em  C:\Users\z0167207\git\EderSpringRESTAPI\ProjetoSpring 
				executar no powershell
					#mvn package -e			
					
			na pasta \target o mvn deverá criar o arquivo ProjetoSpring-0.0.1-SNAPSHOT.war
				[INFO] Replacing main artifact with repackaged archive
				[INFO] ------------------------------------------------------------------------
				[INFO] BUILD SUCCESS
				[INFO] ------------------------------------------------------------------------
				[INFO] Total time:  12.112 s
				[INFO] Finished at: 2022-09-16T18:11:44-03:00
				[INFO] ------------------------------------------------------------------------
				
			9.3.2 - copiar esse arquivo ProjetoSpring-0.0.1-SNAPSHOT.war para a pasta C:\apache-tomcat-10.0.23\webapps do servidor tomcat no servidor
				
		- Assim quando o servdior for startado o War que estiver nessa pasta \webapps vai subir.
		
10 - ]
	Criando Relacionamento um para muitos
		cria no package eder.springProject.ProjetoSpring.model
			new > class > userTelephone
				atualizar 
				
				@Entity //para virar uma tabela no banco 
				public class userTelephone {

					//id auto incremental
					@Id
					@GeneratedValue(strategy = GenerationType.AUTO)
					private Long id;

					private String numero;

					//quando criar a tabela, já criar a chave estrangeira 
					@org.hibernate.annotations.ForeignKey(name = "user_id")
					//informar que esse tabela tem realicionamento muitos para um usuario	
					@ManyToOne
					private user User;
					
				/*CRIAR OS GETTRS E SETTERS*/
				
		10.1 - ] 
			na classe user.java	
				atualizar
					/*anotação de um usuario pode possuir varios telefones*/
					@OneToMany(mappedBy = "User", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
					private List<userTelephone> telephones = new ArrayList<userTelephone>();
					// criar uma lista de telefone do tipo telefones
					
				/*criar a gettrs e setters*/
				
					public List<userTelephone> getTelephones() {
						return telephones;
					}

					public void setTelephones(List<userTelephone> telephones) {
						this.telephones = telephones;
					}
		
					
	/*SUBIR PARA VERFICAR SE A TABELA TELEPHONE e o RELACIONAMENTO DO TABELA USER FOI CRIAR COM SUCESSO*/
	
	
11 - ]
	Evitando recursividade e gerando o JSON de pai e filhos
		na classe userTelephone.java
			atualizar
					
				@JsonIgnore /*anotação que evita recursão de resultado em json*/
				@org.hibernate.annotations.ForeignKey(name = "user_id")
				@ManyToOne
				private user User;
				
	
12 - ]
	Cadastrando novos usuários telefones no END-POINT
		- Apesar do cadastro de novos usuarios estar funcinando atraves do Postman:
					
					
			//DADOS INFORMADOS
			{
					"id": "",
					"login": "moises",
					"senha": "362959",
					"nome": "Moises da Silva",
					
					"telephones": 
				[
					{
						"id": "",
						"numero": "9996262"
					},
					{
						"id": "",
						"numero": "3326151"
					},
					{
						"id": "",
						"numero": "3452162"
					}
				]
			},
	
			//RESULTADO 
			{
				"id": 26,
				"login": "moises",
				"senha": "362959",
				"nome": "Moises da Silva",
				"telephones": [
					{
						"id": 27,
						"numero": "99956262"
					},
					{
						"id": 28,
						"numero": "33248451"
					},
					{
						"id": 29,
						"numero": "88845621"
					}
				]
			}
			
		//não estava conseguindo associar o user_id do usuario cadastrado
		
		12.1 - ]
			Na classe de Controller IndexController.java	
				atualizar
			
				@PostMapping(value = "/caduser", produces = "application/json")
				public ResponseEntity<user> caduser(@RequestBody user users) {
					
					//varer  os registros de telefone
					for (int pos = 0; pos < users.getTelephones().size(); pos ++) {
						users.getTelephones().get(pos).setUser(users);
					}
					
				/**FAZER ISSO NO METODO PUT TBM/*/
				
			12.1.2 - 
				e na classe userTelephone.java
					@JsonIgnore /* anotação que evita recursão de resultado em json */
					@org.hibernate.annotations.ForeignKey(name = "user_id")
					@ManyToOne(optional = false) /*ATUALIZAR QUE O CAMPO user_id no Banco passe a ser Obrigatório */
					private user User;

			
13 - ]
	Cross Origin - Configuração em controler especifico
		13.1 - ]
			criar em ProjetoSpring\src\main\webapp\index.html
				<!DOCTYPE html>
				<html>
				<head>
				<meta charset="ISO-8859-1">
				<title>Insert title here</title>
				<script src="https://code.jquery.com/jquery-3.4.1.js"
					integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
					crossorigin="anonymous"></script>
				</head>
				<body>
					<script>
						$(document)
								.ready(

										function() {

											$
													.ajax(
															{
																url : "http://localhost:8080/springProjectRestAPI/usuario/",
																method : "GET"
															}).then(
															function(data, status, info) {

															});

										});
					</script>

				</body>
				</html>
				
		13.2 - ]
			anotar com @CrossOrigin
				@CrossOrigin /*para permitir todos o metodos da classe possa ser acessadas e utlizado por qualquer origem */
				@RestController
				@RequestMapping(value = "/usuario")
				public class IndexController { 
				
				ou 
				
				@CrossOrigin(origins = "localhost:8080") /*afim que restrigir que somente requisições dessa origem localhost poderam aceitar requisições da api passando o end-poit em questão */	
				// get Mappign que retona todos os registro http://localhost:8080/usuario/
				@GetMapping(value = "/", produces = "application/json")
				public ResponseEntity<List<user>> userall() {
				) 
							
14 - ]
	Cross Origin - Configuração centralizada
		atualizar ProjetoSpringApplication.java
		public class ProjetoSpringApplication implements WebMvcConfigurer {

			public static void main(String[] args) {
				SpringApplication.run(ProjetoSpringApplication.class, args);
			}

			/* MAPEAMENTO GLOBAL */
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				
				/*qual endpoint,                  qual metodo           e origem*/    
				registry.addMapping("/usuario/**").allowedMethods("*").allowedOrigins("*");

			}

		}
		
	/*PARA TESTAR VAMOS EXECURAR O ARQUIVOS INDEX.HTML POR FORA.*/
		- executando ele não deve exibir erros no console, porem até o momento 
		o javascript da pagina acessa a url: http://localhost:8080/springProjectRestAPI/usuario
		e metodo GET, porem não retorna nada, para um teste, iforme um alert e dos parametros
		passados, informe um a data + indice + o atributo:
		
			=> function(data, status, info) {
				 alert(data[0].id);
		
		o resultado é uma alert:
			Essa pagina diz
			2  
			
15 - ]
	Spring Security - Configurando ROLE
		/*CLASSE QUE REPRESENTA OS PAPEIS "ROLES" DO USUARIOS QUE VÃO ACESSAR O SISTEMA*/
		- criar em eder.springProject.ProjetoSpring.model > userRole.java
			atualizar
				@Entity
				@Table(name = "user_role")
				@SequenceGenerator(name = "seq_role", sequenceName = "seq_role", allocationSize = 1, initialValue = 1)
				public class userRole implements GrantedAuthority {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Id
					@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
					private Long id;

					private String descricaorole;
					
					//criar os gettrs e setters
					
		- para implements o GrantedAuthority, requer a depemdencia no pom.xml:
				<dependency>
					<groupId>org.springframework.security</groupId>
					<artifactId>spring-security-web</artifactId>
					<version>5.6.1</version>
				</dependency>
	
		- em sequida como sugestão do eclipse criar o metodo getAuthority:
			atualizar
				@Override
				public String getAuthority() {
					return this.descricaoRole;
				}
16 - ]
	Spring Security - Configurando Usuário
		atualizar	
			user.java
				@Entity
				public class user implements UserDetails {
					
			- como sugestão do eclipse criar todos metodos
				7 methods to implement:
				 getAuthorities()
				 getPassword()
				 getUsername()
				 isAccountNonExpired()
				 isAccountNonLocked()
				 isCredentialsNonExpired()
				 isEnabled()
				 
			- criados o metodos altere todos os return para "true";
				
			- atualizar metodos
				@Override
				public String getPassword() {
					return this.senha;
				}

				@Override
				public String getUsername() {
					return this.login;
				}

				/*acessos do usuario*/	
				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					return null;
				}
				
			- criar um novo private abaixo do telephones
						/*sempre que carregar o usuario tbm carregar os papeis "roles" dele*/		
				@OneToMany(fetch = FetchType.EAGER)
				/*criar uma tabela unica para os acessos que contem o uma coluna com id do user e o id do role + uma constraint "unique_role_user" */
				@JoinTable(name = "user_on_roles", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "role_id" }, name = "unique_role_user"),
				/*unificando as colunas das tabelas onde a user_id que aponta para a tabela user como refenrecnia  o campo id*/
				joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "user", unique = false, 
				foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)), 
				
				/*inverso informando é que campo role_id é fk que aponta para a tabela user_role como refenrecnia o campo id*/ 
				inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "user_role", unique = false, updatable = false, 
				foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
				
				private List<userRole> roles;
				
				atualizar
					@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					return roles;
				}
	
			- testar e verificar se as tabelas e os relacionamento foram criados.	
		//https://projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/0bIpdBwxxWg/idcurso/1/idvideoaula/770
				

17 - ]
	Spring Security - Configurando o Repository e o Service
		em UserRepository
			atualizar		
					
		/*query construida para que em determinado ponto do sistema só temos o login para ser validade, no caso o login ao sistema */	
		@Query("select u from user u where u.login = ?1")
		user findUserByLogin(String login);
		
		17.1 - ]
			criar package eder.springProject.ProjetoSpring.service
				e criar class ImplementacaoUserDetailsSercice.java				
					atualizar	
						@Service
						public class ImplementacaoUserDetailsSercice implements UserDetailsService{
	
					@Autowired
					private UserRepository userRepository;	

					@Override
					public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
						
						
						/*Consulta no banco o usuario*/
						
						user usuario = userRepository.findUserByLogin(username);
						
						if (usuario == null) {
							throw new UsernameNotFoundException("Usuário não foi encontrado");
						}
						
						/*Chama o construtor mais complexo com todos os argumentos booleanos definidos como {@code true}.*/
						return new User(usuario.getLogin(),
								usuario.getPassword(),
								usuario.getAuthorities());
					}

				}
				
		/*https://projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/ZEu0fZSMisY/idcurso/1/idvideoaula/771*/
		
18 - ]
	Spring Security - Configurando o Spring Security
		- parte central do spring security (toda a parte de segurança)
		
			- atualizar o pom.xml
				<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-config -->
				<dependency>
					<groupId>org.springframework.security</groupId>
					<artifactId>spring-security-config</artifactId>
					<version>5.6.1</version>
				</dependency>
			
			- 	criar package eder.springProject.ProjetoSpring.security
				e criar class WebConfigSecurity.java	
					atualizar
						
					/*Mapeaia URL, enderecos, autoriza ou bloqueia acessoa a URL*/
					@Configuration
					@EnableWebSecurity
					public class WebConfigSecurity extends WebSecurityConfigurerAdapter {
						
						@Autowired
						private ImplementacaoUserDetailsSercice implementacaoUserDetailsSercice;
						
						/*Configura as solicitações de acesso por Http*/
						@Override
						protected void configure(HttpSecurity http) throws Exception {

							/*Ativando a proteção contra usuário que não estão validados por TOKEN*/
							http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
							
							/*Ativando a permissão para acesso a página incial do sistema EX: sistema.com.br/index*/
							.disable().authorizeRequests().antMatchers("/").permitAll()
							.antMatchers("/index").permitAll()
							
							/*URL de Logout - Redireciona após o user deslogar do sistema*/
							.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
							
							/*Maperia URL de Logout e insvalida o usuário*/
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
							
							/*Filtra requisições de login para autenticação*/
							
							
							/*Filtra demais requisições paa verificar a presenção do TOKEN JWT no HEADER HTTP*/
						
						}	
			
						@Override
						protected void configure(AuthenticationManagerBuilder auth) throws Exception {

							/* Service que irá consultar o usuário no banco de dados */
							auth.userDetailsService(implementacaoUserDetailsSercice)

									/* Padrão de codigição de senha */
									.passwordEncoder(new BCryptPasswordEncoder());

						}

					}
					
		/*https://projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/gmvWEPFVARw/idcurso/1/idvideoaula/772*/
							
							
19 - ]
	Estrututa de classes JWT Token
		no pom.xml atualizar depemdencia
			<dependency>
				<groupId>io.jsonwebtoken</groupId>
				<artifactId>jjwt</artifactId>
				<version>0.9.1</version>
			</dependency>
		
	/*https://projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/uXWVGrMuYo8/idcurso/1/idvideoaula/776*/
	
	19.1 - ]
		no package eder.springProject.ProjetoSpring.security
			criar classJWTTokenAutenticacaoService.java
				atualizar
					/*Gera o nosso token e também valida o token enviado*/
				@Service
				@Component /*injeção de dependencia*/
				public class JWTTokenAutenticacaoService {

					/*Tempo de validade do Token 2 dias*/
					private static final long EXPIRATION_TIME = 172800000;
					
					/*Uma senha unica para compor a autenticacao e ajudar na segurança*/
					private static final String SECRET = "SECRET_KEY";
					
					/*Prefixo padrão de Token*/
					private static final String TOKEN_PREFIX = "Bearer";
					
					/*identificação do cabeçalho da resposta do token*/
					private static final String HEADER_STRING = "Authorization";
					
		19.2 - ]
			em classJWTTokenAutenticacaoService.java
				na sequencia dos "privates"
					atualizar
			
				/*Gerando token de autenticado e adiconando ao cabeçalho e resposta Http no navegador*/
				public void addAuthentication(HttpServletResponse response , String username) throws IOException {
					
					/*Montagem do Token*/
					String JWT = Jwts.builder() /*Chama o gerador de Token*/
									.setSubject(username) /*Adicona o usuario para gerar o token*/
									.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*Tempo de expiração*/
									.signWith(SignatureAlgorithm.HS512, SECRET).compact(); /*Compactação e algoritmos de geração de senha*/
					
					/*Junta token com o prefixo*/
					String token = TOKEN_PREFIX + " " + JWT; /*Bearer 87878we8we787w8e78w78e78w7e87w*/
					
					/*Adiciona no cabeçalho http*/
					response.addHeader(HEADER_STRING, token); /*Authorization: Bearer 87878we8we787w8e78w78e78w7e87w*/
					
					/*Escreve token como responsta no corpo http*/
					response.getWriter().write("{\"Authorization\": \""+token+"\"}");
					
				}
				
		19.3 - ]
			em classJWTTokenAutenticacaoService.java
				na sequencia do metodo gerar token
					atualizar
							
			/*Retorna o usuário validado com token ou caso não sejá valido retorna null, recebe resposta do navegador*/
			public Authentication getAuhentication(HttpServletRequest request) {
						
						/*Pega o token enviado no cabeçalho http, consulta no cabeçalho*/		
						String token = request.getHeader(HEADER_STRING);
						/*se for difernte de nulll entra*/
						if (token != null) {
							
							/*Faz a validação do token do usuário na requisição*/
							String user = Jwts.parser().setSigningKey(SECRET) /*Bearer 87878we8we787w8e78w78e78w7e87w*/
												/*pegar o token e remover o prefixo dele*/
												.parseClaimsJws(token.replace(TOKEN_PREFIX, "")) /*87878we8we787w8e78w78e78w7e87w*/
												/*descompactação e retorna*/
								.getBody().getSubject(); /*João Silva*/
											
				/*AQUI ANTES DESSE IF É NESCESSÁRIO CRIAR UMA OUTRA CLASSE
				POR DE DENTRO DESSE METODO NÃO ESTAVA DANDO CERTO INJETAR 
				OS ATRIBUTOS DA INTERFACE DE UserRepository*/
				
				19.3.1 - ]
					- criar no package eder.springProject.ProjetoSpring
						ApplicationContextLoad.java
						 atualizar	
							
						@Component
						public class ApplicationContextLoad implements ApplicationContextAware{
							
							@Autowired
							private static ApplicationContext applicationContext;

							@Override
							public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
								
								this.applicationContext = applicationContext;
								
								}
								
								public static ApplicationContext getApplicationContext() {
									return applicationContext;
								}

						}	

			19.4 - ]
			em classJWTTokenAutenticacaoService.java
			na sequencia do metodo gerar token
					atualizar			
				
			/*se usuario diferente de null entra*/
			if (user != null) {
					
				user usuario = ApplicationContextLoad.getApplicationContext()
									.getBean(UserRepository.class).findUserByLogin(user);
					
				if (usuario != null) {
					/*UsernamePasswordAuthenticationToken é uma classe propria do sprind para trabalhar com token */					
						return new UsernamePasswordAuthenticationToken(
								usuario.getLogin(), 
								usuario.getSenha(),
								usuario.getAuthorities());
						
				}
			}			
		}	
		return null; /*Não autorizado*/		
	}
}

		19.5 - ]
			JSON Web Token (JWT) - Criando a classe JwtLoginFilter
				no package eder.springProject.ProjetoSpring.security 
					criar JWTLoginFilter.java
						atualizar extends AbstractAuthenticationProcessingFilter
							
						/*Estabelece o nosso gerenciador de Token*/
						public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
								
				19.5.1 - ]
						- criar metodos
							attemptAuthentication
						/* Retorna o usuário ao processar a autenticação */
						@Override
						public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
								throws AuthenticationException, IOException, ServletException {

							/* Esta pegando o token para validar */
							user user = new ObjectMapper().readValue(request.getInputStream(), user.class);

							/*Retorna o usuario login, senha e acessos*/
							return getAuthenticationManager().
									authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
						}
				19.5.2 - ]
						- e o construtor 
							/* Configurando o gerenciador de autenticacao */
							protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
								/* Obriga a autenticar a URL */
								super(new AntPathRequestMatcher(url));

								/* Gerenciador de autenticacao */
								setAuthenticationManager(authenticationManager);
							}	
							
				19.5.3 - ]
						- metodo de sucesso Ctrl + Space = successfulAuthentication
							@Override
							protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
									Authentication authResult) throws IOException, ServletException {		
								new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
							}

20 - ]
	 JSON Web Token (JWT) - Criando a classe JwtApiAutenticacaoFilter
		no package	eder.springProject.ProjetoSpring.security
			criar JwtApiAutenticacaoFilter.java
					atualizar extends GenericFilterBean
					
					public class JwtApiAutenticacaoFilter extends GenericFilterBean {
						
						20.1 - criar metodo doFilter
						atualizar
							@Override
							public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
									throws IOException, ServletException {

								/* Estabelece a autenticação para a requisição */

								Authentication authentication = new JWTTokenAutenticacaoService()
										.getAuhentication((HttpServletRequest) request);

								/* Coloca o processo de autenticação no spring security */
								SecurityContextHolder.getContext().setAuthentication(authentication);

								/* Continua o processo */

								chain.doFilter(request, response);
							}

					}
						
						
			20.1 - ]
				atualizar WebConfigSecurity
					@Override
					protected void configure(HttpSecurity http) throws Exception {

						/* Ativando a proteção contra usuário que não estão validados por TOKEN */
						http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

								/*
								 * Ativando a permissão para acesso a página incial do sistema EX:
								 * sistema.com.br/index
								 */
								.disable().authorizeRequests().antMatchers("/").permitAll().antMatchers("/index").permitAll()

								/* URL de Logout - Redireciona após o user deslogar do sistema */
								.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")

								/* Maperia URL de Logout e insvalida o usuário */
								.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

								/* Filtra requisições de login para autenticação */
								.and().addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), 
										UsernamePasswordAuthenticationFilter.class)

								/* Filtra demais requisições paa verificar a presenção do TOKEN JWT no HEADER  HTTP */
								.addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class);

					}
						

21 - ]
	TESTE DE FUNCIONABILIDADE 
		/*https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/6XcxDBWwFsM/idcurso/1/idvideoaula/780*/
		
		Para fazer teste é necessário:
			- ter no banco de dados o login e senha para o exemplo:
				usaremos login: admin e senha 99999, na senha devida ao metodo configure do WebConfigSecurity
				esta usando o "BCryptPasswordEncoder" assim é preciso no banco esteja criptografado
				nesse caso senha 99999 = $2y$10$MWjqsnOFJHHKel7VLmoRk.iGr1J0jfFLQXTH2XeJJiW6/NblbgOHG
			
			- atraves da ferramenta postman com o metodo POST,
				- informar a url localhost:8080/springProjectRestAPI/login
				- editar o json   { "login": "admin", "senha": "99999" }
				- Send =>
				
			No Postman deve retornar o status 200 Ok + a Authorization
				{"Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NDM0NDcxOH0.LqSgU2Sl7O9VYCjZ9jlFu-KMCQUSSXTPyx4JIWZswBcMcnpElMlSJrw7EfnNsu4GpsFZII9VDlbtWMhem2pkEQ"}
				
			Result no console da IDE:
				Hibernate: select user0_.id as id1_0_, user0_.login as login2_0_, user0_.nome as nome3_0_, user0_.senha as senha4_0_ from user user0_ where user0_.login=?
				Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, userrole1_.id as id1_2_1_, userrole1_.descricaorole as descrica2_2_1_, userrole1_.status as status3_2_1_ from user_on_roles roles0_ inner join user_role userrole1_ on roles0_.role_id=userrole1_.id where roles0_.user_id=?
				
			
		21.1 - ]
			JSON Web Token (JWT) - Consultando e Deletando
			/*https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/hrFRpQtgNC4/idcurso/1/idvideoaula/782*/
			
			Para fazer a consulta dos dados da API utilizando os metodos criados no IndexController, antes a gente 
			apenas passa a url porex: localhost:8080/springProjectRestAPI/usuario/ nesse mapeamento / ele iri localizar um
			metodo Get e tras todos os registros.
			Agora como o sistema obriga a passarmo o token gerado anteriormente:
				- no Postman informe a url localhost:8080/springProjectRestAPI/usuario/
				- e na ABA "Authentication" > Type "Bearer Token" > informe o token gerado 
					eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NDM0NDcxOH0.LqSgU2Sl7O9VYCjZ9jlFu-KMCQUSSXTPyx4JIWZswBcMcnpElMlSJrw7EfnNsu4GpsFZII9VDlbtWMhem2pkEQ				
				
				- Send
				
				- O Postman deve retornar o resultado:
					 {
						"id": 1,
						"login": "admin",
						"senha": "$2y$10$MWjqsnOFJHHKel7VLmoRk.iGr1J0jfFLQXTH2XeJJiW6/NblbgOHG",
						"nome": "Administrador Sistema",
						"telephones": [
							{
								"id": 59,
								"numero": "14 9995623"
							},
							{
								"id": 60,
								"numero": "19 2342523"
							},
							{
								"id": 61,
								"numero": "11 88874541"
							}
						],
												
							
		21.2 - ]
			JSON Web Token (JWT) - Cadastrando e editando
				atulizar o metodos gravar e atualizar do indexController, para que ao ser informada a senha possa 
				ser cadastrada de forma criptografado.
				
					@PostMapping(value = "/caduser", produces = "application/json")
					public ResponseEntity<user> caduser(@RequestBody user users) {
						
						//varer  os registros de telefone
						for (int pos = 0; pos < users.getTelephones().size(); pos ++) {
							users.getTelephones().get(pos).setUser(users);
						}

						/*criptografar senha antes de gravar*/
						String keyBCrypt = new BCryptPasswordEncoder().encode(users.getSenha());
						users.setSenha(keyBCrypt);
						user usersave = userRepository.save(users);

						return new ResponseEntity<user>(usersave, HttpStatus.OK);

					}

					// PUT Mappign que grava um novo regsitro na tabela
					// http://localhost:8080/caduser/
					@PutMapping(value = "/caduser", produces = "application/json")
					public ResponseEntity<user> update (@RequestBody user users) {

						//varer  os registros de telefone
						for (int pos = 0; pos < users.getTelephones().size(); pos ++) {
							users.getTelephones().get(pos).setUser(users);
						}
						
						/*incluir outra rotinas antes de atualizar*/
						user usertmp = userRepository.findUserByLogin(users.getLogin());
		
						/* verificar se a senha que estiver vindo foi atulizada */
						if (!usertmp.getSenha().equals(users.getSenha())) {

							/* criptografar senha antes de atualizar */
							String keyBCrypt = new BCryptPasswordEncoder().encode(users.getSenha());
							users.setSenha(keyBCrypt);

						}	
						
						user usersave = userRepository.save(users);

						return new ResponseEntity<user>(usersave, HttpStatus.OK);

					}
					
			- No Postman informe :
				- Metodo POST
				- na url localhost:8080/springProjectRestAPI/usuario/caduser/
				- e na ABA "Authentication" > Type "Bearer Token" > informe o token gerado 
					eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NDM0NDcxOH0.LqSgU2Sl7O9VYCjZ9jlFu-KMCQUSSXTPyx4JIWZswBcMcnpElMlSJrw7EfnNsu4GpsFZII9VDlbtWMhem2pkEQ	
	
				- os dados do json		
				
				    {
						"id": "",
						"login": "Moacir",
						"senha": "77845",
						"nome": "Moacir Sistema",
						"bcryptpasswordencoder": "senha 77845",
						"telephones": [
							{
								"id": "",
								"numero": "14 661215831"
							},
							{
								"id": "",
								"numero": "19 11512121"
							},
							{
								"id": "",
								"numero": "11 1154211"
							}
						]        
					}
		
				- para o PUT a mesma coisa porem informando o id gerado.
				
22 - ]
	 Versionamento de API
		- atraves de proprio indexController podemos acrescentar um cabeçalho headers = "x-api-key=1"
			@GetMapping(value = "/{id}", produces = "application/json", headers = "x-api-key=1" )
				- nesse caso a url no POSTMan permanece com antes 
					localhost:8080/springProjectRestAPI/usuario/1
					
				- porem na Headers 
					temos que informar na coluna Key = x-api-key com o value 1 ou 2
					de acordo com o Get que queremos obter da versão da API-REST*************
				
		- esse Versinamento de API é utilizado para caso que o mesma URL possa ser acessa porem com headers diferentes
			temos outra instrução de metodo, ou seja no get acima só busca por um id especifico porem ao informar o headers
			diferente a API pode trazer o id em questão e muitas mais coisas.
			
			
23 - ]
	Pool de conexão com Hikari
		- configuração necessária afim de conseguir mais agilidade do sistema
			no pom.xml	atualizar	
				<dependency>
					<groupId>com.zaxxer</groupId>
					<artifactId>HikariCP</artifactId>
					<version>4.0.3</version>
				</dependency>
		
		- no application.properties
			#############################################################
			##########Pool de conexão com Hikari#########################
			#############################################################

			#número máximo de milissegundos que um cliente aguardará por uma conexão
			spring.datasource.hikari.connection-timeout = 20000 

			#número mínimo de conexões inativas mantidas pelo HikariCP em um conjunto de conexões
			spring.datasource.hikari.minimum-idle= 10

			#Máximo do pool de conexão
			spring.datasource.hikari.maximum-pool-size= 40

			#Tempo ociosos para conexão
			spring.datasource.hikari.idle-timeout=10000 

			#salvando dados no banco automaticamente
			spring.datasource.hikari.auto-commit =true
							
						
24 - ]
	Implementando o cache para performance
		- forma de deixar o sistema preparado para trabalhar com cache afim de agilizar o processo em cache	
			- no pom.xml atualizar depemdencia
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-cache</artifactId>
				</dependency>
				
			- em ProjetoSpringApplication.java	
				anotar com 
					@EnableCaching /*anotação da depemdencia de cache de memoria*/
					
			
			- e em IndexController.java
					anotar com 
						@Cacheable /*anota que agiliza o processo lentos em cache*/
		
		/*NÃO DEU MUITO CERTO O PROGRAMA GEROU ERROS */
		
		***************************
		APPLICATION FAILED TO START
		***************************

		Description:

		Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

		Reason: Failed to determine a suitable driver class


		Action:

		Consider the following:
			If you want an embedded database (H2, HSQL or Derby), please put it on the classpath.
			If you have database settings to be loaded from a particular profile you may need to activate it (no profiles are currently active).
		
			
			
25 - ]
	Conhecendo diferença entre CacheEvict e o CachePUT
		na classe de controller indexController
			- no metodo GetMapping
				@GetMapping(value = "/", produces = "application/json")
				@CacheEvict(value="cacheusuarios", allEntries = true) /*essa anotação limpas cache poucos usados*/
				@CachePut("cacheusuarios") /*essa atuliza novos informações, update cache*/
				
			
26 - ]
	JSON Web Token (JWT) - Autenticação com TOKEN em banco de dados
		em user.java	
			atualizar	
				private String token_user;
				
				criar getters e setters
				
			public String getToken_user() {
				return token_user;
			}

			public void setToken_user(String token_user) {
				this.token_user = token_user;
			}
	
		26.1 - ]
			em JWTTokenAutenticacaoService.java	
				atualizar
					public Authentication getAuhentication(HttpServletRequest request) {
		
					/*Pega o token enviado no cabeçalho http, consulta no cabeçalho*/		
					String token = request.getHeader(HEADER_STRING);
					/*se for difernte de nulll entra*/
					if (token != null) {
						
						/*pega o token gerado e remove todo prefixo dexando limpo*/
						String tokenclear = token.replace(TOKEN_PREFIX, "").trim();
						
						/*Faz a validação do token do usuário na requisição*/
						String user = Jwts.parser().setSigningKey(SECRET) /*Bearer 87878we8we787w8e78w78e78w7e87w*/
											/*pegar o token e remover o prefixo dele*/
											.parseClaimsJws(tokenclear) /*87878we8we787w8e78w78e78w7e87w*/
											/*descompactação e retorna*/
											.getBody().getSubject(); /*João Silva*/
						/*se usuario diferente de null entra*/
						if (user != null) {
							
							/*todo os controles carregados na memoria estão nesse ApplicationContextload*/
							user usuario = ApplicationContextLoad.getApplicationContext()
									.getBean(UserRepository.class).findUserByLogin(user);
							
							if (usuario != null) {
									
								/*vai carregar o resultado se o token enviado por request é mesmo cad no banco token_user */
								if (tokenclear.equalsIgnoreCase(usuario.getToken_user())) {
												
									/*UsernamePasswordAuthenticationToken é uma classe propria do sprind para trabalhar com token */
									return new UsernamePasswordAuthenticationToken(
											usuario.getLogin(), 
											usuario.getSenha(),
											usuario.getAuthorities());
								}
							}
						}			
					}
					
27 - ]
	 Liberação de CORS e Allow em Origin, Headers , Methods e Request
		- serve para corrigir um erro que acontece na implementação das telas com framework ex "Angular"
		atualizar	
			- em JWTTokenAutenticacaoService.java passar novo paremtros "response"
				/*Retorna o usuário validado com token ou caso não sejá valido retorna null, recebe resposta do navegador*/
				public Authentication getAuhentication(HttpServletRequest request, HttpServletResponse response) {
					
			- em JwtApiAutenticacaoFilter.java  passar novo paremtros "response"
				atualizar	
					/* Estabelece a autenticação para a requisição */
					Authentication authentication = new JWTTokenAutenticacaoService()
						.getAuhentication((HttpServletRequest) request, (HttpServletResponse) response);
						
			- em JWTTokenAutenticacaoService.java criar função e metodo antes do return
				controlalllow(response);
				return null; /* Não autorizado */
				}

				private void controlalllow(HttpServletResponse response) {
					if (response.getHeader("Access-Control-Allow-Origin") == null) {
						response.addHeader("Access-Control-Allow-Origin", "*");
					}
					if (response.getHeader("Access-Control-Allow-Headers") == null) {
						response.addHeader("Access-Control-Allow-Headers", "*");
					}
					if (response.getHeader("Access-Control-Request-Headers") == null) {
						response.addHeader("Access-Control-Request-Headers", "*");
					}
					if (response.getHeader("Access-Control-Allow-Methods") == null) {
						response.addHeader("Access-Control-Allow-Methods", "*");
					}

				}
				
			- atualizar a chamada da função em 
				public void addAuthentication(HttpServletResponse response, String username) throws IOException {

					/* Montagem do Token */
					String JWT = Jwts.builder() /* Chama o gerador de Token */
							.setSubject(username) /* Adicona o usuario para gerar o token */
							.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* Tempo de expiração */
							.signWith(SignatureAlgorithm.HS512, SECRET)
							.compact(); /* Compactação e algoritmos de geração de senha */

					/* Junta token com o prefixo */
					String token = TOKEN_PREFIX + " " + JWT; /* Bearer 87878we8we787w8e78w78e78w7e87w */

					/* Adiciona no cabeçalho http */
					response.addHeader(HEADER_STRING, token); /* Authorization: Bearer 87878we8we787w8e78w78e78w7e87w */
									
					/*Liberando resposta para portas diferentes que usam a API ou caso clientes web*/
					controlalllow(response);
					
					/* Escreve token como responsta no corpo http */
					response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

				}
28 - ]
	/*Centralizar as msg de erros para o usuario que esteja consumindo nossa API */
		- Controle de erros da API com @ControllerAdvice, @RestControllerAdvice e @ExceptionHandler
			 no package eder.springProject.ProjetoSpring criar ObjectErrors.java
				atualizar	
						package eder.springProject.ProjetoSpring;

						public class ObjectErrors {

							private String error;
							private String code;

							public String getError() {
								return error;
							}

							public void setError(String error) {
								this.error = error;
							}

							public String getCode() {
								return code;
							}

							public void setCode(String code) {
								this.code = code;
							}

						}
						
			28.1 - ]
				e criar o class ControllException,java
					atualizar
						
						@RestControllerAdvice
						@ControllerAdvice
						public class ControllException extends ResponseEntityExceptionHandler {
							
							/*qualquer erro que gerar execeção Exception, RuntimeException ou Throwable*/
							/*sera interceptada e criar uma lista de msg de errors*/
							@ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
							@Override
							protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
									HttpStatus status, WebRequest request) {
								
								String msg = "";
								
								if (ex instanceof MethodArgumentNotValidException) {
									List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
									for (ObjectError objectError : list) {
										msg += objectError.getDefaultMessage() + "\n";
									}
								}else {
									msg = ex.getMessage();
								}
								
								ObjectErrors objetoErro = new ObjectErrors();
								objetoErro.setError(msg);
								objetoErro.setCode(status.value() + " ==> " + status.getReasonPhrase());
								
								return new ResponseEntity<>(objetoErro, headers, status);
							}
							
						}

29 - ]
		Controle de erros da API - Parte 2
			ATUALIZAR
				 ControllException,java IMPLEMTAR METODO ABAIXO
					/*METODO QUE INTECEPTA OS PRICIPAIUS ERROS QUE POSSAM ACONTECE ENTRE API E BANCO DE DADOS*/
					@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class,
							MySQLTimeoutException.class, SQLException.class })
					protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {

						String msg = "";

						if (ex instanceof DataIntegrityViolationException) {
							msg = ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
						} else if (ex instanceof ConstraintViolationException) {
							msg = ((ConstraintViolationException) ex).getCause().getCause().getMessage();
						} else if (ex instanceof MySQLTimeoutException) {
							msg = ((MySQLTimeoutException) ex).getCause().getCause().getMessage();
						} else if (ex instanceof SQLException) {
							msg = ((SQLException) ex).getCause().getCause().getMessage();
						} else {
							/* msg padrão */
							msg = ex.getMessage();
						}

						/* construção do Objeto Error */
						ObjectErrors objetoErro = new ObjectErrors();
						objetoErro.setError(msg);
						objetoErro.setCode(HttpStatus.INTERNAL_SERVER_ERROR + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR);

						return new ResponseEntity<>(objetoErro, HttpStatus.INTERNAL_SERVER_ERROR);

					}
					
30 - ]
	 Tratamento do TOKEN Expirado
		 - em JWTTokenAutenticacaoService.java trata o metodo getAuhentication para que em caso de token EXPIRATION_TIME,
			sera necessário um novo login para geração.
				atualizar
			public Authentication getAuhentication(HttpServletRequest request, HttpServletResponse response) {

				/* Pega o token enviado no cabeçalho http, consulta no cabeçalho */
				String token = request.getHeader(HEADER_STRING);
					
				try {
					/* se for difernte de nulll entra */
					if (token != null) {

						/* pega o token gerado e remove todo prefixo dexando limpo */
						String tokenclear = token.replace(TOKEN_PREFIX, "").trim();

						/* Faz a validação do token do usuário na requisição */
						String user = Jwts.parser().setSigningKey(SECRET) /* Bearer 87878we8we787w8e78w78e78w7e87w */
								/* pegar o token e remover o prefixo dele */
								.parseClaimsJws(tokenclear) /* 87878we8we787w8e78w78e78w7e87w */
								/* descompactação e retorna */
								.getBody().getSubject(); /* João Silva */
						/* se usuario diferente de null entra */
						if (user != null) {

							/* todo os controles carregados na memoria estão nesse ApplicationContextload */
							user usuario = ApplicationContextLoad.getApplicationContext().getBean(UserRepository.class)
									.findUserByLogin(user);

							if (usuario != null) {

								/*
								 * vai carregar o resultado se o token enviado por request é mesmo cad no banco
								 * token_user
								 */
								if (tokenclear.equalsIgnoreCase(usuario.getToken_user())) {

									/*
									 * UsernamePasswordAuthenticationToken é uma classe propria do sprind para
									 * trabalhar com token
									 */
									return new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getSenha(),
											usuario.getAuthorities());
									}
								}
						}
					}/*Fim da condição*/
				}catch (io.jsonwebtoken.ExpiredJwtException e) {
						try {
							response.getOutputStream().println("Expired token, login again !");
						} catch (IOException e1) {}
					}
						controlalllow(response);
						return null; /* Não autorizado */
			}
					
31 - ]
	Padrão DTO (Data Transfer Object) dentro de uma API ou projeto
			- usar DTO quando vai trafergar dados e escontece atributos da classe principal de persitencia
			por exemplo ocupar a senha o usuario em sem precisa mexer na classe usuario.
				no package eder.springProject.ProjetoSpring.model criar userDTO.java
					atualizar	
						/*classe DTO que recebe somente os atributos que podem ser exibidos no retorno das requisições da classe de controle*/
						public class userDTO implements Serializable {

							private static final long serialVersionUID = 1L;

							private String userLogin;
							private String userNome;
							private String userCPF;

							/*
							 * construtor responsavel por receber a classe de pesistencia e limitar quais
							 * atributos podem ser vializados ao usuario
							 */
							public userDTO(user user) {

								this.userLogin = user.getLogin();
								this.userNome = user.getNome();
								this.userCPF = user.getCPF();
							}

							/*getters && setters*/
						}

				31.1 - ]
					em IndexController.java
						atualizar um dos metodos, porem esse mesma modificação pode ser realizada em qualquer outro metodo
						
							@GetMapping(value = "/{id}", produces = "application/json",  headers = "x-api-key=1")
							public ResponseEntity<userDTO> userparams(@PathVariable(value = "id") Long id) {

								/* injentando o UserRepository é possivel usar qualquer metodo pronto que a
								  interface disponibiliza 
								Apesar de receber a classe userDTO no banco de dados os dados viram da classe user*/
								Optional<user> usuario = userRepository.findById(id);

								/*alterado para a classe userDTO que contem os atributos que vão retornar na tela*/
								return new ResponseEntity<userDTO>(new userDTO(usuario.get()), HttpStatus.OK);

							}
							
					Result:
						{
							"userLogin": "admin",
							"userNome": "Administrador do Sistema up 0214",
							"userCPF": null
						}
						
32 - ]
	Atualizando TOKEN em novo login
		em UserRepository
			- criar novo metodo updateTokenUser 
				
					/*faz update do token na tebela user*/
					@Modifying
					@Transactional
					@Query(nativeQuery = true, value = "update user set token_user = ?1 where login = ?2")	
					user updateTokenUser(String login, String token);

			- atulizar JWTTokenAutenticacaoService o metodo addAuthentication
			public void addAuthentication(HttpServletResponse response, String username) throws IOException {

				/* Montagem do Token */
				String JWT = Jwts.builder() /* Chama o gerador de Token */
						.setSubject(username) /* Adicona o usuario para gerar o token */
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /* Tempo de expiração */
						.signWith(SignatureAlgorithm.HS512, SECRET)
						.compact(); /* Compactação e algoritmos de geração de senha */

				/* Junta token com o prefixo */
				String token = TOKEN_PREFIX + " " + JWT; /* Bearer 87878we8we787w8e78w78e78w7e87w */

				/* Adiciona no cabeçalho http */
				response.addHeader(HEADER_STRING, token); /* Authorization: Bearer 87878we8we787w8e78w78e78w7e87w */

				/* todo os controles carregados na memoria estão nesse ApplicationContextload */
				ApplicationContextLoad.getApplicationContext().getBean(UserRepository.class)
						.updateTokenUser(JWT, username);
				
				/*Liberando resposta para portas diferentes que usam a API ou caso clientes web*/
				controlalllow(response);
				
				/* Escreve token como responsta no corpo http */
				response.getWriter().write("{\"Authorization\": \"" + token + "\"}");

			}
		
		32.1 - ]	
		/*TESTE UNITARIO*/
		
			- Atraves do Postman informe com Metodo POST => a url localhost:8080/springProjectRestAPI/login 
				Body > Raw > Json
					{
						"login": "admin",
						"senha": "1234"
					}
					
					/*os dados de login e senha necessariamente precisam estar presente no banco de dados 
					previamente*/
					
				=> Send
					
					resultado:
					Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NDc4Mzg3OH0.BcsCy1wQSV4CZA2gc4woAKkHmyQ862twnCMiH8MDao-dCH7FL9y1f8o0cY9NR5UWq45EZHWvEX_JOdCKzjFdtw
					
			- gerado o token e já atualiza o token na tabela de user o campo token_user 
				
			- copiar o token gerado e informar no: 
				- Headers > Authorization para conseguir obter resultado "ok" nos metodos da classe de controller.

					ex: localhost:8080/springProjectRestAPI/usuario/
									+
								Authorization
									=
					
					Resultado:
					[
						{
							"id": 1,
							"login": "admin",
							"senha": "$2a$10$N26qZn36TnwBO8mcQnCWNujNsmy949mfOE0fsHN5m4f82CFVAH/we",
							"nome": "Administrador do Sistema up 0214",
							"bcryptpasswordencoder": "senha 1234",
							"token_user": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY2NDc5Mzg1MH0.ULG8d_8pLLI7C3HCbgpaOuZLdKwVcE1O6cNVLX0ZE3IuJ3OKZt292jNGPR6ZEVdrX6X_LuoetjxHLRGJEz94PQ",
							"telephones": [
								{
									"id": 290,
									"numero": "14 99926232"
								},
								{
									"id": 291,
									"numero": "19 23421234123"
								},
								{
									"id": 292,
									"numero": "11 523462345"
								}
							],
							"username": "admin",
							"authorities": [],
							"cpf": "2222312321"
						},
						{
							"id": 297,
							"login": "farinha1",
							"senha": "$2a$10$8Kas3G1iyuMFCAF9XFiZ6.bLtZ4ELS5UFM3w05SCc.sCJsFMg62Fy",
							"nome": "Farinha3 do Sistema",
							"bcryptpasswordencoder": "senha 151551",
							"token_user": null,
							"telephones": [
								{
									"id": 298,
									"numero": "14 661215831"
								},
								{
									"id": 299,
									"numero": "19 11512121"
								},
								{
									"id": 300,
									"numero": "11 1154211"
								}
							],
							"username": "farinha1",
							"authorities": [],
							"cpf": "451565131"
						}
					]
			
			- o Metodo Post de incluir novos registros não chama o metodo de atualização do token_user na tabela do usuario
			logo ao cadatra um novo registro o campo token_user vai ficar null:
				{
					"id": 301,
					"login": "farinha2",
					"senha": "$2a$10$Q895sDuouixtaUkjBJvMBexkneRejfGat8e/8/7pJq7k9EIyvgK0y",
					"nome": "Farinha2 do Sistema",
					"bcryptpasswordencoder": "senha 6541",
					"token_user": null,
					"telephones": [
						{
							"id": 302,
							"numero": "14 661215831"
						},
						{
							"id": 303,
							"numero": "19 11512121"
						},
						{
							"id": 304,
							"numero": "11 1154211"
						}
					],
					"username": "farinha2",
					"authorities": null,
					"cpf": "451565131"
				}
				
			- para atualizar o token_user basta informar a url localhost:8080/springProjectRestAPI/login 
				Body > Raw > Json
					{
						"login": "farinha2",
						"senha": "6541"
					}
					
				Resultado:
					
				{"Authorization": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXJpbmhhMiIsImV4cCI6MTY2NDc5NjM2OX0.ABeRnymMHbFFjNksPZklgQgV4NIvY2k8u2BUZQdxChEqDMBNA-z0ZwounNCEtCl9hqcLDrMEI0n2PWKHkjjQpg"}
				
			- esse novo token gerado tem a mesma validade do primeiro gerado (logico com um certa diferenca de tempo de expiração), entretando ele pode ser usando na validação de do headers > Authorization.
			
			- NOta:
				o metodo getAuhentication em JWTTokenAutenticacaoService.java
					faz a verificação em
						/* Pega o token enviado no cabeçalho http, consulta no cabeçalho */
						String token = request.getHeader(HEADER_STRING);
						
				- ele recebe na vaiavel request atraves do um etdo do sprind HttpServletRequest caso o token voltar null
				o metodo é abortado.
				
				