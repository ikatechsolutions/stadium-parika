package com.stadium_parika.controller.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.stadium_parika.dto.VehicleTypeDto;
import com.stadium_parika.utils.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/vehicle_types")
public interface VehicleTypeApi {
	
	@Operation(summary = "Créer un type vehicule", description = "Cette methode permet d'enregistrer ou modifier un type vehicule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'objet type vehicule cree / modifie"),
            @ApiResponse(responseCode = "400", description = "L'objet type vehicule n'est pas valide")
    })
    @PostMapping(value = Constants.APP_ROOT + "/vehicle_types/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VehicleTypeDto save(
    		@RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "photo") MultipartFile photo
    );

    @Operation(summary = "Trouver un type vehicule par son ID", description = "Cette methode permet de chercher un type vehicule par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le type vehicule a ete trouve dans la BDD"),
            @ApiResponse(responseCode = "404", description = "Aucun type vehicule n'existe dans la BDD avec l'ID fourni")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_types/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    VehicleTypeDto findById(@PathVariable("id") Long id);

    @Operation(summary = "Récupérer la liste de tous les types vehicules", description = "Cette methode permet de chercher et renvoyer la liste des types vehicules qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des types vehicules / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_types/list-search-pagination", produces = MediaType.APPLICATION_JSON_VALUE)
    Page<VehicleTypeDto> findByVehiculeTypeName(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
    
    @Operation(summary = "Récupérer la liste de tous les types vehicules", description = "Cette methode permet de chercher et renvoyer la liste des types de vehicules qui existent" + "dans la BDD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La liste des types vehicules / Une liste vide")
    })
    @GetMapping(value = Constants.APP_ROOT + "/vehicle_types/list", produces = MediaType.APPLICATION_JSON_VALUE)
    List<VehicleTypeDto> findAll();

    @Operation(summary = "Supprimer un type vehicule par son ID", description = "Cette methode permet de supprimer un type vehicule par ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le type vehicule a ete supprime")
    })
    @DeleteMapping(value = Constants.APP_ROOT + "/vehicle_types/delete/{id}")
    void delete(@PathVariable("id") Long id);
}
