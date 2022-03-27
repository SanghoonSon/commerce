package com.study.commerce.board.repository

import com.study.commerce.board.domain.Board
import org.springframework.data.jpa.repository.JpaRepository

/**
 * DATABASE Sql support
 */
interface IBoardRepository : JpaRepository<Board, Int>  {

    fun findByUserId(userId : String) : List<Board>
}