package com.kotiki.microservices.external.infrastructure.entities;
import com.kotiki.core.entities.Owner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=6, message = "Не меньше 6 знаков")
    private String username;

    @Size(min=6, message = "Не меньше 6 знаков")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public User() { }

    public User(String username, String password, Set<Role> roles, Owner owner) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.owner = owner;
    }

    public Long getId() { return id; }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return getRoles(); }

    @Override
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public Set<Role> getRoles() { return roles; }

    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public void addRoles(Role... roles) { Collections.addAll(this.roles, roles); }

    public void setOwner(Owner owner) { this.owner = owner; }

    public Owner getOwner() { return owner; }
}
