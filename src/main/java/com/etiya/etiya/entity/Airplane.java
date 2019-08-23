package com.etiya.etiya.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "airplane")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Airplane extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "airplane_name")
    private String airplaneName;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    @OneToMany(mappedBy = "airplane",fetch = FetchType.LAZY)
    private Set<Calendar> calendar;
}
