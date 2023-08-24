package com.system.System.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuario")
class Usuario(login: String, senha: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     val id: Short? = null

    @Column
    val login: @NotEmpty(message = "{campo.login.obrigatorio}") String? = null

    @Column
     val senha: @NotEmpty(message = "{campo.senha.obrigatorio}") String? = null

    @Column
     val admin = false

    @Column(name = "documento_image")
     val documento: String? = null

    @Transient
     val pessoa: Pessoa? = null

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    class Builder {
        private var login: String = ""
        private var senha: String = ""

        fun login(login: String): Builder {
            this.login = login
            return this
        }

        fun senha(senha: String): Builder {
            this.senha = senha
            return this
        }

        fun build(): Usuario {
            return Usuario(login, senha)
        }
    }
}