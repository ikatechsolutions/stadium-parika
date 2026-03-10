package com.stadium_parika.controller.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stadium_parika.dto.ParkingDto;
import com.stadium_parika.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/parkings")
public interface ParkingApi {
	
	@Operation(summary = "Créer une place de parking", description = "Cette methode permet d'enregistrer ou modifier une place de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet place de parking cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet place de parking n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/parkings/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingDto save(@RequestBody ParkingDto dto);

    @Operation(summary = "Trouver une place de parking par son ID", description = "Cette methode permet de chercher une place de parking par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La place de parking a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune place de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parkings/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les places de parking", description = "Cette methode permet de chercher et renvoyer la liste des places de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des places de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parkings/list-search-pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ParkingDto> findByParkingName(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @Operation(summary = "Récupérer la liste de tous les places de parking", description = "Cette methode permet de chercher et renvoyer la liste des places de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des places de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parkings/list", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ParkingDto> findAll();

    @Operation(summary = "Supprimer une place de parking par son ID", description = "Cette methode permet de supprimer une place de parking par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La place de parking a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/parkings/delete/{id}")
    void delete(@PathVariable("id") Long id);
    
    @Operation(summary = "Activer une place de parking par son ID", description = "Cette methode permet d'activer une place de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La place de parking a ete activé avec succes"),
            @ApiResponse(responseCode = "404", description = "Aucune place de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parkings-activate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingDto activateParking(@PathVariable("id") Long id);
    
    @Operation(summary = "Desactiver une place de parking par son ID", description = "Cette methode permet de desactiver une place de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La place de parking a ete desactivé avec succes"),
            @ApiResponse(responseCode = "404", description = "Aucune place de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parkings-deactivate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingDto deactivateParking(@PathVariable("id") Long id);
    
}
