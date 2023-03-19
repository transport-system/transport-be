package com.transport.transport.mapper;

import com.transport.transport.model.entity.Route;
import com.transport.transport.model.response.route.RoutePropose;
import com.transport.transport.model.response.route.RouteResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", config = ConfigurationMapper.class)
public interface RouteMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "id", target = "routeId")
    @Mapping(source = "city1.city", target = "cityArrival")
    @Mapping(source = "city2.city", target = "cityDeparture")
    RoutePropose mapRouteFromRoutePropose(Route route);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @InheritConfiguration(name = "mapRouteFromRoutePropose")
    List<RoutePropose> mapRouteFromRoutePropose(List<Route> route);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "city1.city", target = "cityArrival")
    @Mapping(source = "city2.city", target = "cityDeparture")
    RouteResponse mapRouteResponseFromRoute(Route route);

}
