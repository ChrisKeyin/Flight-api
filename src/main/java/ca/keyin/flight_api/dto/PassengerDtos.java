package ca.keyin.flight_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PassengerDtos {
    public record CreateOrUpdate(
            @NotBlank @Size(max = 80) String firstName,
            @NotBlank @Size(max = 80) String lastName,
            @NotBlank @Size(max = 25) String phoneNumber,
            @NotNull Long cityId
    ) {}

    public record PassengerResp(
            Long id, String firstName, String lastName, String phoneNumber, Long cityId
    ) {}
}
