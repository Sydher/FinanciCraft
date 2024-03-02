package fr.dev.sydher.financicraft.ds

import fr.dev.sydher.financicraft.bean.dto.StatsByCategoryDTO
import java.util.*

interface StatsDS {

    /**
     * Calcul stats by main category
     * @param accountId id of the account
     * @param dateMin start of the period to search
     * @param dateMax start of the period to search
     * @return stats
     */
    fun getStatsByMainCategory(accountId: Long, dateMin: String, dateMax: String): StatsByCategoryDTO

}
