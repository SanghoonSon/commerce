package com.study.commerce.board.service

import com.study.commerce.board.domain.Board

interface IBoardService {
    //게시판 전체 리스트
    fun getBoardList() : List<Board>

    //특정 유저의 게시판 리스트
    fun findUserBoardList(userId: String) : List<Board>

    //게시판 항목 추가
    fun createBoard(board: Board)

    //게시판 항목 삭제
    fun deleteBoard(boardId : Int)

}