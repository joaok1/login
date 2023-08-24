package com.system.System.securityJwt

import com.system.System.service.UsuarioService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthFilter(jwtService: JwtService?, usuarioService: UsuarioService?) : OncePerRequestFilter()  {

    private val jwtService: JwtService? = null;
    private val usuarioService: UsuarioService? = null;

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = httpServletRequest.getHeader("Authorization")
        if (authorization != null && authorization.startsWith("Bearer")) {
            val token = authorization.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            val isValid = jwtService!!.tokenValido(token)
            if (isValid) {
                val loginUsuario = jwtService.obterLoginUsuario(token)
                val usuario = usuarioService!!.loadUserByUsername(loginUsuario)
                val user = UsernamePasswordAuthenticationToken(
                    usuario, null,
                    usuario.authorities
                )
                user.details = WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                SecurityContextHolder.getContext().authentication = user
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }



}