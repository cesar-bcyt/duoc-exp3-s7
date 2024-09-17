package com.example.exp3s7.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.exp3s7.model.Pelicula;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PeliculaRepositoryTest {
    @Autowired
    private PeliculaRepository peliculaRepository;
    
    @BeforeEach
    public void setUp() {
        // Como se cargaron datos semilla en el archivo data.sql, se debe borrar todo antes de empezar a probar
        peliculaRepository.deleteAll();
    }

    @Test
    public void testFindAll() {
        List<Pelicula> peliculas = peliculaRepository.findAll();
        assertEquals(0, peliculas.size());
    }

    @Test
    public void guardarPeliculaTest() {
        // Arrange
        Pelicula pelicula = new Pelicula("Titanic", "James Cameron", 1997, "Un barco que se hunde");
        // Act
        Pelicula peliculaGuardada = peliculaRepository.save(pelicula);
        // Assert
        assertThat(peliculaGuardada).isNotNull();
        assertThat(peliculaGuardada.getId()).isGreaterThan(0);
        assertEquals("Titanic", peliculaGuardada.getTitle());
        assertEquals("James Cameron", peliculaGuardada.getDirector());
        assertEquals(1997, peliculaGuardada.getYear());
        assertEquals("Un barco que se hunde", peliculaGuardada.getSynopsis());
    }

    @Test
    public void obtenerPeliculaPorIdTest() {
        Pelicula pelicula = new Pelicula("Titanic", "James Cameron", 1997, "Un barco que se hunde");
        peliculaRepository.save(pelicula);
        Pelicula peliculaObtenida = peliculaRepository.findById(pelicula.getId()).orElse(null);
        assertThat(peliculaObtenida).isNotNull();
        assertEquals("Titanic", peliculaObtenida.getTitle());
        assertEquals("James Cameron", peliculaObtenida.getDirector());
        assertEquals(1997, peliculaObtenida.getYear());
        assertEquals("Un barco que se hunde", peliculaObtenida.getSynopsis());
    }
}