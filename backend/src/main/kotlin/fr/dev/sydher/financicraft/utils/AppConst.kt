package fr.dev.sydher.financicraft.utils

class AppConst {

    companion object {
        // Codes d'erreurs
        const val ERR_ITEM_NOT_FOUND = "ERR_ITEM_NOT_FOUND"

        // Messages d'erreurs
        const val MSG_ERR_ITEM_NOT_FOUND = "{} not found with id {}"

        // Messages warning
        const val MSG_WARN_ITEM_NOT_FOUND_BY_CAT = "{} not found with categoryId {}"
        const val MSG_WARN_ITEM_NOT_FOUND_BY_ACC = "{} not found with accountId {}"

        // Tags Swagger
        const val SWAGGER_TAG_ACCOUNT = "Account"
        const val SWAGGER_TAG_CATEGORY = "Category"
        const val SWAGGER_TAG_TRANSACTION = "Transaction"
    }

}