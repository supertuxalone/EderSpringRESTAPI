package eder.springProject.ProjetoSpring.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ConstraintMode;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class user implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	private String login;
	private String senha;
	private String nome;
	private String CPF;
	private String bcryptpasswordencoder;
	private String token_user;

	/* anotação de um usuario pode possuir varios telefones */
	@OneToMany(mappedBy = "User", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<userTelephone> telephones = new ArrayList<userTelephone>();

	/* sempre que carregar o usuario tbm carregar os papeis "roles" dele */
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_on_roles", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id",
			"role_id" }, name = "unique_role_user"), joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", table = "user", unique = false, foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)),

			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "user_role", unique = false, updatable = false, foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))

	private List<userRole> roles;

	public List<userTelephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(List<userTelephone> telephones) {
		this.telephones = telephones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		user other = (user) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getBcryptpasswordencoder() {
		return bcryptpasswordencoder;
	}

	public void setBcryptpasswordencoder(String bcryptpasswordencoder) {
		this.bcryptpasswordencoder = bcryptpasswordencoder;
	}

	public String getToken_user() {
		return token_user;
	}

	public void setToken_user(String token_user) {
		this.token_user = token_user;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}
}
