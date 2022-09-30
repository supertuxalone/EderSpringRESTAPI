package eder.springProject.ProjetoSpring.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import eder.springProject.ProjetoSpring.ApplicationContextLoad;

import eder.springProject.ProjetoSpring.model.user;
import eder.springProject.ProjetoSpring.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
	
	/*Retorna o usuário validado com token ou caso não sejá valido retorna null, recebe resposta do navegador*/
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
		return null; /*Não autorizado*/		
	}	
}
