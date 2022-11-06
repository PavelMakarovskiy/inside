package com.inside.insidetask.common;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public class GeneralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
