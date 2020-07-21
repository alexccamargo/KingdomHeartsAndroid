package acdc.kingdomhearts.ui.khbbs.models

import acdc.kingdomhearts.ui.khbbs.models.json.CrystalJSON
import acdc.kingdomhearts.ui.khbbs.models.json.EffectJSON

fun fromJSON(crystalJSON: CrystalJSON?): Crystal {
    if (crystalJSON == null) {
        return Crystal("INVALID", "Invalid")
    }

    return Crystal(crystalJSON.id, crystalJSON.description)
}

fun fromJSON(effectJSON: EffectJSON?): Effect {
    if (effectJSON == null) {
        return Effect("INVALID", "Invalid", ArrayList())
    }

    return Effect(effectJSON.id, effectJSON.description, ArrayList())
}
