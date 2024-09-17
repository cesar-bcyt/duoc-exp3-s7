package com.example.exp3s7.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exp3s7.model.Pelicula;
import com.example.exp3s7.service.PeliculaService;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    private final PeliculaService peliculaService;

    @Autowired
    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pelicula>>> getAllPeliculas() {
        List<EntityModel<Pelicula>> peliculas = peliculaService.getAllPeliculas().stream()
                .map(pelicula -> EntityModel.of(pelicula,
                        linkTo(methodOn(PeliculaController.class).getPeliculaById(pelicula.getId())).withSelfRel(),
                        linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withRel("peliculas")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(peliculas,
                        linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pelicula>> getPeliculaById(@PathVariable Long id) {
        return peliculaService.getPeliculaById(id)
                .map(pelicula -> EntityModel.of(pelicula,
                        linkTo(methodOn(PeliculaController.class).getPeliculaById(pelicula.getId())).withSelfRel(),
                        linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withRel("peliculas")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<Pelicula>> createPelicula(@RequestBody Pelicula pelicula) {
        Pelicula savedPelicula = peliculaService.savePelicula(pelicula);
        EntityModel<Pelicula> entityModel = EntityModel.of(savedPelicula,
                linkTo(methodOn(PeliculaController.class).getPeliculaById(savedPelicula.getId())).withSelfRel(),
                linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withRel("peliculas"));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pelicula>> updatePelicula(@PathVariable Long id, @RequestBody Pelicula pelicula) {
        pelicula.setId(id);
        Pelicula updatedPelicula = peliculaService.savePelicula(pelicula);
        EntityModel<Pelicula> entityModel = EntityModel.of(updatedPelicula,
                linkTo(methodOn(PeliculaController.class).getPeliculaById(updatedPelicula.getId())).withSelfRel(),
                linkTo(methodOn(PeliculaController.class).getAllPeliculas()).withRel("peliculas"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePelicula(@PathVariable Long id) {
        peliculaService.deletePelicula(id);
        return ResponseEntity.noContent().build();
    }
}