package com.system.System.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Short? = null
    private val name: String? = null
}