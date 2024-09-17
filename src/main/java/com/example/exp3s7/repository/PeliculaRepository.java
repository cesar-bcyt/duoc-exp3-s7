package com.example.exp3s7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.exp3s7.model.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
}