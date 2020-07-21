package acdc.kingdomhearts.ui.khbbs

import acdc.kingdomhearts.R
import acdc.kingdomhearts.ui.khbbs.models.Command
import acdc.kingdomhearts.ui.khbbs.models.Effect
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TabAllPageFragment internal constructor(
    commands: List<Command>,
    effects: List<Effect>
) : Fragment(), TabAllRecyclerViewAdapter.ItemClickListener {

    private var adapter: TabAllRecyclerViewAdapter? = null
    private val allCommands = commands
    private val effectsModel = effects
    private var selectedEffect: Effect? = null
    private var spinnerData: ArrayList<Effect> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_khbbs_tab_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView: RecyclerView = view.findViewById(R.id.rvCommands)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TabAllRecyclerViewAdapter(context, allCommands)
        adapter!!.setClickListener(this)
        recyclerView.adapter = adapter

        spinnerData.add(Effect("", "No filter selected", ArrayList()))
        spinnerData.addAll(effectsModel)

        val spinner: Spinner = view.findViewById(R.id.effects_spinner)
        context?.let {
            val adapter = ArrayAdapter<Effect>(
                it,
                R.layout.fragment_khbbs_tab_all_dd,
                R.id.spinner_effect_name,
                spinnerData
            )
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinner.setSelection(0)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                recyclerView.adapter.apply {
                    val adp = adapter as TabAllRecyclerViewAdapter
                    if (position == 0) {
                        adp.commands = allCommands
                    } else {
                        selectedEffect = spinnerData[position]
                        adp.commands = allCommands.filter { cmd ->
                            cmd.melding.any {
                                selectedEffect!!.crystalGroupsAssociated.contains(it.crystalGroup?.id)
                            }
                        }
                    }
                    adp.notifyDataSetChanged()
                }
            }
        }

    }

    override fun onItemClick(view: View?, position: Int) {
        Toast.makeText(
            context,
            "${adapter?.getItem(position).toString()} selected",
            Toast.LENGTH_SHORT
        ).show()
    }
}

class TabAllRecyclerViewAdapter internal constructor(
    context: Context?,
    data: List<Command>
) :
    RecyclerView.Adapter<TabAllRecyclerViewAdapter.ViewHolder>() {
    var commands = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = mInflater.inflate(R.layout.fragment_khbbs_tab_all_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val command = commands[position]
        holder.myTextView.text = command.name
    }

    // total number of rows
    override fun getItemCount(): Int {
        return commands.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView = itemView.findViewById(R.id.commandName)
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun getItem(id: Int): String {
        return commands[id].name
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}
