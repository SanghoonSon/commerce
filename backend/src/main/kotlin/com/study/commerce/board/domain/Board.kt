package com.study.commerce.board.domain

import javax.persistence.*

@Entity
@Table(name = "board")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var board_seq: Int? = null,
    var title: String,
    var contents: String,
    var user_id: String,
)