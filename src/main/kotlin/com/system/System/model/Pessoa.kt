package com.system.System.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pessoa")
class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Short? = null

    private val nome: String? = null

    private val sobrenome: String? = null

    private val data_nascimento: Date? = null

    private val cpf: String? = null

    private val rg: String? = null

    private val endereco: String? = null

    private val cidade: String? = null

    private val estado: String? = null

    private val cep: String? = null

    private val telefone: String? = null

    private val email: String? = null

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = ["applications", "hibernateLazyInitializer"])
    private val usuario: Usuario? = null

}