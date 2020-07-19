package acdc.kingdomhearts.ui.khbbs.models.json

import com.google.gson.annotations.SerializedName

data class EffectsJSON(
    @SerializedName("data")
    val data: List<EffectJSON>
)

data class EffectJSON(
    @SerializedName("id")
    val id: String,
    @SerializedName("desc")
    val description: String
)
