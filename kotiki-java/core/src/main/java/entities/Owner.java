package entities;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @OneToMany(mappedBy = "owner")
    private List<Cat> cats;

    public Owner(String name, LocalDateTime birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cats = new ArrayList<>();
    }

    protected Owner() { }

    public Long getId() { return id; }

    public String getName() { return name; }

    public LocalDateTime getBirthDate() { return birthDate; }

    public List<Cat> getCats() { return Collections.unmodifiableList(cats); }

    public void addCat(Cat... cats) { Collections.addAll(this.cats, cats); }

    public void removeCat(Cat... cats) { this.cats.removeAll(Arrays.asList(cats)); }
}
