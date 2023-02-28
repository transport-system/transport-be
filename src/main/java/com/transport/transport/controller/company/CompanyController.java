package com.transport.transport.controller.company;

import com.transport.transport.common.EndpointConstant;
import com.transport.transport.config.security.jwt.JwtService;
import com.transport.transport.mapper.CompanyMapper;
import com.transport.transport.mapper.VehicleMapper;
import com.transport.transport.model.entity.Company;
import com.transport.transport.model.entity.Vehicle;
import com.transport.transport.model.request.company.CompanyRequest;
import com.transport.transport.model.request.vehicle.VehicleRequest;
import com.transport.transport.model.response.conpany.CompanyResponse;
import com.transport.transport.model.response.conpany.CompanyResponseMsg;
import com.transport.transport.model.response.vehicle.VehicleResponse;
import com.transport.transport.service.CompanyService;
import com.transport.transport.service.VehicleService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = EndpointConstant.Company.COMPANY_ENDPOINT)
@Api( tags = "Companies")
public class CompanyController {
    private final CompanyMapper companyMapper;
    private final CompanyService companyService;
    private final VehicleMapper vehicleMapper;
    private final VehicleService vehicleService;


    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    @GetMapping
    public ResponseEntity<?> getAllCompany() {
        List<Company> companies = companyService.findAll();
        List<CompanyResponse> response = companyMapper.mapToCompanyResponse(companies);
        return new ResponseEntity<>(new CompanyResponseMsg("Get ALL Company", response), null, 200);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable(name = "id") Long id) {
        Company company = companyService.findById(id);
        CompanyResponse response = companyMapper.mapToCompanyResponse(company);
        return new ResponseEntity<>(new CompanyResponseMsg("Get  Company Id", response), null, 200);
    }

    //COMPANY
    @GetMapping("vehicle/{companyId}")
    public ResponseEntity<?> getVehicleFromCompany(@PathVariable(name = "companyId") Long id) {
        List<Vehicle> vehicles = vehicleService.findAllByCompanyId(id);
        List<VehicleResponse> response = vehicleMapper.mapToVehicleResponse(vehicles);
        return new ResponseEntity<>(new CompanyResponseMsg("Get Company id with Vehicles", id, response), null, 200);
    }

    @GetMapping("/vehicle/{companyId}/{id}")
    public ResponseEntity<?> getVehicleIdFromCompanyId(@PathVariable(name = "companyId") Long companyId
            , @PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(vehicleService.getVehicleIdAndCompanyId(companyId, id), HttpStatus.OK);
    }

    @GetMapping("/vehicle/name/{name}&{companyId}")
    public ResponseEntity<?> getVehicleNameFromCompanyId(@PathVariable(name = "companyId") Long companyId,
                                                         @PathVariable(name = "name") String name) {

        return new ResponseEntity<>(vehicleService.getVehiclenameAndCompanyId(name,companyId), HttpStatus.OK);
    }

    @GetMapping("/vehicle/status/{status}&{companyId}")
    public ResponseEntity<?> getVehicleStatusFromCompanyId(@PathVariable(name = "companyId") Long companyId, @PathVariable(name = "status") String name) {

        return new ResponseEntity<>(vehicleService.findAllByStatusAndCompany_Id(name,companyId), HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    public ResponseEntity<?> registerCompany(@Valid @RequestBody CompanyRequest request, @RequestHeader(AUTHORIZATION) String token) {
        Company company = companyService.registerCompany(request);

        CompanyResponse response = companyMapper.mapToCompanyResponse(company);
        return new ResponseEntity<>(new CompanyResponseMsg("Create successfully", response), null, 200);
    }

    @PreAuthorize("hasAuthority(T(com.transport.transport.common.RoleEnum).ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") Long id) {
        companyService.delete(id);
        return new ResponseEntity<>(new CompanyResponseMsg("Delete successfully"), null, 204);
    }

}
