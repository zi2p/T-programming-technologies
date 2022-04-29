package com.kotiki.core.entities;
import com.kotiki.core.models.Color;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "cats")
public class Cat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    private String breed;
    private Color color;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany
    @JoinTable(
            name = "cat_friends",
            joinColumns = { @JoinColumn(name = "left_id") },
            inverseJoinColumns = { @JoinColumn(name = "right_id") }
    )
    private List<Cat> friends;

    public Cat(String name, LocalDateTime birthDate, String breed, Color color, Owner owner) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        this.friends = new ArrayList<>();
    }

    public Cat(String name, LocalDateTime birthDate, String breed, Color color) {
        this(name, birthDate, breed, color, null);
    }

    protected Cat() { }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LocalDateTime getBirthDate() { return birthDate; }

    public String getBreed() { return breed; }

    public Color getColor() { return color; }

    public void setColor(Color color) { this.color = color; }

    public Owner getOwner() { return owner; }

    public void setOwner(Owner owner) { this.owner = owner; }

    public List<Cat> getFriends() { return Collections.unmodifiableList(friends); }

    public void addFriend(Cat... cats) { Collections.addAll(friends, cats); }

    public void removeFriend(Cat... cats) { friends.removeAll(Arrays.asList(cats)); }
}
