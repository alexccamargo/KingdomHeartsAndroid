package acdc.kingdomhearts.ui.khbbs.models.json

import com.google.gson.annotations.SerializedName

data class CommandsJSON(
    @SerializedName("data")
    val data: List<CommandJSON>
)

data class CommandJSON(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("melding")
    val meldingJSON: List<CommandMeldingJSON>
)

data class CommandMeldingJSON(
    @SerializedName("fi")
    val firstItemId: String,
    @SerializedName("si")
    val secondItemId: String,
    @SerializedName("cg")
    val crystalGroup: String,
    @SerializedName("perc")
    val percentage: String
)
