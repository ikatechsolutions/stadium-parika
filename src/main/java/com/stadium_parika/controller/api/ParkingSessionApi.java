package com.stadium_parika.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stadium_parika.dto.ParkingSessionDto;
import com.stadium_parika.dto.ParkingSessionRequestDto;
import com.stadium_parika.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/parking_sessions")
public interface ParkingSessionApi {
	
	@Operation(summary = "Créer ou faire entrer une session de parking", description = "Cette methode permet d'enregistrer une session de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet session de parking crée"),
            @ApiResponse(responseCode = "400", description = "L'objet session de parking n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/parking_sessions/entry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingSessionDto entryParking(@RequestBody ParkingSessionRequestDto dto);

    @Operation(summary = "Faire sortir une session de parking", description = "Cette methode permet de modifier une session de parking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet session de parking modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet session de parking n'est pas valide")
    })
    @PutMapping(value = Constants.APP_ROOT + "/parking_sessions/exit/{id}")
    ParkingSessionDto exitParking(@PathVariable("id") Long parkingSessionId);

    @Operation(summary = "Trouver une session de parking par son ID", description = "Cette methode permet de chercher une session de parking par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Une session de parking a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucune session de parking n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_sessions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ParkingSessionDto findById(@PathVariable("id") Long id);
    
    @Operation(summary = "Récupérer la liste de tous les sessions de parking", description = "Cette methode permet de chercher et renvoyer la liste des sessions de parking qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des sessions de parking / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/parking_sessions/all", produces = MediaType.APPLICATION_JSON_VALUE)
    List<ParkingSessionDto> findAll();
    
    @Operation(summary = "Supprimer une session de parking par son ID", description = "Cette methode permet de supprimer une session de parking par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La session de parking a été supprimé")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/parking_sessions/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
