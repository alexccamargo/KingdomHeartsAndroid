package acdc.kingdomhearts.ui.khbbs.models

data class CrystalEffect(
    val id: String,
    val refs: List<CrystalEffectRefs>
)

data class CrystalEffectRefs(
    val cristal: Crystal,
    val effect: Effect
)

