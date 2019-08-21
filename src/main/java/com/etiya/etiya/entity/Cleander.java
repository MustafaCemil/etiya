package com.etiya.etiya.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "cleander")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode
public class Cleander  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "flight_time")
    @Temporal(TemporalType.DATE)
    private Date flightTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="departure_airport_id")
    private Airport departure;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="airplane_id")
    private Airplane airplane;

    @OneToMany(mappedBy = "cleander")
    private Set<Ticket> ticket;

    @Column(name = "departure_time")
    @Temporal(TemporalType.TIME)
    private Date departureTime;

    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIME)
    private Date arrivalTime;
}
