package fr.dev.sydher.financicraft.ds

import fr.dev.sydher.financicraft.bean.dto.CategoryDTO
import fr.dev.sydher.financicraft.bean.exception.CategoryNotFoundException

interface CategoryDS {

    /**
     * Find a category by id.
     * @param id the identifier of the category to find
     * @return the category
     * @throws CategoryNotFoundException if the category with the specified id doesn't exist
     */
    @Throws(CategoryNotFoundException::class)
    fun find(id: Long): CategoryDTO

    /**
     * Get all categories.
     * @return all categories
     */
    fun getAll(): List<CategoryDTO>

    /**
     * Creates or updates a category.
     * @param category the category to create/update
     * @return the created/updated category
     */
    fun save(category: CategoryDTO): CategoryDTO

    /**
     * Deletes a category by its identifier
     * @param id the identifier of the category to delete
     * @throws CategoryNotFoundException if the category with the specified id doesn't exist
     */
    @Throws(CategoryNotFoundException::class)
    fun delete(id: Long)

}
