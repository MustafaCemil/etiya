package com.etiya.etiya.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Customers extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Column(name = "gender",length = 50)
    private String gender;

    @OneToMany(mappedBy = "customers",fetch = FetchType.LAZY)
    private Set<Ticket> ticket;

    @Column(name = "email",length = 50,unique = true)
    private String email;

    @Column(name = "birth_day")
    @Temporal(TemporalType.DATE)
    private Date birthDay;

    @Column(name = "phone_number",length = 11)
    private String phoneNumber;

    @Column(name = "indirim_kuponu")
    private String indirimKuponu;

}
