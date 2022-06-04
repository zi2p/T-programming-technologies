package com.kotiki.dtos;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.entities.Owner;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class OwnerDto implements Serializable {
    private Long id;
    private String name;
    private LocalDateTime birthDate;
    private List<Long> catsIds;

    public OwnerDto() { }

    public OwnerDto(Owner owner) {
        id = owner.getId();
        name = owner.getName();
        birthDate = owner.getBirthDate();
        catsIds = owner.getCats().stream().map(Cat::getId).toList();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LocalDateTime getBirthDate() { return birthDate; }

    public void setBirthDate(LocalDateTime birthData) { this.birthDate = birthData; }

    public List<Long> getCatsIds() { return catsIds; }

    public void setCatsIds(List<Long> catsIds) { this.catsIds = catsIds; }
}
