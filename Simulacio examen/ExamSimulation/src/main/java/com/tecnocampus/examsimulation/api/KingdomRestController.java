package com.tecnocampus.examsimulation.api;

import com.tecnocampus.examsimulation.application.KingdomService;
import com.tecnocampus.examsimulation.application.dto.KingdomDTO;
import com.tecnocampus.examsimulation.utilities.NotAcceptableException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/kingdoms")
public class KingdomRestController {
    private final KingdomService kingdomService;
    public KingdomRestController(KingdomService kingdomService) {
        this.kingdomService = kingdomService;
    }
    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    @Operation (summary = "Create a new Kingdom")
    public KingdomDTO createKingdom(@RequestBody KingdomDTO kingdomDTO) throws Exception {
        return kingdomService.createKingdom(kingdomDTO);
    }
    @PostMapping("/{id}")
    @ResponseStatus (HttpStatus.OK)
    @Operation (summary = "Create a new Kingdom with ID")
    public KingdomDTO startProduction(@PathVariable String id) throws Exception {
        return kingdomService.startProduction(id);

    }
    @GetMapping("/{id}")
    public KingdomDTO getKingdomById(@PathVariable String id) {
        try {
            return kingdomService.getKingdomById(id);

        } catch (NotAcceptableException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());

        }
    }

}



