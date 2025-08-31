package com.jshubham.bookmyshow.controllers;

import com.jshubham.bookmyshow.dtos.CreateShowRequestDTO;
import com.jshubham.bookmyshow.dtos.CreateShowResponseDTO;
import com.jshubham.bookmyshow.dtos.ResponseStatus;
import com.jshubham.bookmyshow.models.Show;
import com.jshubham.bookmyshow.models.enums.Feature;
import com.jshubham.bookmyshow.models.enums.SeatType;
import com.jshubham.bookmyshow.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class ShowController {

    private ShowService showService;


    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }


    public CreateShowResponseDTO createShow(CreateShowRequestDTO requestDTO) {
        CreateShowResponseDTO responseDTO = new CreateShowResponseDTO();
        Long movieId = requestDTO.getMovieId();
        Long userId = requestDTO.getUserId();
        Long screenId = requestDTO.getScreenId();
        Date startTime = requestDTO.getStartTime();
        Date endTime = requestDTO.getEndTime();
        List<Feature> features = requestDTO.getFeatures();
        List<Pair<SeatType, Double>> pricingConfig = requestDTO.getPricingConfig();

        try {
            Show show = showService.createShow(userId, movieId, screenId, startTime, endTime, pricingConfig, features);
            responseDTO.setShow(show);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }
}
