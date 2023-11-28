package com.example.hanghaero.controller;

import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.entity.Card;
import com.example.hanghaero.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    //@GetMapping("/cards/columns/{columnId}")
    @GetMapping("/cards/boards/{boardId}")
    @ResponseBody
    public List<CardResponseDto> getCardsByColumnId(@PathVariable Long boardId) {
        System.out.println("CardController getCardsByColumnId");
        //List<Card> cards = cardService.getCardsByColumnId(columnId);
        List<CardResponseDto> cards = cardService.getCards(boardId);
        return cards;
    }
}
