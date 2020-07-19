package acdc.kingdomhearts.ui.khbbs.models

data class Command(
    val id: String,
    val name: String,
    val melding: ArrayList<CommandMelding> = ArrayList()
)

data class CommandMelding(
    val firstItem: Command?,
    val secondItem: Command?,
    val crystalGroup: CrystalEffect?,
    val percentage: String
)
