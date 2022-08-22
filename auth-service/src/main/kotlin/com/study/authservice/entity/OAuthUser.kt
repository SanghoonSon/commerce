package com.study.authservice.entity

import com.study.authservice.entity.vo.AuthProvider
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "oauth_users")
class OAuthUser(

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "oauth_user_id")
    var id: String? = null,

    @Column(nullable = false, unique = true, length = 70)
    var email: String,

    @Column(length = 20)
    var name: String? = null,

    @Embedded
    var authProvider: AuthProvider,

    @Column(length = 200)
    var imageUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", foreignKey = ForeignKey(name = "fk_oauth_user_to_user"))
    var user: User? = null
): BaseEntity() {

    fun isSameAuthProvider(authProviderType: AuthProviderType): Boolean {
        return this.authProvider.authProviderType == authProviderType
    }

    fun isEnrollProfile() : Boolean {
        return this.user == null
    }
}