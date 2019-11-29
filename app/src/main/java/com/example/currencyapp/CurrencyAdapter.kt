package com.example.currencyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.network.Currency

class CurrencyAdapter(val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<CurrencyViewHolder>() {
    var data = ArrayList<Currency>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CurrencyViewHolder(layoutInflater, parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = data.get(position)
        holder.bind(item, itemClickListener)
    }
}

class CurrencyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.currency_item_view, parent, false)) {
    private var currencyNameView: TextView? = null
    private var currencyRateView: TextView? = null


    init {
        currencyNameView = itemView.findViewById(R.id.list_title)
        currencyRateView = itemView.findViewById(R.id.list_description)
    }

    fun bind(currency: Currency, clickListener: OnItemClickListener) {
        currencyNameView?.text = currency.name
        currencyRateView?.text = currency.rate.toString()

        itemView.setOnClickListener {
            clickListener.onItemClicked(currency)
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(currency: Currency)
}

