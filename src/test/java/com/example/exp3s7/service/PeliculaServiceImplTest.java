package com.example.exp3s7.service;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.exp3s7.model.Pelicula;
import com.example.exp3s7.repository.PeliculaRepository;

@ExtendWith(MockitoExtension.class)
public class PeliculaServiceImplTest {
    @InjectMocks
    private PeliculaServiceImpl peliculaService;

    @Mock
    private PeliculaRepository peliculaRepository;

    @Test
    public void guardarPeliculaTest() {
        Pelicula pelicula = new Pelicula("Película 1", "Director 1", 2024, "Sinopsis 1");
        // Hacer mock del repositorio
        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(pelicula);
        
        Pelicula resultado = peliculaService.savePelicula(pelicula);

        assertNotNull(resultado);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getTitle()).isEqualTo("Película 1");
        assertThat(resultado.getDirector()).isEqualTo("Director 1");
        assertThat(resultado.getYear()).isEqualTo(2024);
        assertThat(resultado.getSynopsis()).isEqualTo("Sinopsis 1");
    }

    @Test
    public void obtenerPeliculaPorIdTest() {
        Pelicula pelicula = new Pelicula("Titanic", "James Cameron", 1997, "Un barco que se hunde");
        when(peliculaRepository.save(any(Pelicula.class))).thenReturn(pelicula);
        when(peliculaRepository.findById(pelicula.getId())).thenReturn(Optional.of(pelicula));
        peliculaRepository.save(pelicula);
        Pelicula peliculaObtenida = peliculaService.getPeliculaById(pelicula.getId()).orElse(null);
        assertNotNull(peliculaObtenida);
        assertEquals("Titanic", peliculaObtenida.getTitle());
        assertEquals("James Cameron", peliculaObtenida.getDirector());
        assertEquals(1997, peliculaObtenida.getYear());
    }
}