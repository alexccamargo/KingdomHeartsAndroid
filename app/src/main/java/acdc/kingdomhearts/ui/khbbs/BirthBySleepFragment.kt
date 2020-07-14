package acdc.kingdomhearts.ui.khbbs

import acdc.kingdomhearts.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import java.nio.charset.Charset

class BirthBySleepFragment : Fragment() {

    private lateinit var birthBySleepViewModel: BirthBySleepViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        birthBySleepViewModel =
                ViewModelProviders.of(this).get(BirthBySleepViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_khbbs, container, false)

        val effects = Gson().fromJson<EffectsModel>(readFile(R.raw.khbbs_effects), EffectsModel::class.java)

        val textView: TextView = root.findViewById(R.id.textView2)
        textView.text = effects.data.first().description

        return root
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