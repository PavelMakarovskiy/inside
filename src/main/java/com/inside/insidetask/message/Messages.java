package com.inside.insidetask.message;

import com.inside.insidetask.common.GeneralEntity;
import com.inside.insidetask.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table
@Entity
public class Messages extends GeneralEntity {
    @Column
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition = "user_id")
    private User user;
}
