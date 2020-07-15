package acdc.kingdomhearts.ui.khbbs

import acdc.kingdomhearts.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import java.nio.charset.Charset
import com.google.android.material.tabs.TabLayoutMediator


class BirthBySleepFragment : Fragment() {

    private lateinit var birthBySleepViewModel: BirthBySleepViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var birthBySleepCollectionAdapter: BirthBySleepCollectionAdapter
    private lateinit var effects: EffectsModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        birthBySleepViewModel = ViewModelProvider(this).get(BirthBySleepViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_khbbs, container, false)

        effects =
            Gson().fromJson<EffectsModel>(readFile(R.raw.khbbs_effects), EffectsModel::class.java)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        birthBySleepCollectionAdapter = BirthBySleepCollectionAdapter(this)
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
        val iS = resources.openRawResource(rID);
        val size: Int = iS.available()
        val buffer = ByteArray(size)
        iS.read(buffer)
        iS.close()
        return String(buffer, Charset.defaultCharset())
    }
}

class BirthBySleepCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabAllPageFragment()
            1 -> TabPerCommandPageFragment()
            else -> TabPerEffectPageFragment()
        }
    }
}

class TabAllPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_khbbs_tab_all, container, false)
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

class TabPerEffectPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_khbbs_tab_per_effect, container, false)
    }
}