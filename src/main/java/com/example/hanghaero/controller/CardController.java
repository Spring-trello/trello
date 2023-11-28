package com.example.hanghaero.controller;

import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/cards/columns/{columnId}")
    public List<CardResponseDto> getCardsByColumnId(Model model, @PathVariable Long columnId) {
        return cardService.getCardsByColumnId(columnId);
    }
}
