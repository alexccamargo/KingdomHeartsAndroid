package acdc.kingdomhearts.ui.kh3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import acdc.kingdomhearts.R

class KingdomHearts3Fragment : Fragment() {

    private lateinit var kingdomHearts3ViewModel: KingdomHearts3ViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        kingdomHearts3ViewModel =
                ViewModelProviders.of(this).get(KingdomHearts3ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_kh3, container, false)
        val textView: TextView = root.findViewById(R.id.text_kh3)
        kingdomHearts3ViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}