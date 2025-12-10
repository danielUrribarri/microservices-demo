package org.example.pagilaactorservice.dto;

import jakarta.validation.constraints.NotBlank;

public class ActorCreateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
