package com.tecnocampus.examsimulation.application;

import com.tecnocampus.examsimulation.application.dto.KingdomDTO;
import com.tecnocampus.examsimulation.domain.Kingdom;
import com.tecnocampus.examsimulation.persistence.KingdomRepository;
import org.springframework.stereotype.Service;

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

        boolean production = kingdom.dailyProduction();
        if (production) {
            kingdomRepository.removeKingdomById(id);
            return null;
        }
        kingdomRepository.updateKingdom(kingdom);
        return kingdom.toDTO();

    }
}
