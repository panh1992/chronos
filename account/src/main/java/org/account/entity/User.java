package org.account.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user", schema = "account")
public class User {

    @Id
    private Long userId;

    private String username;

    private String password;

}
