package com.study.commerce.board.service

import com.study.commerce.board.domain.Board
import com.study.commerce.board.repository.IBoardRepository
import org.springframework.stereotype.Service

@Service
class BoardServiceImpl(
    private val boardRepository: IBoardRepository
) : IBoardService {

    override fun getBoardList(): List<Board> {
        try {
            return boardRepository.findAll()
        }catch (e : Exception) {
            println("Exception ${e.message}")
        }
        return emptyList()
    }

    override fun findUserBoardList(userId: String): List<Board> {

        return try {
             boardRepository.findByUserId(userId)
        }catch (e : Exception) {
            println("Exception ${e.message}")
            emptyList()
        }
    }

    override fun createBoard(board: Board) {

        try {
            boardRepository.save(board)

        }catch (e : Exception) {
            println("Exception ${e.message}")
        }
    }

    override fun deleteBoard(boardId: Int) {

        try {
            boardRepository.deleteById(boardId)
        }catch (e : Exception) {
            println("Exception ${e.message}")
        }
    }
}