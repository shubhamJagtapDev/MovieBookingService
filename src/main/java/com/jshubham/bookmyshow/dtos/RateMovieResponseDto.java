package com.jshubham.bookmyshow.dtos;

import com.jshubham.bookmyshow.models.Rating;
import lombok.Data;

@Data
public class RateMovieResponseDto {
    private ResponseStatus responseStatus;
    private Rating rating;
}
