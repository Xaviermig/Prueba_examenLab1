package com.tecnocampus.examsimulation.application.dto;

import java.time.LocalDateTime;

public record KingdomDTO (String id, Integer gold, Integer citizens, Integer food, LocalDateTime dateOfCreation) {
}