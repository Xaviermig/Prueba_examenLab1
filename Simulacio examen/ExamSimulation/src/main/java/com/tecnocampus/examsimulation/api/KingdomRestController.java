package com.tecnocampus.examsimulation.api;

import com.tecnocampus.examsimulation.application.KingdomService;
import com.tecnocampus.examsimulation.application.dto.KingdomDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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


}
