package navigation.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class OrdersViewModel :
    ViewModel() {

    init {
        Log.e("Selena", "INITIALISE OrdersViewModel")
    }

    override fun onCleared() {
        Log.e("Selena", "ONCLEARED OrdersViewModel")
        super.onCleared()
    }

    val ordersUiState: StateFlow<List<String>> =
        getStreamOrders()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    fun getStreamOrders() = flow {
        delay(2000)
        emit(
            listOf(
                "456677",
                "1234555",
                "1234555",
                "53562",
                "3453636",
                "2525266",
                "333333",
                "577777",

            )
        )
    }
}