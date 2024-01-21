package fr.dev.sydher.financicraft.bean.entity

import jakarta.persistence.*

@Entity
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var icon: String,
    var color: String

)
