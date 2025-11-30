package in.anirudhjwala.module2RestApi.controllers;

import in.anirudhjwala.module2RestApi.dto.EmployeeDTO;
import in.anirudhjwala.module2RestApi.exceptions.ResourceNotFoundException;
import in.anirudhjwala.module2RestApi.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return new EmployeeDTO(
                employeeId, "Anirudh", "hi@anirudhjwala.in",
                25, "USER", 25000.00,
                LocalDate.of(2021, 3, 4),true
        );
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

    @PutMapping(path = "/static")
    public String updateStaticEmployeeById() {
        return "Hello from PUT";
    }

    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);

       return employeeDTO
               .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
               .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + employeeId + " not found"));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        return ResponseEntity.ok(
                employeeService.updateEmployeeById(employeeId, employeeDTO)
        );
    }

    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId) {
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);

        if (gotDeleted) {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> employeeUpdates, @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId, employeeUpdates);

        if  (employeeDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employeeDTO);
    }
}