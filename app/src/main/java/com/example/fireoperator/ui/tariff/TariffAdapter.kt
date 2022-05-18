package com.example.fireoperator.ui.tariff

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.fireoperator.R
import com.example.fireoperator.databinding.ItemTarifBinding

class TariffAdapter( val listener: Listener) : RecyclerView.Adapter<TariffAdapter.TarifViewHolder>() {

    val list = ArrayList<Tariff>()

    class TarifViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemTarifBinding.bind(item)
        fun bind(tariff: Tariff, listener: Listener) = with(binding) {
            txtNameTarif.text = tariff.name
            txtCostTarif.text = tariff.cost.toString()
            txtDescriptionTarif.text = tariff.description
            itemView.setOnClickListener {
                listener.onClick(tariff)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarifViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarif, parent, false)
        return TarifViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarifViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addTariff (tariff: Tariff) {
        list.add(tariff)
        notifyDataSetChanged()
    }

    interface Listener {
        fun onClick (tariff: Tariff)
    }
}