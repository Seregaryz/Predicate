package com.example.predicate.fragment.statistic

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.predicate.R
import com.example.predicate.model.mistake.MistakesItem
import com.example.predicate.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_mistake.*
import kotlin.collections.ArrayList

class MistakesAdapter(
    private val deleteListener: (MistakesItem?, Int) -> Unit
) : RecyclerView.Adapter<MistakesAdapter.ViewHolder>() {

    private val items = ArrayList<MistakesItem?>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_mistake))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun setData(list: List<MistakesItem?>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(override val containerView: View) :
        LayoutContainer, RecyclerView.ViewHolder(containerView) {

        fun bind(mistake: MistakesItem?, position: Int) {
            tvMistake.text = mistake?.type?.get(0)
            btnDecline.setOnClickListener {
                deleteListener(mistake, position)
            }
        }
    }
}
