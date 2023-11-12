package nz.adjmunro.tracker.data.remote.dto

import com.squareup.moshi.Json

typealias Per100g = Double
data class Nutriments(
    @field:Json(name = "carbohydrates_100g") val carbohydrates100g: Per100g,
    @field:Json(name = "energy-kcal_100g") val energyKcal100g: Per100g,
    @field:Json(name = "fat_100g") val fat100g: Per100g,
    @field:Json(name = "proteins_100g") val proteins100g: Per100g,
)
