package com.system.System.model

import com.system.System.Interfaces.Roles
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*


class Role : Roles {
    override val ADMIN: String = "ADMIN"
    override val GERENTE: String = "GERENTE"
    override val CAIXA: String = "CAIXA"
    override val VENDEDOR: String = "VENDEDOR"
    override val RH: String = "RH"
}