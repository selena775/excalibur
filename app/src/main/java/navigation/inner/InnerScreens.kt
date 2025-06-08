package navigation.inner

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface InnerScreen : NavKey {
    // NavKeys for the nested flow (could be inner sealed classes or separate objects)
    @Serializable
    data class ProductDetailTabOverview(val productId: String) : InnerScreen
    @Serializable
    data class ProductDetailTabReviews(val productId: String) : InnerScreen
    @Serializable
    data class ProductDetailTabSpecs(val productId: String) : InnerScreen
}