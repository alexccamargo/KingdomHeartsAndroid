package acdc.kingdomhearts.ui.khbbs

import acdc.kingdomhearts.R
import acdc.kingdomhearts.ui.khbbs.models.*
import acdc.kingdomhearts.ui.khbbs.models.json.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import java.nio.charset.Charset


class BirthBySleepFragment : Fragment() {

    private lateinit var birthBySleepViewModel: BirthBySleepViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var birthBySleepCollectionAdapter: BirthBySleepCollectionAdapter
    private lateinit var effects: List<Effect>
    private lateinit var allCommands: List<Command>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        birthBySleepViewModel = ViewModelProvider(this).get(BirthBySleepViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_khbbs, container, false)

        val commandJSONS = Gson().fromJson<CommandsJSON>(
            readFile(R.raw.khbbs_commands),
            CommandsJSON::class.java
        ).data
        val effectJSONS = Gson().fromJson<EffectsJSON>(
            readFile(R.raw.khbbs_effects),
            EffectsJSON::class.java
        ).data
        val crystalJSONS = Gson().fromJson<CrystalsJSON>(
            readFile(R.raw.khbbs_crystal),
            CrystalsJSON::class.java
        ).data
        val crystalsEffects = Gson().fromJson<CrystalEffectsJSON>(
            readFile(R.raw.khbbs_crystal_effects),
            CrystalEffectsJSON::class.java
        ).data

        val effectMap = processEffectData(effectJSONS, crystalJSONS, crystalsEffects)

        allCommands = processData(commandJSONS, effectMap)
        effects = effectJSONS.map { eff ->
            Effect(
                eff.id,
                eff.description,
                effectMap.filter { em -> em.value.refs.any { ref -> ref.effect.id == eff.id } }
                    .map { it.key })
        }

        return root
    }

    private fun processData(
        commandsJSON: List<CommandJSON>,
        crystalEffectMap: Map<String, CrystalEffect>
    ): List<Command> {

        val commandMap = commandsJSON.map { it.id to Command(it.id, it.name) }.toMap()

        commandsJSON.forEach { cmd ->
            val melding = cmd.meldingJSON.map {
                CommandMelding(
                    commandMap[it.firstItemId],
                    commandMap[it.secondItemId],
                    crystalEffectMap[it.crystalGroup],
                    it.percentage
                )
            }
            commandMap[cmd.id]?.melding?.addAll(melding)
        }

        return commandMap.map { (_, value) -> value }

    }

    private fun processEffectData(
        effectsJSON: List<EffectJSON>,
        crystalJSONS: List<CrystalJSON>,
        crystalsEffects: List<CrystalEffectJSON>
    ): Map<String, CrystalEffect> {
        val crystalMap = crystalJSONS.map { it.id to it }.toMap()
        val effectMap = effectsJSON.map { it.id to it }.toMap()

        return crystalsEffects.map { crystalEffect ->
            crystalEffect.id to
                    CrystalEffect(
                        crystalEffect.id,
                        crystalEffect.refs.map { crystalEffectRef ->
                            CrystalEffectRefs(
                                fromJSON(crystalMap[crystalEffectRef.cristalId]),
                                fromJSON(effectMap[crystalEffectRef.effectId])
                            )
                        }
                    )
        }.toMap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        birthBySleepCollectionAdapter = BirthBySleepCollectionAdapter(this, effects, allCommands)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = birthBySleepCollectionAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = context?.getString(R.string.khbbs_tabs_all)
                }
                1 -> {
                    tab.text = context?.getString(R.string.khbbs_tabs_effect)
                }
                2 -> {
                    tab.text = context?.getString(R.string.khbbs_tabs_command)
                }
            }

        }.attach()
    }

    private fun readFile(rID: Int): String {
        val iS = resources.openRawResource(rID)
        val size: Int = iS.available()
        val buffer = ByteArray(size)
        iS.read(buffer)
        iS.close()
        return String(buffer, Charset.defaultCharset())
    }
}

class BirthBySleepCollectionAdapter(
    fragment: Fragment,
    effects: List<Effect>,
    commands: List<Command>
) : FragmentStateAdapter(fragment) {

    private val effectsModel = effects
    private val commandsModel = commands

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAllPageFragment(commandsModel, effectsModel)
            1 -> TabPerCommandPageFragment()
            else -> TabPerEffectPageFragment(commandsModel, effectsModel)
        }
    }
}

class TabPerCommandPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_khbbs_tab_per_command, container, false)
    }
}

class TabPerEffectPageFragment(
    commands: List<Command>,
    effects: List<Effect>
) : Fragment() {

    private val commandsModel = commands
    private val effectsModel = effects

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_khbbs_tab_per_effect, container, false)
    }
}