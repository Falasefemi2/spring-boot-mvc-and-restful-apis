package in.anirudhjwala.module2RestApi.controllers;

import in.anirudhjwala.module2RestApi.dto.EmployeeDTO;
import in.anirudhjwala.module2RestApi.entities.EmployeeEntity;
import in.anirudhjwala.module2RestApi.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/getSecretMessage")
    public String getMySuperSecretMessage() {
        return "Secret Message :: 13AS89$2#239";
    }

    @GetMapping(path = "/static/{employeeId}")
    public EmployeeDTO getStaticEmployeeById(@PathVariable Long employeeId) {
        return new EmployeeDTO(employeeId, "Anirudh", "hi@anirudhjwala.in", 25, LocalDate.of(2021, 3, 4),true);
    }

    @GetMapping(path = "/static")
    public String getAllStaticEmployees(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String sortBy
    ) {
        return "Employee age :: " + age + " sort by :: " + sortBy;
    }

    @PostMapping(path = "/static")
    public EmployeeDTO createNewStaticEmployee(@RequestBody EmployeeDTO inputEmployee) {
        inputEmployee.setId(134090);
        return inputEmployee;
    }

    @PutMapping
    public String updateEmployeeById() {
        return "Hello from PUT";
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeEntity inputEmployee) {
        return employeeService.createNewEmployee(inputEmployee);
    }
}
