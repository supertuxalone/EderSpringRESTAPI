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
						@GeneratedValue(strategy = GenerationType.AUTO)

						private Long id;
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
		