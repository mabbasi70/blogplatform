package com.mohdeveloper.blogplatform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BusinessUser extends BaseUser {

    @Id
    private Long id;
}
