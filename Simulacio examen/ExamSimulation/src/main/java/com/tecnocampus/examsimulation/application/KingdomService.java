package com.tecnocampus.examsimulation.application;

import com.tecnocampus.examsimulation.application.dto.KingdomDTO;
import com.tecnocampus.examsimulation.domain.Kingdom;
import com.tecnocampus.examsimulation.persistence.KingdomRepository;
import com.tecnocampus.examsimulation.utilities.NotAcceptableException;
import com.tecnocampus.examsimulation.utilities.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@Service
public class KingdomService {

    private final KingdomRepository kingdomRepository;


    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }
    public KingdomDTO createKingdom(KingdomDTO kingdomDTO) throws SQLException {
        Kingdom kingdom = new Kingdom(kingdomDTO);
        kingdomRepository.addKingdom(kingdom);
        return kingdom.toDTO();
    }

    public KingdomDTO startProduction(String id) {
        Kingdom kingdom = kingdomRepository.getKingdomById(id);

        boolean noCitizens = kingdom.dailyProduction();
        if (noCitizens) {
            kingdomRepository.removeKingdomById(id);
            throw new NotAcceptableException("Kingdom has been destroyed due to lack of citizens.");
        }
        kingdomRepository.updateKingdom(kingdom);

        return kingdom.toDTO();

    }

    public KingdomDTO getKingdomById(String id) {
        Kingdom kingdom = kingdomRepository.getKingdomById(id);
        if (kingdom == null) {
            throw new NotAcceptableException("Kingdom not found with id: " + id);
        }
        return kingdom.toDTO();
    }

    public KingdomDTO investKingdom(String id, String type, KingdomDTO kingdomDTO) {
        Kingdom kingdom = kingdomRepository.getKingdomById(id);
        if (kingdom == null) {
            throw new NotFoundException("Kingdom not found with id: " + id);
        }
        int goldToInvest = kingdomDTO.gold();
        if (type.equals("food")) {
            kingdom.investInFood(goldToInvest);
        }
        else if (type.equals("citizens")) {
            kingdom.investInCitizens(goldToInvest);
        }else{
            throw new NotAcceptableException("Investment type not acceptable: " + type);
        }

        kingdomRepository.updateKingdom(kingdom);
        return kingdom.toDTO();
    }

    public KingdomDTO getRichestKingdom() {
        Kingdom kingdom = kingdomRepository.getRichestKingdom();
        return kingdom.toDTO();
    }
}
