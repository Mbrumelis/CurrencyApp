package com.example.currencyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.network.Currency
import java.math.BigDecimal
import java.math.RoundingMode

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
        RecyclerView.ViewHolder(inflater.inflate(R.layout.currency_list_grey, parent, false)) {
    private var nameView: TextView? = null
    private var rateView: TextView? = null
    private var fullNameView: TextView? = null
    private var fullRateView: TextView? = null


    init {

        fullRateView = itemView.findViewById(R.id.list_fullrate_black)
        nameView = itemView.findViewById(R.id.list_title_black)
        rateView = itemView.findViewById(R.id.list_rate_darkgray)
        fullNameView = itemView.findViewById(R.id.list_fulltitle_darkgray)
    }

    fun bind(currency: Currency, clickListener: OnItemClickListener) {

        nameView?.text = currency.name
        fullNameView?.text = currency.fullName

        fullRateView?.text = BigDecimal(currency.fullRate.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        rateView?.text = currency.rateString


        itemView.setOnClickListener {
            clickListener.onItemClicked(currency)
        }
    }
}

interface OnItemClickListener{
    fun onItemClicked(currency: Currency)
}

