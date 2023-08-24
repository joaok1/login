package com.system.System.service

import com.system.System.model.Usuario
import com.system.System.repository.PessoaRepository
import com.system.System.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService : UserDetailsService {

    @Autowired
    private val encoder : PasswordEncoder? = null;
    @Autowired
    private val usuarioRepository : UsuarioRepository? = null;
    @Autowired
    private val pessoaRepository : PessoaRepository? = null;

    fun findById(id: Short): Optional<Usuario> {
        if (id != null) {
            return usuarioRepository!!.findById(id)
        }
        return throw Exception("id invalido")
    }

    @Throws(Exception::class)
    fun autenticar(usuario: Usuario): UserDetails? {
        val user: UserDetails = loadUserByUsername(usuario.login)
        val senhasBatem = encoder!!.matches(usuario.senha, user.password)
        if (senhasBatem) {
            return user
        }
        throw Exception("Senha invalida")
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val usuario = usuarioRepository!!.findByLogin(username)!!.orElseThrow { UsernameNotFoundException("Usuário não encontrado na base de dados.") }!!
        val roles = if (usuario.admin) arrayOf<String>("ADMIN", "USER") else arrayOf<String>("USER")
        return User
            .builder()
            .username(usuario.login)
            .password(usuario.senha)
            .roles(*roles)
            .build()
    }


    fun findByLogin(login: String?): Usuario {
        val user = usuarioRepository!!.findByLogin(login)
        return user!!.get()
    }

}
