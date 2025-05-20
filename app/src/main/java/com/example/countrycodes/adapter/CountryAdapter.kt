package com.example.countrycodes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countrycodes.R
import com.example.countrycodes.databinding.ItemCountryBinding
import com.example.countrycodes.model.Country

class CountryAdapter(private var countryList: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

        class CountryViewHolder(val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
        val nameRegionText = "${country.name} (${country.region})"

        holder.itemView.findViewById<TextView>(R.id.nameRegion).text = nameRegionText
        holder.itemView.findViewById<TextView>(R.id.code).text = country.code
        holder.itemView.findViewById<TextView>(R.id.capital).text = country.capital
    }

    override fun getItemCount(): Int = countryList.size

    fun updateData(newList: List<Country>) {
        countryList = newList
        notifyDataSetChanged()
    }
}



