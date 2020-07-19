package acdc.kingdomhearts.ui.khbbs.models.json

import com.google.gson.annotations.SerializedName

data class CrystalEffectsJSON(
    @SerializedName("data")
    val data: List<CrystalEffectJSON>
)

data class CrystalEffectJSON(
    @SerializedName("id")
    val id: String,
    @SerializedName("refs")
    val refs: List<CrystalEffectRefsJSON>
)

data class CrystalEffectRefsJSON(
    @SerializedName("cid")
    val cristalId: String,
    @SerializedName("eid")
    val effectId: String
)

