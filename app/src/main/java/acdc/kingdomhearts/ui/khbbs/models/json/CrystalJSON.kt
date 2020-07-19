package acdc.kingdomhearts.ui.khbbs.models.json

import com.google.gson.annotations.SerializedName

data class CrystalsJSON(
    @SerializedName("data")
    val data: List<CrystalJSON>
)

data class CrystalJSON(
    @SerializedName("id")
    val id: String,
    @SerializedName("desc")
    val description: String
)