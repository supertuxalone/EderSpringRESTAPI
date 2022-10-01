package eder.springProject.ProjetoSpring.model;

import java.io.Serializable;

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

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserNome() {
		return userNome;
	}

	public void setUserNome(String userNome) {
		this.userNome = userNome;
	}

	public String getUserCPF() {
		return userCPF;
	}

	public void setUserCPF(String userCPF) {
		this.userCPF = userCPF;
	}

}
