package com.example.hanghaero.controller;

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
    public List<Card> getCardsByColumnId(Model model, @PathVariable Long columnId) {
        List<Card> cards = cardService.getCardsByColumnId(columnId);
//        model.addAttribute("cards", cards);
        return cards;
    }
}
