package com.etiya.etiya.entity;

import lombok.*;

import javax.persistence.*;
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

    @Column(name = "price")
    private Float price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="airplane_id")
    private Airplane airplane;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "flight_time")
    @Temporal(TemporalType.DATE)
    private Date flightTime;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="departure_airport_id")
    private Airport departure;

    @OneToMany(mappedBy = "cleander",fetch = FetchType.LAZY)
    private Set<Ticket> ticket;

    @Column(name = "departure_time")
    @Temporal(TemporalType.TIME)
    private Date departureTime;

    @Column(name = "arrival_time")
    @Temporal(TemporalType.TIME)
    private Date arrivalTime;
}
