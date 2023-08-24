package com.system.System.repository

import com.system.System.model.Pessoa
import com.system.System.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PessoaRepository : JpaRepository<Pessoa, Short> {

    @Query(nativeQuery = true, value = "select * from pessoa p WHERE p.usuario_id = :usuario")
    fun findByIdUsuario(usuario: Short?): Optional<Pessoa?>?

    fun findByUsuario(usuario: Usuario?): Optional<Pessoa?>?

}