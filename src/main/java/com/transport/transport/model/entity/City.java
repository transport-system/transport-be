package com.transport.transport.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "city", uniqueConstraints = @UniqueConstraint(columnNames = "city_name"))
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Column(name = "city_name")
    private String city;

    @JsonBackReference
    @OneToMany(mappedBy = "city1", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Route> route1;
    @JsonBackReference
    @OneToMany(mappedBy = "city2", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Route> route2;
}
