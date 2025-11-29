package in.anirudhjwala.module2RestApi.controllers;

import in.anirudhjwala.module2RestApi.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping(path = "/getSecretMessage")
    public String getMySuperSecretMessage() {
        return "Secret Message :: 13AS89$2#239";
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
        return new EmployeeDTO(employeeId, "Anirudh", "hi@anirudhjwala.in", 25, LocalDate.of(2021, 3, 4),true);
    }

    @GetMapping
    public String getEmployees(
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String sortBy
    ) {
        return "Employee age :: " + age + " sort by :: " + sortBy;
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
        inputEmployee.setId(134090);
        return inputEmployee;
    }

    @PutMapping
    public String updateEmployeeById() {
        return "Hello from PUT";
    }
}
