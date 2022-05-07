package com.kotiki.infrastructure.entities;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<User> users;

    public Role() { }

    public Role(Long id) { this.id = id; }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() { return name; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public Set<User> getUsers() { return users; }

    public void setUsers(Set<User> users) { this.users = users; }
}
