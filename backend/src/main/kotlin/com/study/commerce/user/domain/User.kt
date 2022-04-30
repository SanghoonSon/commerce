package com.study.commerce.user.domain
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null,
    var userId: String,
    var password: String,
    var phoneNumber: String,
    var emailAddress: String,
    var gender: String,
    var address: String,
    var role: String,
    var registeredDate: Date,
    var failCount: Int = 0,
    var lockStatus: Boolean = false
)