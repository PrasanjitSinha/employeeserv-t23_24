package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeRepo;
import com.paypal.bfs.test.employeeserv.mapper.EmployeeMapper;
import com.paypal.bfs.test.employeeserv.model.EmployeeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmployeeMapper employeeMapper;


    private final Map<Integer, Employee> employeeMap = new ConcurrentHashMap<>();

    public EmployeeService(EmployeeRepo employeeRepo, EmployeeMapper employeeMapper) {
        this.employeeRepo = employeeRepo;
        this.employeeMapper = employeeMapper;
    }

    public Optional<Employee> byId(String id){

        Employee employee = employeeMap.get(Integer.valueOf(id));
        if(Objects.nonNull(employee)){
            return Optional.of(employee);
        }

        Optional<EmployeeTable> et = employeeRepo.findById(Integer.valueOf(id));
        if(et.isPresent()){
            Employee e = employeeMapper.forAPI(et.get());
            if(Objects.nonNull(e)){
                employeeMap.put(e.getId(),e);
                return Optional.of(e);
            }

        }
        return Optional.empty();
    }

    public boolean create(Employee employeeRequest){
        EmployeeTable employeeTable = null;
        try{
            employeeTable = employeeRepo.save(employeeMapper.forDB(employeeRequest));
            if(Objects.nonNull(employeeTable)){
                return true;
            }
        }catch(Exception e){
            System.out.println("Error in table creating...");
        }

        return false;
    }

}