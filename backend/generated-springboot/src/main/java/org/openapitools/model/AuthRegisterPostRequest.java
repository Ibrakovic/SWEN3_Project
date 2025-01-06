package org.openapitools.model;

import java.util.Objects;

import javax.annotation.processing.Generated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AuthRegisterPostRequest
 */

@JsonTypeName("_auth_register_post_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-05T12:12:53.257010100+02:00[Europe/Vienna]", comments = "Generator version: 7.8.0")
public class AuthRegisterPostRequest {

	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is mandatory")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	public AuthRegisterPostRequest() {
		super();
	}

	/**
	 * Constructor with only required parameters
	 */
	public AuthRegisterPostRequest(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public AuthRegisterPostRequest username(String username) {
		this.username = username;
		return this;
	}

	/**
	 * Get username
	 * 
	 * @return username
	 */
	@NotNull
	@Schema(name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AuthRegisterPostRequest email(String email) {
		this.email = email;
		return this;
	}

	/**
	 * Get email
	 * 
	 * @return email
	 */
	@NotNull
	@Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AuthRegisterPostRequest password(String password) {
		this.password = password;
		return this;
	}

	/**
	 * Get password
	 * 
	 * @return password
	 */
	@NotNull
	@Schema(name = "password", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AuthRegisterPostRequest authRegisterPostRequest = (AuthRegisterPostRequest) o;
		return Objects.equals(this.username, authRegisterPostRequest.username)
				&& Objects.equals(this.email, authRegisterPostRequest.email)
				&& Objects.equals(this.password, authRegisterPostRequest.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, email, password);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AuthRegisterPostRequest {\n");
		sb.append("    username: ").append(toIndentedString(username)).append("\n");
		sb.append("    email: ").append(toIndentedString(email)).append("\n");
		sb.append("    password: ").append(toIndentedString(password)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
