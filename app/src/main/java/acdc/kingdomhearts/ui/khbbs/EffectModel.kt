package acdc.kingdomhearts.ui.khbbs

import com.google.gson.annotations.SerializedName

data class EffectModel (
    @SerializedName("id")
    val id: String,
    @SerializedName("desc")
    val description: String
)


data class EffectsModel (
    @SerializedName("data")
    val data: List<EffectModel>
)
