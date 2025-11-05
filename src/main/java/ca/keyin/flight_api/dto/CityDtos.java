package ca.keyin.flight_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CityDtos {
    public record CreateOrUpdate(
            @NotBlank @Size(max = 120) String name,
            @NotBlank @Size(max = 8) String state,
            @Min(0) int population
    ) {}

    public record CityResp(
            Long id, String name, String state, int population
    ) {}
}
