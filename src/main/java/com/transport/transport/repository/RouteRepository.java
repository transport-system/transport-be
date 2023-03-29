package com.transport.transport.repository;

import com.transport.transport.model.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findByCity1_IdAndCity2_Id(Long city1, Long city2);
}