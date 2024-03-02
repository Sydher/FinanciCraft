package fr.dev.sydher.financicraft.rc

import fr.dev.sydher.financicraft.bean.ApiResponse
import fr.dev.sydher.financicraft.bean.dto.StatsByCategoryDTO
import fr.dev.sydher.financicraft.ds.StatsDS
import fr.dev.sydher.financicraft.utils.AppConst
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stats")
class StatsRC @Autowired constructor(private val statsDS: StatsDS) : AbstractAppRC() {

    @Operation(summary = "Get stats by main category", tags = [AppConst.SWAGGER_TAG_STATS])
    @GetMapping("/{accountId}/{dateMin}/{dateMax}")
    fun getStatsByMainCategory(
        @PathVariable accountId: Long,
        @PathVariable dateMin: String,
        @PathVariable dateMax: String
    ): ResponseEntity<ApiResponse<StatsByCategoryDTO>> {
        return ResponseEntity(getResponse(statsDS.getStatsByMainCategory(accountId, dateMin, dateMax)), HttpStatus.OK)
    }

}
