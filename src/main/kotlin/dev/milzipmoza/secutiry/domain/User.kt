package dev.milzipmoza.secutiry.domain

import javax.persistence.*

@Entity
class User protected constructor(
        name: String,
        password: String?,
        provider: UserProvider,
        providerKey: String,
        role: UserRole
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column
    val name: String = name

    @Column
    val password: String? = password

    @Column
    val provider: UserProvider = provider

    @Column
    val providerKey: String = providerKey

    @Column
    @Enumerated(EnumType.STRING)
    val role: UserRole = role

    companion object {
        fun noProviderOf(name: String, password: String): User =
                User(
                        name = name,
                        password = password,
                        provider = UserProvider.NO_PROVIDER,
                        providerKey = "",
                        role = UserRole.ROLE_NONE
                )

        fun providerOf(name: String, provider: UserProvider, providerKey: String): User =
                User(
                        name = name,
                        password = null,
                        provider = provider,
                        providerKey = providerKey,
                        role = UserRole.ROLE_NONE,
                )
    }
}

enum class UserProvider {
    NO_PROVIDER,
    GITHUB,
    GOOGLE,
    FACEBOOK
}

enum class UserRole {
    ROLE_NONE,
    ROLE_HAS
}
