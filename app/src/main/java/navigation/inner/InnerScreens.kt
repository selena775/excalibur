package navigation.inner

import android.os.Parcelable
import androidx.navigation3.runtime.NavKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed interface InnerScreen : NavKey, Parcelable {
    // NavKeys for the nested flow (could be inner sealed classes or separate objects)
    @Parcelize
    data class ProductDetailTabOverview(val productId: String) : InnerScreen
    @Parcelize
    data class ProductDetailTabReviews(val productId: String) : InnerScreen
    @Parcelize
    data class ProductDetailTabSpecs(val productId: String) : InnerScreen
}