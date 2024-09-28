package com.eroom.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddSupplyVerticalDTO {

    @NotBlank(message = "Supply Vertical Is Required.")
    private String supplyVertical;

}