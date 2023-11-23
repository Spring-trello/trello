// package com.example.hanghaero.assured;
//
// import org.springframework.http.MediaType;
//
// import com.example.hanghaero.dto.CommentRequestDto;
//
// import io.restassured.RestAssured;
// import io.restassured.response.ExtractableResponse;
// import io.restassured.response.Response;
//
// public class CommentRestAssuredCRUD {
// 	public static ExtractableResponse<Response> createComment(CommentRequestDto commentRequestDto) {
// 		return RestAssured
// 			.given().log().all()
// 			.contentType(MediaType.APPLICATION_JSON_VALUE)
// 			.body(commentRequestDto)
// 			.when()
// 			.post("/cards/{cardId}/comment")
// 			.then()
// 			.log().all()
// 			.extract();
// 	}
// }
