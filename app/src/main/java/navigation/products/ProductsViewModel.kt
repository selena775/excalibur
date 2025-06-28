package navigation.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class ProductsViewModel :
    ViewModel() {

    init {
        Log.e("Selena", "INITIALISE ProductsViewModel")
    }

    override fun onCleared() {
        Log.e("Selena", "ONCLEARED ProductsViewModel")
        super.onCleared()
    }

    val productsUiState: StateFlow<List<String>> =
        getStreamProducts()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


    fun getStreamProducts() = flow {
        delay(1000)
        emit(
            listOf(
                "Apple",
                "Banana",
                "Cherry",
                "Mango",
                "Orange",
                "Pineapple",
                "Strawberry",
                "Watermelon"
            )
        )
    }
}