package dev.milzipmoza.secutiry.domain.dto

import dev.milzipmoza.secutiry.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUserDetails(
        val user: User,
        private val authorities: Collection<GrantedAuthority>
) :UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword(): String = user.password!!

    override fun getUsername(): String = user.name

    override fun isAccountNonExpired(): Boolean = user.enabled

    override fun isAccountNonLocked(): Boolean = user.enabled

    override fun isCredentialsNonExpired(): Boolean = user.enabled

    override fun isEnabled(): Boolean = user.enabled
}
