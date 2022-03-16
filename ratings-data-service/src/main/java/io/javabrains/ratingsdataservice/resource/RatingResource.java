package io.javabrains.ratingsdataservice.resource;

import io.javabrains.ratingsdataservice.model.Rating;
import io.javabrains.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable  String movieId){
        return new Rating(movieId,4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getListOfRating(@PathVariable  String userId){

        List<Rating> ratings = Arrays.asList(new Rating("abc",4),
        new Rating("Xyz",5)  );
        UserRating userRating = new UserRating();
         userRating.setRatings(ratings);
        return userRating;
    }
}
