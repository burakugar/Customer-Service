package com.quotation.entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customer")
@Builder
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name cannot be blank")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;
    @Size(max = 50, message = "Middle name cannot exceed 50 characters")
    private String middleName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]{10,}$", message = "Phone number should be valid")
    @Size(max = 20, message = "Phone number cannot exceed 20 characters")
    private String phoneNumber;

    @Past(message = "Birth date should be in the past")
    private LocalDate birthDate;
}

