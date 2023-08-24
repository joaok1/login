package com.system.System.configSecurity

import com.system.System.securityJwt.JwtAuthFilter
import com.system.System.securityJwt.JwtService
import com.system.System.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@EnableWebSecurity
class SecurityConfig  {

    @Autowired
    private val usuarioService: UsuarioService? = null

    @Autowired
    private val jwtService: JwtService? = null

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtFilter(): OncePerRequestFilter? {
        return JwtAuthFilter(jwtService, usuarioService)
    }

    @Throws(Exception::class)
    protected fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .userDetailsService<UserDetailsService?>(usuarioService)
            .passwordEncoder(passwordEncoder())
    }

    @Throws(Exception::class)
    protected fun configure(http: HttpSecurity) {
        http
            .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/arquivos/**")
            .permitAll()
            .antMatchers("/api/produtos/**")
            .hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/api/pessoa/**")
            .permitAll()
            .antMatchers(HttpMethod.POST, "/api/usuarios/**")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/api/usuarios/**")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    //Libera o cors para as rotas
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = Arrays.asList("http://localhost:8080") // Configure as origens permitidas
        configuration.allowedMethods =
            Arrays.asList("GET", "POST", "PUT", "DELETE") // Configure os métodos HTTP permitidos
        configuration.allowedHeaders =
            Arrays.asList("Content-Type", "Authorization") // Configure os cabeçalhos permitidos
        configuration.allowCredentials = true // Permitir envio de credenciais (por exemplo, cookies)
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}