package com.inside.insidetask.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inside.insidetask.common.GeneralEntity;
import com.inside.insidetask.message.Messages;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

// User entity, таблицы создаются с помощью javax.persistence.
@Getter
@Setter
@Table(name = "users")
@Entity
public class User extends GeneralEntity {
    @Column(unique = true)
    private String name;

    @Column
    @JsonIgnore
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Messages> messages;
}
