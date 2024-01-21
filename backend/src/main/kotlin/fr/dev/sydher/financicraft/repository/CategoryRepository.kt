package fr.dev.sydher.financicraft.repository

import fr.dev.sydher.financicraft.bean.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>
