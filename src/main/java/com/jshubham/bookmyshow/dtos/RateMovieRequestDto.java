package com.jshubham.bookmyshow.dtos;

import lombok.Data;

@Data
public class RateMovieRequestDto {
    private long userId;
    private int movieId;
    private int rating;
}
