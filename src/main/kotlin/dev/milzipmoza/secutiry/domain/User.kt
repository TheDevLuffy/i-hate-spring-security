package dev.milzipmoza.secutiry.domain

import javax.persistence.*

@Entity
class User protected constructor(
        email: String,
        name: String,
        password: String?,
        provider: UserProvider,
        providerKey: String,
        enabled: Boolean,
        role: UserRole
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column
    val email: String = email

    @Column
    val name: String = name

    @Column
    val password: String? = password

    @Column
    val provider: UserProvider = provider

    @Column
    val providerKey: String = providerKey

    @Column
    val enabled: Boolean = enabled

    @Column
    @Enumerated(EnumType.STRING)
    val role: UserRole = role

    companion object {
        fun noProviderOf(email: String, name: String, password: String): User =
                User(
                        email = email,
                        name = name,
                        password = password,
                        provider = UserProvider.NO_PROVIDER,
                        providerKey = "",
                        enabled = true,
                        role = UserRole.ROLE_NONE
                )

        fun providerOf(email: String, name: String, provider: UserProvider, providerKey: String): User =
                User(
                        email = email,
                        name = name,
                        password = null,
                        provider = provider,
                        providerKey = providerKey,
                        enabled = true,
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
