package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel: ViewModel() {
    private val _qauntity = MutableLiveData<Int>()
    val qauntity: LiveData<Int> = _qauntity

    private val _falvor = MutableLiveData<String>()
    val falvor: LiveData<String> = _falvor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val dateOptions = getPickedOptions()


    init {      //* first executes the resetorder fun *//
        resetOrder()
    }

    private fun updatePrice() {
        var calculatedPrice = (qauntity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOptions[0] == _date.value) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }

    fun setQuantity(numberCupCake:Int) {  //*function created *//
        _qauntity.value = numberCupCake
        updatePrice()
    }
    fun setFalvor(desiredFalvor:String){
        _falvor.value = desiredFalvor
    }
    fun setDate(pickDate:String){
        _date.value = pickDate
        updatePrice()
    }

    private fun getPickedOptions(): MutableList<String> {
        val options = mutableListOf<String>()
        val formatter  = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4){
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE,1)
        }
        return options
    }

    fun hasNoFalvorSet(): Boolean{
         return _falvor.value.isNullOrEmpty()
     }

    fun resetOrder() {
        _qauntity.value = 0
        _falvor.value = ""
        _date.value = dateOptions[0]
        _price.value = 0.0
    }

}

