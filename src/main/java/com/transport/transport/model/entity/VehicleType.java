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
@Table(name = "Vehicle_type")
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_type_id")
    private Long id;

    @Column(name = "type_name")
    private String typeName;

    @JsonBackReference
    @ManyToMany(mappedBy = "vehicles",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Company> companies;

    @JsonBackReference
    @OneToMany(mappedBy = "vehicleType",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;

}

