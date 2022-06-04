package com.kotiki.dtos;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.entities.Owner;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class CatDto implements Serializable {
    private Long id;
    private String name;
    private LocalDateTime birthDate;
    private String breed;
    private String color;
    private Long ownerId;
    private List<Long> friendIds;

    public CatDto() { }

    public CatDto(Cat cat) {
        id = cat.getId();
        name = cat.getName();
        birthDate = cat.getBirthDate();
        breed = cat.getBreed();
        color = cat.getColor().toString();

        Owner owner = cat.getOwner();
        ownerId = owner == null ? -1 : owner.getId();

        friendIds = cat.getFriends().stream().map(Cat::getId).toList();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public LocalDateTime getBirthDate() { return birthDate; }

    public void setBirthDate(LocalDateTime birthData) { this.birthDate = birthData; }

    public String getBreed() { return breed; }

    public void setBreed(String breed) { this.breed = breed; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public Long getOwnerId() { return ownerId; }

    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public List<Long> getFriendIds() { return friendIds; }

    public void setFriendIds(List<Long> friendIds) { this.friendIds = friendIds; }
}
