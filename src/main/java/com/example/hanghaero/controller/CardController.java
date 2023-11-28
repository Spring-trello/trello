package com.example.hanghaero.controller;

import com.example.hanghaero.dto.card.CardResponseDto;
import com.example.hanghaero.service.CardService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/cards/boards/{boardId}")
    @ResponseBody
    public List<CardResponseDto> getCardsByBoardId(@PathVariable Long boardId) {
        return  cardService.getCardsByBoardId(boardId);
    }

    @GetMapping("/cards/columns/{columnId}")
    @ResponseBody
    public List<CardResponseDto> getCardsByColumnId(@PathVariable Long columnId) {
        return  cardService.getCardsByColumnId(columnId);
    }
}
