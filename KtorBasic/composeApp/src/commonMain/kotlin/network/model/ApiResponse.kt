package network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val results:List<Hero>,
    @SerialName("response")
    val ok:String
)