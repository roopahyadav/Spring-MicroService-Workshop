package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.model.CatalogItem;
import io.javabrains.moviecatalogservice.model.Movie;
import io.javabrains.moviecatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackMovieInfo")
    public CatalogItem getCatalogInfo(Rating rating){
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),Movie.class);
        return   new CatalogItem(movie.getName(),movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallBackMovieInfo(Rating rating){
        return new CatalogItem("movie name not found","",rating.getRating());
    }
}
