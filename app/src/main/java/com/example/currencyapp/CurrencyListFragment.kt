package com.example.currencyapp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.currencyapp.databinding.CurrencyListFragmentBinding


class CurrencyListFragment : Fragment() {


    private lateinit var binding: CurrencyListFragmentBinding
    private lateinit var viewModel: CurrencyListViewModel

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

        viewModel = ViewModelProviders.of(this).get(CurrencyListViewModel::class.java)

        binding.currencyViewModel= viewModel

        binding.setLifecycleOwner(this)


        return binding.root
    }



}
