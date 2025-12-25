package movie.app.movie_service.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import movie.app.movie_service.domain.dto.response.MovieResponse;
import movie.app.movie_service.domain.entity.Movie;
import movie.app.movie_service.domain.mapper.MovieMapper;
import movie.app.movie_service.service.MovieService;



@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc
public class MovieControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @MockitoBean
    private MovieMapper movieMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        Movie movie = new Movie(1L, "Movie 1", "Description 1", 120, LocalDate.of(2021, 1, 1));

        MovieResponse movieDto = MovieResponse.builder()
            .id(movie.getId())
            .title(movie.getTitle())
            .description(movie.getDescription())
            .duration(movie.getDuration())
            .releaseDate(movie.getReleaseDate())
            .build();

        when(movieService.getAllMovies()).thenReturn(List.of(movieDto));
    }

    @Test
    void shouldReturnOkStatus() throws Exception {
        mockMvc.perform(get("/api/movie/health"))
            .andExpect(status().isOk())
            .andExpect(content().string("Movie Service is running"));

    }

    @Test
    void shouldReturnMovieList() throws Exception {
        
        mockMvc.perform(get("/api/movie").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].title").value("Movie 1"))
        .andExpect(jsonPath("$[0].description").value("Description 1"))
        .andExpect(jsonPath("$[0].duration").value(120))
        .andExpect(jsonPath("$[0].releaseDate").value("2021-01-01"));
    }


    @Test
    void shoudReturnMovieCreated() throws Exception {
        Movie movie = new Movie(1L, "Movie 1", "Description 1", 120, LocalDate.of(2021, 1, 1));
        
        MovieResponse movieDto = movieMapper.movieToMovieDto(movie);

        mockMvc.perform(post("/api/movie").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movieDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(movieDto.getId()))
        .andExpect(jsonPath("$.title").value(movieDto.getTitle()))
        .andExpect(jsonPath("$.description").value(movieDto.getDescription()))
        .andExpect(jsonPath("$.duration").value(movieDto.getDuration()))
        .andExpect(jsonPath("$.releaseDate").value(movieDto.getReleaseDate()));
    }

    

    
}
