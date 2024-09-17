package com.example.exp3s7.service;

import java.util.List;
import java.util.Optional;

import com.example.exp3s7.model.Pelicula;

public interface PeliculaService {
    List<Pelicula> getAllPeliculas();
    Optional<Pelicula> getPeliculaById(Long id);
    Pelicula savePelicula(Pelicula pelicula);
    void deletePelicula(Long id);
}