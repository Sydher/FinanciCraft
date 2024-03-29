package fr.dev.sydher.financicraft.bean.entity

import jakarta.persistence.*

@Entity
data class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,

    @ManyToOne
    var category: Category? = null

)
