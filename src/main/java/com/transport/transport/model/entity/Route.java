package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long id;

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "city_arrival_id", referencedColumnName = "city_id")
    private City city1;

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_departure_id", referencedColumnName = "city_id")
    private City city2;

    @JsonBackReference
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Trip> trips;
}
