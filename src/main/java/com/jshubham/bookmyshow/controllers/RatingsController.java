package com.jshubham.bookmyshow.controllers;

import com.jshubham.bookmyshow.dtos.*;
import com.jshubham.bookmyshow.models.Rating;
import com.jshubham.bookmyshow.services.RatingsService;
import org.springframework.stereotype.Controller;

@Controller
public class RatingsController {

    private RatingsService ratingsService;

    public RatingsController(RatingsService ratingsService) {
        this.ratingsService = ratingsService;
    }

    public RateMovieResponseDto rateMovie(RateMovieRequestDto requestDto){
        RateMovieResponseDto responseDto = new RateMovieResponseDto();
        try {
            Rating movieRating = ratingsService.rateMovie(requestDto.getUserId(), requestDto.getMovieId(), requestDto.getRating());
            responseDto.setRating(movieRating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

    public GetAverageMovieResponseDto getAverageMovieRating(GetAverageMovieRequestDto requestDto){
        GetAverageMovieResponseDto responseDto = new GetAverageMovieResponseDto();
        try {
            double averageRating = ratingsService.getAverageRating(requestDto.getMovieId());
            responseDto.setAverageRating(averageRating);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}