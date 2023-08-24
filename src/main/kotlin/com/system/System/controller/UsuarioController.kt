package com.system.System.controller

import com.system.System.Dto.CredenciaisDto
import com.system.System.Dto.TokenDto
import com.system.System.model.Usuario
import com.system.System.securityJwt.JwtService
import com.system.System.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = ["http://localhost:8080"])
class UsuarioController {

    @Autowired
    private val usuarioService : UsuarioService? = null;
    @Autowired
    private val jwtService : JwtService? = null;

    @GetMapping("/findById/{id}")
    fun findById(@PathVariable id : Short) : Optional<Usuario> {
        return usuarioService!!.findById(id);
    }

    @GetMapping("/findByLogin/{login}")
    fun usuario(@PathVariable login : String) : Usuario {
        return usuarioService!!.findByLogin(login);
    }

    @PostMapping("/auth")
    fun autenticar(@RequestBody credenciais: CredenciaisDto): TokenDto {
        return try {
            val usuario: Usuario = Usuario.builder()
                .login(credenciais.login!!)
                .senha(credenciais.senha!!).build()
            usuarioService!!.autenticar(usuario)
            val token = jwtService!!.gerarToken(usuario)
            TokenDto(usuario.login, token)
        } catch (e: UsernameNotFoundException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, e.message)
        }
    }

}