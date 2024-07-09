package org.example.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.validator.custom_annotation.EmailUniquenessCheck;

/**
 * Student data model.
 * Contains student information such as ID, name, email and group.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Student {

    @Min(value = 0, message = "ID cannot be less than or equal to 0")
    private int id;

    @Size(min = 2, max = 32)
    private String name;

    @Email
    @Size(min = 5, max = 256)
    @EmailUniquenessCheck
    private String email;

    @Size(min = 3, max = 9)
    private String group;
}
