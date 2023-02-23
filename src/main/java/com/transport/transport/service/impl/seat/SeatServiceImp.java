package com.transport.transport.service.impl.seat;

import com.transport.transport.model.entity.FreeSeat;
import com.transport.transport.repository.SeatRepository;
import com.transport.transport.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImp implements SeatService {
    private final SeatRepository repository;
    @Override
    public List<FreeSeat> findAll() {
        return null;
    }

    @Override
    public FreeSeat findById(Long id) {
        return null;
    }

    @Override
    public void save(FreeSeat entity) {
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(FreeSeat entity) {

    }

    @Override
    public List<FreeSeat> findAllSeatByVehicle(Long vehicleId) {
        return repository.findAllByVehicleId(vehicleId);
    }
}
