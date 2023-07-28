package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "mail", unique = true)
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(String mail, String password, String firstName, String lastName, Integer age) {
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String toString() {
        return String.format(" User = [mail =%s, password = %s, firstName = %s, lastName = %s, age = $d]", mail, password, firstName, lastName, age);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        User user = (User) obj;
        return this.age == user.age
                && (this.mail == user.mail || (this.mail != null && this.mail.equals(user.mail)))
                && (this.password == user.password || (this.password != null && this.password.equals(user.password)))
                && (this.firstName == user.firstName || (this.firstName != null && this.firstName.equals(user.firstName)))
                && (this.lastName == user.lastName || (this.lastName != null && this.lastName.equals(user.lastName)));
    }


    @Override
    public int hashCode() {
        return 31 + (age == 0 ? 0 : age.hashCode()) + (mail == null ? 0 : mail.hashCode()) + (password == null ? 0 : password.hashCode() + (firstName == null ? 0 : firstName.hashCode()) + (lastName == null ? 0 : lastName.hashCode()));
    }
}

