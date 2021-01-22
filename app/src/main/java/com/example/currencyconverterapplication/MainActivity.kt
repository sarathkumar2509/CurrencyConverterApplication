package com.example.currencyconverterapplication

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverterapplication.databinding.ActivityMainBinding
import com.example.currencyconverterapplication.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConvert.setOnClickListener {


            if(binding.etFrom.text?.isEmpty() == true){
                Log.d("Value", binding.etFrom.text.toString())
                binding.progressBar.isVisible = false
                binding.tvResult.setTextColor(Color.RED)
                binding.tvResult.setText("Enter Amount")
            }else{
                Log.d("Value", binding.etFrom.text.toString())
                viewModel.convert(
                    binding.etFrom.text.toString(),
                    binding.spFromCurrency.selectedItem.toString(),
                    binding.spToCurrency.selectedItem.toString()
                )
            }

        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect{ event->
                when(event){
                    is MainViewModel.CurrencyEvent.Success ->{
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.BLACK)
                        binding.tvResult.text = event.resultText

                    }
                    is MainViewModel.CurrencyEvent.Failure ->{
                        binding.progressBar.isVisible = false
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.text = event.errorText
                    }
                    is MainViewModel.CurrencyEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }

            }
        }
    }
}