package com.mohdeveloper.blogplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RegularUser extends BaseUser{
    @Id
    private Long id;
}
