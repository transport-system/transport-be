package com.transport.transport.service.impl.route;

import com.transport.transport.model.entity.City;
import com.transport.transport.model.entity.Route;
import com.transport.transport.model.request.route.RouteRequest;
import com.transport.transport.repository.CityRepository;
import com.transport.transport.repository.RouteRepository;
import com.transport.transport.repository.TripRepository;
import com.transport.transport.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImp implements RouteService {
    private final RouteRepository routeRepository;
    private final CityRepository cityRepository;

    private final TripRepository tripRepository;
    @Override
    public Route create(String Departure, String Arrival) {
        Route routeNew = new Route();
        City departureCity = cityRepository.findByCity(Departure);
        City arrivalCity = cityRepository.findByCity(Arrival);
        if(departureCity == null || arrivalCity == null){
            throw new RuntimeException("City not exits");
        }
        routeNew.setCity1(arrivalCity);
        routeNew.setCity2(departureCity);
        return routeRepository.save(routeNew);
    }
    @Override
    public List<Route> allRoute() {
        return routeRepository.findAll();
    }

    @Override
    public List<Route> propose() {
        List<Long> list = tripRepository.findCountAndNameOrderByCountDesc();
        List<Route> propose = new ArrayList<>();
        int i = 0;
        for (Long l: list){
            i++;
            if(i == 3){
                break;
            }
            propose.add(routeRepository.findById(l).get());
        }
        return propose;
    }
}
