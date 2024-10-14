package org.openapitools.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 30, message = "Username muss zwischen 2 und 30 Zeichen lang sein.")
    private String username;

    @NotNull
    @Email(message = "Bitte geben Sie eine gültige E-Mail-Adresse ein.")
    private String email;

    @NotNull
    @Size(min = 8, message = "Das Passwort muss mindestens 8 Zeichen lang sein.")
    private String password;

    // Standardkonstruktor
    public User() {}

    // Konstruktor mit Feldern
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // equals Methode für Objektsvergleich
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    // hashCode Methode
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password);
    }

    // toString Methode zur Darstellung des User-Objekts als String
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
