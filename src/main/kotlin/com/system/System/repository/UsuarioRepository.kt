package com.system.System.repository

import com.system.System.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository : JpaRepository<Usuario,Short> {

    fun findByLogin(login: String?): Optional<Usuario?>?

}
