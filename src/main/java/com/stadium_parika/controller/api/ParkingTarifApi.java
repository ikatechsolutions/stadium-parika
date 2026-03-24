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

import com.stadium_parika.dto.ParkingTarifDto;
import com.stadium_parika.dto.ParkingTarifRequestDto;
import com.stadium_parika.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/parking_tarifs")
public interface ParkingTarifApi {
	
	@Operation(summary = "Créer un tarif de parking", description = "Cette methode permet d'enregistrer ou modifier un tarif de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet tarif de parking cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet tarif de parking n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/parking_tarifs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingTarifDto save(@RequestBody ParkingTarifRequestDto dto);

    @Operation(summary = "Trouver un tarif de parking par son ID", description = "Cette methode permet de chercher un tarif de parking par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le tarif de parking a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun tarif de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_tarifs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingTarifDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les tarifs de parking", description = "Cette methode permet de chercher et renvoyer la liste des tarifs de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des tarifs de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_tarifs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ParkingTarifDto> findAll();

    @Operation(summary = "Récupérer la liste de tarif de parking", description = "Cette methode permet de chercher et renvoyer la liste des prix de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des tarifs de parking")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_tarifs/parking/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<ParkingTarifDto> findParkingTarifs(
            @PathVariable("idParking") Long idParking,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @Operation(summary = "Récupérer la liste de tarif d'un parking", description = "Cette methode permet de chercher et renvoyer la liste des tarifs de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des tarifs d'une parking")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_tarifs/parking-with-no-pagination-and-search/{idParking}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ParkingTarifDto> findByParkingTarifs(@PathVariable("idParking") Long idParking);

    @Operation(summary = "Supprimer un tarif de parking par son ID", description = "Cette methode permet de supprimer un tarif de parking par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le tarif de parking a été supprimé")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/parking_tarifs/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
