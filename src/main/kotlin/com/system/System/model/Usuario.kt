package com.system.System.model

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "usuario")
class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Short? = null

    @Column
    private val login: @NotEmpty(message = "{campo.login.obrigatorio}") String? = null

    @Column
    private val senha: @NotEmpty(message = "{campo.senha.obrigatorio}") String? = null

    @Column
    private val admin = false

    @Column(name = "documento_image")
    private val documento: String? = null

    @Transient
    private val pessoa: Pessoa? = null
}