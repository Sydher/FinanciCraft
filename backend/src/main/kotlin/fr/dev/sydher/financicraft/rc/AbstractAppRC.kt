package fr.dev.sydher.financicraft.rc

import fr.dev.sydher.financicraft.bean.ApiResponse

abstract class AbstractAppRC {

    protected fun <T> getResponse(response: T): ApiResponse<T> {
        return ApiResponse(response)
    }

}
