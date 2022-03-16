package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.model.CatalogItem;
import io.javabrains.moviecatalogservice.model.Movie;

import io.javabrains.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder builder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {


      UserRating userRating =  restTemplate.getForObject("http://movie-rating-service/ratings/user/"+userId, UserRating.class);

       return userRating.getRatings().stream().map(rating-> {

       Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),Movie.class);
         return   new CatalogItem(movie.getName(),movie.getDescription(), rating.getRating());
       }).collect(Collectors.toList());


       /* Connecting via web client builder*/
//           Movie movie = builder.build()
//               .get()
//               .uri("http://localhost:8082/movies/" + rating.getMovieId())
//               .retrieve()
//               .bodyToMono(Movie.class)
//               .block();

    }
}
