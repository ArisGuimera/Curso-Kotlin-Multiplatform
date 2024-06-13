import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import ktorbasic.composeapp.generated.resources.Res
import ktorbasic.composeapp.generated.resources.compose_multiplatform
import network.NetworkUtils.httpClient
import network.model.ApiResponse
import network.model.Hero

@Composable
@Preview
fun App() {
    MaterialTheme {
        var superheroName by remember { mutableStateOf("") }
        var superheroList by remember{ mutableStateOf<List<Hero>>(emptyList()) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                TextField(value = superheroName, onValueChange = { superheroName = it })
                Button(onClick = { getSuperheroList(superheroName){superheroList = it} }) {
                    Text("Load")
                }
            }
            //List
            LazyColumn {
                items(superheroList){ hero ->
                    Text(hero.name)
                }
            }
        }
    }
}

fun getSuperheroList(superheroName: String, onSuccessResponse: (List<Hero>) -> Unit) {
    if (superheroName.isBlank()) return
    val url =
        "https://www.superheroapi.com/api.php/79c99fda9894cf4017793cdb40721cb6/search/$superheroName"

    CoroutineScope(Dispatchers.IO).launch {
        val response = httpClient.get(url).body<ApiResponse>()
        onSuccessResponse(response.results)
    }
}
