package com.study.commerce.board.domain

import javax.persistence.*

@Entity
@Table(name = "board")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
   private var boardId: Int? = null,

    var title: String,

    var contents: String,

    @Column(name = "user_id")
    var userId: String,
)