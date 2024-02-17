package fr.dev.sydher.financicraft.mapper

import fr.dev.sydher.financicraft.bean.dto.CategoryDTO
import fr.dev.sydher.financicraft.bean.entity.Category

object CategoryMapper {
    fun Category.toDTO() = CategoryDTO(id, name, icon, color, main)
    fun CategoryDTO.toEntity() = Category(id, name, icon, color, main)
}
