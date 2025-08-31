package com.jshubham.bookmyshow.dtos;

import com.jshubham.bookmyshow.models.Show;
import lombok.Data;

@Data
public class CreateShowResponseDTO {
    private ResponseStatus responseStatus;
    private Show show;
}
