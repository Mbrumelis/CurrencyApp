package com.example.currencyapp.presentation.currencylist.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.domain.model.CurrencyDomainModel
import com.example.currencyapp.R
import kotlinx.android.synthetic.main.currency_list_grey.view.*
import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyAdapter(val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CurrencyViewHolder>() {
    var data = ArrayList<CurrencyDomainModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.currency_list_grey, parent, false)
        return CurrencyViewHolder(
            view
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = data.get(position)
        holder.bind(item, itemClickListener)
    }
}

class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(currency: CurrencyDomainModel, clickListener: OnItemClickListener) {
        with(itemView) {
            list_title_black.text = currency.name
            list_fulltitle_darkgray.text = currency.fullName

            list_fullrate_black.text =
                BigDecimal(currency.fullRate.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
                    .toString()
            list_rate_darkgray.text = currency.rateString
        }
        itemView.setOnClickListener {
            clickListener.onItemClicked(currency)
        }
    }
}

interface OnItemClickListener {
    fun onItemClicked(currency: CurrencyDomainModel)
}

