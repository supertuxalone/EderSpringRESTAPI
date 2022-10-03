package eder.springProject.ProjetoSpring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eder.springProject.ProjetoSpring.model.user;
import eder.springProject.ProjetoSpring.model.userDTO;
import eder.springProject.ProjetoSpring.repository.UserRepository;

@RestController
@RequestMapping(value = "/usuario")
public class IndexController {

	@Autowired /* de CDI seria @Inject */
	private UserRepository userRepository;

	// dependendo do url que foi informada pelo usuario é metodo que vai ser chamado
	// get Mapping que retona um registro ao se passar o id especifico
	// http://localhost:8080/usuario/15
	@GetMapping(value = "/{id}", produces = "application/json", headers = "x-api-key=1")
	public ResponseEntity<userDTO> userparams(@PathVariable(value = "id") Long id) {

		/*
		 * injentando o UserRepository é possivel usar qualquer metodo pronto que a
		 * interface disponibiliza Apesar de receber a classe userDTO no banco de dados
		 * os dados viram da classe user
		 */
		Optional<user> usuario = userRepository.findById(id);

		/*
		 * alterado para a classe userDTO que contem os atributos que vão retornar na
		 * tela
		 */
		return new ResponseEntity<userDTO>(new userDTO(usuario.get()), HttpStatus.OK);

	}

	// get Mappign que retona todos os registro http://localhost:8080/usuario/
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<user>> userall() {

		/*
		 * injentando o UserRepository é possivel usar qualquer metodo pronto que a
		 * interface disponibiliza
		 */
		List<user> list = (List<user>) userRepository.findAll();

		return new ResponseEntity<List<user>>(list, HttpStatus.OK);

	}

	// POST Mappign que grava um novo regsitro na tabela
	// http://localhost:8080/caduser/
	@PostMapping(value = "/caduser", produces = "application/json")
	public ResponseEntity<user> caduser(@RequestBody user users) throws Exception {

		// varer os registros de telefone
		for (int pos = 0; pos < users.getTelephones().size(); pos++) {
			users.getTelephones().get(pos).setUser(users);
		}

		/* Consumindo APi de CEP a partir desse ponto */
		/* dinamicamente informar a url do api */
		/*
		 * URL url = new URL ("https://viacep.com.br/ws/"+users.getCep()+"/json/");
		 * acrindo a conexão URLConnection connection = url.openConnection(); pegando os
		 * dados do cep que passei InputStream is = connection.getInputStream();
		 * passando o buffer BufferedReader br = new BufferedReader(new
		 * InputStreamReader(is, "UTF-8"));
		 * 
		 * Lendo a variavel String cep = ""; StringBuilder jsonCep = new
		 * StringBuilder();
		 * 
		 * equanto tiver linhas atribuir a uma variavel while((cep = br.readLine())
		 * !=null) { e junta com jsoncep jsonCep.append(cep); }
		 * System.out.println(jsonCep.toString());
		 * 
		 * passa a conversão realizada até aqui e converter em json e gravar no banco de
		 * dados importar dependencia com.google.code.gson converte para uma objeto user
		 * userAux = new Gson().fromJson(jsonCep.toString(), user.class);
		 * 
		 * users.setSenha(userAux.getCep());
		 * users.setLogradouro(userAux.getLogradouro());
		 * users.setComplemento(userAux.getComplemento());
		 * users.setBairro(userAux.getBairro());
		 * users.setLocalidade(userAux.getLocalidade()); users.setUf(userAux.getUf());
		 */

		/* criptografar senha antes de gravar */
		String keyBCrypt = new BCryptPasswordEncoder().encode(users.getSenha());
		users.setSenha(keyBCrypt);
		user usersave = userRepository.save(users);

		return new ResponseEntity<user>(usersave, HttpStatus.OK);

	}

	// PUT Mappign que grava um novo regsitro na tabela
	// http://localhost:8080/caduser/
	@PutMapping(value = "/caduser", produces = "application/json")
	public ResponseEntity<user> update(@RequestBody user users) {

		// varer os registros de telefone
		for (int pos = 0; pos < users.getTelephones().size(); pos++) {
			users.getTelephones().get(pos).setUser(users);
		}

		/* incluir outra rotinas antes de atualizar */
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

	// Delete deltanando uma resgitro passando somente o id http://localhost:8080/16
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public String deleuser(@PathVariable("id") Long id) {

		userRepository.deleteById(id);

		return "ok";

	}

}
