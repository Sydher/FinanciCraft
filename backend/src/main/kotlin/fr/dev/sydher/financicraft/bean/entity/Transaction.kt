package fr.dev.sydher.financicraft.bean.entity

import jakarta.persistence.*
import java.util.*

@Entity
data class Transaction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
    var amount: Double,
    var date: Date,

    @ManyToOne
    var account: Account? = null,

    @ManyToMany
    var categories: List<Category>? = null

)
