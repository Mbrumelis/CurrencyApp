package com.example.currencyapp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.currencyapp.databinding.CurrencyListFragmentBinding
import com.example.currencyapp.network.Currency
import kotlinx.android.synthetic.main.currency_list_fragment.*


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


        binding.listFullrateWhite.setText(currencyViewModel.baseCurrencyRate.toString())

        binding.listFullrateWhite.setOnEditorActionListener { v, actionId, event ->
            val newRate = binding.listFullrateWhite.getText().toString()
            if(actionId == EditorInfo.IME_ACTION_DONE && newRate != ""){
                currencyViewModel.setNewRate(newRate)
                true
            }
            else binding.listFullrateWhite.setText(currencyViewModel.baseCurrencyRate.toString())
            false
        }



        currencyViewModel.currencyList.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.data = it
            }
        })

        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onItemClicked(currency: Currency) {
        currencyViewModel.onListItemClick(currency)
    }


}
