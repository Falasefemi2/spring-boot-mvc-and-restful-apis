package in.anirudhjwala.module2RestApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.anirudhjwala.module2RestApi.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// POJO Class
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private long id;

    // @NotNull(message = "Required field in employee: name") --> checks for presence of field
    // @NotEmpty(message = "Name of the employee cannot be empty") --> checks for not null and length > 0
    @NotBlank(message = "Name of the employee cannot be blank") // checks for not null and trimmed length > 0
    @Size(min = 3, max = 10, message = "Number of characters in name should be in the range: [3, 10]")
    private String name;

    @NotBlank(message = "Email of the employee cannot be blank")
    @Email(message = "Email should be in valid format")
    private String email;

    @NotNull(message = "Age of the employee cannot be blank")
    @Max(value = 80, message = "Age of Employee cannot be greater than 80")
    @Min(value = 18, message = "Age of Employee cannot be less than 18")
    private Integer age;

    // @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of Employee can be USER or ADMIN")
    @NotBlank(message = "Role of the employee cannot be blank")
    @EmployeeRoleValidation
    private String role; // ADMIN, USER

    @NotNull(message = "Salary of the employee cannot be blank")
    @Positive(message = "Salary of the employee should be positive")
    @Digits(integer = 6, fraction = 2, message = "Salary of the employee can be in the form X,XX,XXX.YY")
    @DecimalMax(value = "100000.99")
    @DecimalMin(value = "100.50")
    private Double salary;

    @PastOrPresent(message = "Date of joining of the employee cannot be in the future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should be active")
    @JsonProperty("isActive")
    private boolean active;
}
