package com.study.commerce.board.api

import com.study.commerce.board.domain.Board
import com.study.commerce.board.service.IBoardService
import com.study.commerce.order.dto.OrderCreateRequest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/board")
@Api(tags = ["board"]) //swagger
class BoardController(
    //private val boardService : IBoardService
) {

    @Autowired
    private lateinit var boardService : IBoardService


    @GetMapping("/list")
    @ApiOperation(value = "/api/board/list", notes = "get all from board list")
    fun getBoardList(): ResponseEntity<List<Board>> {
        return try {

            val result = boardService.getBoardList()
            ResponseEntity.status(HttpStatus.FOUND).body(result)

        }catch (e : Exception) {
            println("Exception ${e.message}")
            ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(emptyList())
        }
    }

    @GetMapping("/list/{userId}")
    @ApiOperation(value = "/api/board/list/{user_id}", notes = "get user's item from board list")
    fun getUserBoardList(@PathVariable userId: String): ResponseEntity<List<Board>> {
        return try {

            val result = boardService.findUserBoardList(userId)
            ResponseEntity.status(HttpStatus.FOUND).body(result)

        }catch (e : Exception) {
            println("Exception ${e.message}")
            ResponseEntity.status(HttpStatus.NOT_EXTENDED).body(emptyList())
        }
    }

    @PostMapping("/create")
    @ApiOperation(value = "/api/board/create", notes = "create item to board list")
    @ResponseStatus(HttpStatus.CREATED)
    fun createBoard(body: Board): ResponseEntity<Unit> {
        return try {

            val result = boardService.createBoard(body)
            ResponseEntity.status(HttpStatus.CREATED).body(result)

        }catch (e : Exception) {
            println("Exception ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Unit)
        }
    }

    @PostMapping("/delete")
    @ApiOperation(value = "/api/board/delete", notes = "delete item to board list")
    @ResponseStatus(HttpStatus.OK)
    fun deleteBoard(boardId: Int): ResponseEntity<Unit> {
        return try {
            val result = boardService.deleteBoard(boardId)
            ResponseEntity.status(HttpStatus.OK).body(result)

        }catch (e : Exception) {
            println("Exception ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Unit)
        }
    }
}