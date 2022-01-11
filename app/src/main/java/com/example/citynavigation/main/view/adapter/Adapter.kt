package com.example.citynavigation.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citynavigation.R
import com.example.citynavigation.api.CityItem
import com.example.citynavigation.main.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.item_city.view.*

open class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>(), Filterable {
    var cityList: ArrayList<CityItem> = ArrayList()
    var cityFiltered: ArrayList<CityItem> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    fun addData(list: List<CityItem>) {
        cityList = list as ArrayList<CityItem>
        cityFiltered = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cityTitle = holder.itemView.item_title
        val cityImage = holder.itemView.item_image
        val cityPopulation = holder.itemView.item_population

        val city = cityFiltered[position]
        cityPopulation.text = city.populations[0].population
        cityTitle.text = city.name
        Glide.with(holder.itemView).load(city.image).into(cityImage)

        holder.itemView.layout_item.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(city.id)
            Navigation.findNavController(cityTitle).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return cityFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    cityFiltered = cityList
                } else {
                    val resultList = ArrayList<CityItem>()
                    for (city in cityList) {
                        if (city.name.lowercase().contains(charSearch.lowercase())
                        ) {
                            resultList.add(city)
                        }
                    }
                    cityFiltered = resultList

                }
                val filterResults = FilterResults()
                filterResults.values = cityFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
                cityFiltered = result?.values as ArrayList<CityItem>
                notifyDataSetChanged()
            }
        }
    }

}
