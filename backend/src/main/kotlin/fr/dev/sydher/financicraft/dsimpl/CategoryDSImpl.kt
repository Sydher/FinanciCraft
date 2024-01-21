package fr.dev.sydher.financicraft.dsimpl

import fr.dev.sydher.financicraft.bean.dto.CategoryDTO
import fr.dev.sydher.financicraft.bean.exception.CategoryNotFoundException
import fr.dev.sydher.financicraft.ds.CategoryDS
import fr.dev.sydher.financicraft.mapper.CategoryMapper.toDTO
import fr.dev.sydher.financicraft.mapper.CategoryMapper.toEntity
import fr.dev.sydher.financicraft.repository.CategoryRepository
import fr.dev.sydher.financicraft.utils.AppConst
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryDSImpl @Autowired constructor(
    private val categoryRepository: CategoryRepository
) : CategoryDS {

    companion object {
        private val log = LoggerFactory.getLogger(CategoryDSImpl::class.java)
    }

    override fun find(id: Long): CategoryDTO {
        val category = categoryRepository.findById(id)
        if (category.isPresent) {
            return category.get().toDTO()
        }
        log.error(AppConst.MSG_ERR_ITEM_NOT_FOUND, "Category", id)
        throw CategoryNotFoundException(AppConst.ERR_ITEM_NOT_FOUND)
    }

    override fun getAll(): List<CategoryDTO> {
        val categories = categoryRepository.findAll()
        return categories.map { it.toDTO() }
    }

    override fun save(category: CategoryDTO): CategoryDTO {
        val toSave = category.toEntity()
        return categoryRepository.save(toSave).toDTO()
    }

    override fun delete(id: Long) {
        val account = categoryRepository.findById(id)
        account.ifPresentOrElse(
            { categoryRepository.delete(it) },
            {
                log.error(
                    AppConst.MSG_ERR_ITEM_NOT_FOUND,
                    "Category",
                    id
                ); throw CategoryNotFoundException(AppConst.ERR_ITEM_NOT_FOUND)
            }
        )
    }

}
