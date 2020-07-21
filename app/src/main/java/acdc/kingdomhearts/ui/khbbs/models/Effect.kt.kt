package acdc.kingdomhearts.ui.khbbs.models

data class Effect(
    val id: String,
    val description: String,
    val crystalGroupsAssociated: List<String>
) {
    override fun toString(): String = description
}
