package com.samuehcb.employeeemangement.service;

import com.samuehcb.employeeemangement.domain.Employee;
import com.samuehcb.employeeemangement.model.EmployeeDTO;
import com.samuehcb.employeeemangement.repos.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;

    public EmployeeService(final EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll(Sort.by("id"))
                .stream()
                .map(employee -> mapper.map(employee,EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO get(final Long id) {
        return employeeRepository.findById(id)
                .map(employee ->mapper.map(employee,EmployeeDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Transactional
    public Long create(final EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        mapper.map(employeeDTO, employee);
        return employeeRepository.save(employee).getId();
    }

    @Transactional
    public void update(final Long id, final EmployeeDTO employeeDTO) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapper.map(employeeDTO, employee);
        employeeRepository.save(employee);
    }

    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }


}
