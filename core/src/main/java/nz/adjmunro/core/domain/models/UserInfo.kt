package nz.adjmunro.core.domain.models

import nz.adjmunro.core.domain.preferences.Age
import nz.adjmunro.core.domain.preferences.CarbRatio
import nz.adjmunro.core.domain.preferences.FatRatio
import nz.adjmunro.core.domain.preferences.Height
import nz.adjmunro.core.domain.preferences.ProteinRatio
import nz.adjmunro.core.domain.preferences.Weight

data class UserInfo(
    val gender: Gender,
    val age: Age,
    val height: Height,
    val weight: Weight,
    val activityLevel: ActivityLevel,
    val goalType: GoalType,
    val carbRatio: CarbRatio,
    val proteinRatio: ProteinRatio,
    val fatRatio: FatRatio,
)
