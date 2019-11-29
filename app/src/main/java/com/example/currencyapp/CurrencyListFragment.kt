package com.example.currencyapp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.currencyapp.databinding.CurrencyListFragmentBinding
import com.example.currencyapp.network.Currency


class CurrencyListFragment : Fragment(), OnItemClickListener {


    private lateinit var binding: CurrencyListFragmentBinding
    private lateinit var currencyViewModel: CurrencyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.currency_list_fragment,
            container,
            false
        )

        currencyViewModel = ViewModelProviders.of(this).get(CurrencyListViewModel::class.java)

        binding.currencyViewModel= currencyViewModel

        val adapter = CurrencyAdapter(this)
        binding.currencyList.adapter =  adapter

        currencyViewModel.currencyList.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.data = it
            }
        })

        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onItemClicked(currency: Currency) {
        currencyViewModel.baseCurrency.value = currency.name
        currencyViewModel.getCurrencyList()
    }


}
