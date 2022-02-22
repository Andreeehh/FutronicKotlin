import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import databaseinterface.DBICard
import domain.Card
import java.util.ArrayList

@Composable
fun cardInterface (idCard: MutableState<String>, navItemState: MutableState<NavType>) {
    val cardDBI = DBICard()
    var cardList = cardDBI.getCardList()
    var aux = 0
    var index = 1
    var name by remember { mutableStateOf("Selecione uma Comanda") }

    @Composable()
    fun createIconButton(id: Int, offsetX: Int, offsetY: Int){
        for (card in cardList!!){
            if (card.id != id){
                continue
            }
            if (card.isInUse){
                IconButton(onClick = {
                    name = "Comanda em uso"
                    return@IconButton
                }, modifier = Modifier.offset(offsetX.dp,offsetY.dp)) {
                    Icon(
                        Icons.Default.CheckCircle,
                        tint = androidx.compose.ui.graphics.Color.Red)
                }
                return
            }
        }
        IconButton(onClick = {
            idCard.value = id.toString()
            navItemState.value = NavType.HOME
        }, modifier = Modifier.offset(offsetX.dp,offsetY.dp)) {
            Icon(
                Icons.Default.CheckCircle,
                tint = androidx.compose.ui.graphics.Color.Green)
        }
    }
    MaterialTheme {
        var offsetX = 0
        var offsetY = 0
        var offsetXText = 0
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .offset(0.dp, 50.dp)
                .wrapContentSize(Alignment.TopStart)) {
                while (index<=200){
                    createIconButton(index, offsetX, offsetY)
                    offsetXText = if (index < 10){
                        offsetX + 20
                    } else {
                        if (index < 100){
                            offsetX + 15
                        } else {
                            offsetX + 10
                        }
                    }
                    Text(index.toString(), modifier = Modifier.offset(offsetXText.dp,(offsetY + 30).dp))
                    offsetX += 38
                    if (index%30 == 0) {
                        offsetY += 70
                        offsetX = 0
                    }
                    index++
                }
                index = 1
                aux = 0
            }
            Text(name, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(0.dp, 600.dp))
        }
    }
}