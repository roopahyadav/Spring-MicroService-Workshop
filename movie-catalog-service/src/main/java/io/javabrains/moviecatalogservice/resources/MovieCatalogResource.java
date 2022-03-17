package io.javabrains.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import io.javabrains.moviecatalogservice.model.CatalogItem;
import io.javabrains.moviecatalogservice.model.Movie;

import io.javabrains.moviecatalogservice.model.Rating;
import io.javabrains.moviecatalogservice.model.UserRating;
import io.javabrains.moviecatalogservice.services.MovieInfo;
import io.javabrains.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.udp.UdpServer;


import java.util.Arrays;
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

    @Autowired
    private UserRatingInfo userRatingInfo;

    @Autowired
    private MovieInfo movieInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {


      UserRating userRating =  userRatingInfo.getUserRating(userId);

       return userRating.getRatings().stream().map(rating-> movieInfo.getCatalogInfo(rating)).collect(Collectors.toList());


       /* Connecting via web client builder*/
//           Movie movie = builder.build()
//               .get()
//               .uri("http://localhost:8082/movies/" + rating.getMovieId())
//               .retrieve()
//               .bodyToMono(Movie.class)
//               .block();

    }

    public List<CatalogItem> getFallBackCatalog(@PathVariable String userId) {
        return  Arrays.asList(new CatalogItem("No movie","",0));

    }

}
