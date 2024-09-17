package com.example.exp3s7.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.exp3s7.model.Pelicula;
import com.example.exp3s7.service.PeliculaService;

@WebMvcTest(PeliculaController.class)
public class PeliculaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeliculaService peliculaService;

    @Test
    public void obtenerTodasLasPeliculasTest() throws Exception {
        List<Pelicula> peliculas = new ArrayList<>();
        peliculas.add(new Pelicula("Titanic", "James Cameron", 1997, "Un barco que se hunde"));
        peliculas.add(new Pelicula("Avatar", "James Cameron", 2009, "Un barco que se hunde"));
        when(peliculaService.getAllPeliculas()).thenReturn(peliculas);
        mockMvc.perform(get("/api/peliculas"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("_embedded.peliculaList[0].title").value("Titanic"))
            .andExpect(jsonPath("_embedded.peliculaList[0].director").value("James Cameron"))
            .andExpect(jsonPath("_embedded.peliculaList[0].year").value(1997))
            .andExpect(jsonPath("_embedded.peliculaList[0].synopsis").value("Un barco que se hunde"))
            .andExpect(jsonPath("_embedded.peliculaList[1].title").value("Avatar"))
            .andExpect(jsonPath("_embedded.peliculaList[1].director").value("James Cameron"))
            .andExpect(jsonPath("_embedded.peliculaList[1].year").value(2009))
            .andExpect(jsonPath("_embedded.peliculaList[1].synopsis").value("Un barco que se hunde"));
    }

    @Test
    public void obtenerPeliculaPorIdTest() throws Exception {
        Pelicula pelicula = new Pelicula("Titanic", "James Cameron", 1997, "Un barco que se hunde");
        pelicula.setId(1L);
        when(peliculaService.getPeliculaById(pelicula.getId())).thenReturn(Optional.of(pelicula));
        mockMvc.perform(get("/api/peliculas/{id}", pelicula.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.title").value("Titanic"))
            .andExpect(jsonPath("$.director").value("James Cameron"))
            .andExpect(jsonPath("$.year").value(1997))
            .andExpect(jsonPath("$.synopsis").value("Un barco que se hunde"))
            .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/peliculas/1"))
            .andExpect(jsonPath("$._links.peliculas.href").value("http://localhost/api/peliculas"));
    }
}
