package navigation.orders

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class OrdersViewModel : ViewModel() {

    // MutableStateFlow to hold the list of orders
    private val _ordersList = MutableStateFlow<List<String>>(emptyList())
    val ordersList: StateFlow<List<String>> = _ordersList.asStateFlow() // Expose as immutable

    // MutableStateFlow for loading state
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // MutableStateFlow for error state
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        Log.e("Selena", "INITIALISE OrdersViewModel")
        // Start collecting the flow when the ViewModel is created
        loadOrders()
    }

    override fun onCleared() {
        Log.e("Selena", "ONCLEARED OrdersViewModel")
        super.onCleared()
    }

    // This method now simply returns the Flow, it doesn't collect or transform it.
    fun getStreamOrders() = flow {
        Log.d("OrdersViewModel", "Starting getStreamOrders flow (manual collect)...")
        delay(2000) // Simulate network/DB call
        val orders = listOf(
            "456677", "1234555", "1234555", "53562",
            "3453636", "2525266", "333333", "577777"
        )
        emit(orders)
        Log.d("OrdersViewModel", "Emitted orders (manual collect): $orders")
    }

    // Function to trigger the loading, called in init and potentially by UI
    fun loadOrders() {
        // Only load if not already loading or if there's an error from previous attempt
        if (!_isLoading.value && _ordersList.value.isNotEmpty() && _errorMessage.value == null) {
            // Already loaded successfully, no need to reload unless explicitly requested.
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Collect the flow's emissions and update the MutableStateFlows
                getStreamOrders().collect { fetchedList ->
                    _ordersList.value = fetchedList
                }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to load orders: ${e.localizedMessage}"
                Log.e("OrdersViewModel", "Error loading orders", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}