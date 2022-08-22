package com.study.authservice.entity

import com.study.authservice.entity.vo.HomeAddress
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id")
    var id: String? = null,

    @Column(nullable = false, unique = true, length = 70)
    var email: String,

    @Column(length = 20)
    var name: String,

    @Column(nullable = false, length = 100)
    var password: String,

    @Column(length = 20)
    var phoneNumber: String? = null,

    @Embedded
    @Column
    var homeAddress: HomeAddress? = null,

    @Column
    var certificatedDate: LocalDateTime? = null,

    @Column
    @Enumerated(EnumType.STRING)
    var status: UserStatus? = UserStatus.CERTIFIED,
): BaseEntity() {

    fun isCertificated(): Boolean {
        return UserStatus.CERTIFICATED == this.status;
    }
}