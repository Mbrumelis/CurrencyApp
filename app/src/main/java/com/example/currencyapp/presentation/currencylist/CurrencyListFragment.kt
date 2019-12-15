package com.example.currencyapp.presentation.currencylist

import android.graphics.Rect
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.currencyapp.domain.model.CurrencyDomainModel
import com.example.currencyapp.presentation.currencylist.recyclerview.CurrencyAdapter
import com.example.currencyapp.presentation.currencylist.recyclerview.OnItemClickListener
import com.example.currencyapp.R
import com.example.currencyapp.databinding.CurrencyListFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class CurrencyListFragment() : Fragment(),
    OnItemClickListener, KodeinAware {

    override val kodein by kodein()
    private lateinit var binding: CurrencyListFragmentBinding
    private lateinit var currencyViewModel: CurrencyListViewModel

    private val viewModelFactory: CurrencyListViewModelFactory by instance()

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

        currencyViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrencyListViewModel::class.java)

        binding.currencyViewModel = currencyViewModel

        val adapter =
            CurrencyAdapter(this)
        binding.currencyList.adapter =  adapter



        binding.listFullrateWhite.setText(currencyViewModel.baseCurrencyObject.rate.toString())


        binding.rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            binding.rootView.getWindowVisibleDisplayFrame(rec)
            //finding screen height
            val screenHeight = binding.rootView.rootView.height
            //finding keyboard height
            val keypadHeight = screenHeight - rec.bottom
            if (keypadHeight < screenHeight * 0.15 && binding.listFullrateWhite.text.toString() == "") {
                binding.listFullrateWhite.setText(currencyViewModel.baseCurrencyObject.rate.toString())
            }
        }


        binding.listFullrateWhite.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.listFullrateWhite.text.toString() != ""){
                    currencyViewModel.setNewRate(s.toString())
                }
            }

        })





        currencyViewModel.currencyList.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.data = it
            }
        })


        binding.setLifecycleOwner(this)
        return binding.root
    }


    override fun onItemClicked(currency: CurrencyDomainModel) {
        currencyViewModel.onListItemClick(currency)
        binding.currencyList.smoothScrollToPosition(0)
    }


}
