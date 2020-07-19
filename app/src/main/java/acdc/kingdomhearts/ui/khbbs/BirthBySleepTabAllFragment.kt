package acdc.kingdomhearts.ui.khbbs

import acdc.kingdomhearts.R
import acdc.kingdomhearts.ui.khbbs.models.Command
import acdc.kingdomhearts.ui.khbbs.models.Effect
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TabAllPageFragment internal constructor(
    commands: List<Command>,
    effectsModel: List<Effect>
) : Fragment(), TabAllRecyclerViewAdapter.ItemClickListener {

    private var adapter: TabAllRecyclerViewAdapter? = null
    private var commandsModel = commands

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
        adapter = TabAllRecyclerViewAdapter(context, commandsModel)
        adapter!!.setClickListener(this)
        recyclerView.adapter = adapter
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
    private val mData = data
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
        val command = mData[position]
        holder.myTextView.text = command.name
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
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
        return mData[id].name
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}
