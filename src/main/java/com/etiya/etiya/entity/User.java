package com.etiya.etiya.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "f_name")
    private String firstName;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "email")
    private String email;

    @Column(name = "pass")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "role")
    private String role;

}
