package com.example.hanghaero.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.hanghaero.dto.column.ColResponseDto;
import com.example.hanghaero.exception.entity.comment.ObjectNotFoundException;
import com.example.hanghaero.service.CardService;
import com.example.hanghaero.service.ColService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ColRestController {
	private final ColService columnService;

	@GetMapping("/boards/{boardId}")
	public String getColumns(@PathVariable Long boardId, Model model){
		System.out.println("[ColRestController]" + boardId);
		System.out.println("[List<ColResponseDto> getColumns ajax 호출]" + boardId);

		if(columnService.getColumns(boardId) == null){
			System.out.println("exception 실행");
			new ObjectNotFoundException();
		}
		model.addAttribute("columns", columnService.getColumns(boardId));
		return "board";
	}
}
