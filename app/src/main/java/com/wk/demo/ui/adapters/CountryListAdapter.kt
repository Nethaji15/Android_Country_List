package com.wk.demo.ui.adapters

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.wk.demo.R
import com.wk.demo.data.remote.reqres.CountriesItem
import com.wk.demo.decoder.SvgSoftwareLayerSetter
import kotlinx.android.synthetic.main.country_items.view.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

/**
 * class to provide the grid view of country list
 */
class CountryListAdapter(
    private var context: Context,
    private var userList: List<CountriesItem>,
    val clickListener: (CountriesItem) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {


    var membersList = ArrayList<CountriesItem>()
    var chatList = ArrayList<CountriesItem>()

    private val set = ConstraintSet()

    class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        chatList = userList as ArrayList<CountriesItem>
        membersList = userList as ArrayList<CountriesItem>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        val countryListView =
            LayoutInflater.from(parent.context).inflate(R.layout.country_items, parent, false)
        val sch = CountryHolder(countryListView)
        return sch
    }

    override fun getItemCount(): Int {
        return membersList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {

            val item = membersList[position]

            // Display the flag on layout using glide library
            Glide.with(context)
                .`as`(PictureDrawable::class.java)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.warning)
                .listener(SvgSoftwareLayerSetter())
                .load(Uri.parse(item.flag))
                .priority(Priority.IMMEDIATE)
                .into(holder.itemView.imgSource)
            // click event returning selected item for navigate to detailed page
            holder.itemView.setOnClickListener {
                clickListener(item)
            }


            holder.itemView.name.text = item.name


        } catch (e: Exception) {
            Timber.e("Org member error-" + e.message)
        }
    }

    /**
     * function for apply filter on recyclerview
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                membersList = if (charSearch.isEmpty()) {
                    userList as ArrayList<CountriesItem>
                } else {
                    val resultList = ArrayList<CountriesItem>()
                    for (row in chatList) {
                        if (row.name?.toLowerCase(Locale.ROOT)
                                ?.contains(charSearch.toLowerCase(Locale.ROOT))!!
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = membersList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                membersList = results?.values as ArrayList<CountriesItem>
                notifyDataSetChanged()
            }

        }
    }

}