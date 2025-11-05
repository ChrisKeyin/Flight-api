package ca.keyin.flight_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AircraftDtos {
    public record CreateOrUpdate(
            @NotBlank @Size(max = 150) String type,
            @NotBlank @Size(max = 150) String airlineName,
            @Min(1) int numberOfPassengers
    ) {}

    public record AircraftResp(
            Long id, String type, String airlineName, int numberOfPassengers
    ) {}
}
