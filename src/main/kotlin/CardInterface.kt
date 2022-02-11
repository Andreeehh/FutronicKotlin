import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
fun cardInterface () {
    val cardDBI = DBICard()
    var cardList = cardDBI.getCardList()
    var aux = 0
    var i = 1
    fun colorCard (card: ArrayList<Card>, i: Int): androidx.compose.ui.graphics.Color {
        if (card[aux].id == i){
            if (card[aux].isInUse){
                if ((card.size - 1) != aux){
                    aux++
                }
                return androidx.compose.ui.graphics.Color.Yellow
            }
            else
                return androidx.compose.ui.graphics.Color.Green
        } else {
            return androidx.compose.ui.graphics.Color.Green
        }
    }
    fun clickOpen () {
        aux = 0
        i = 0
        return
    }
    fun clickClosed () {
        aux = 0
        i = 0
        return
    }

    fun defineFun (card: ArrayList<Card>, i: Int) {
        if (card[aux].id == i){
            if (card[aux].isInUse){
                if ((card.size - 1) != aux){
                    aux++
                }
                return clickClosed()
            }
            else
                return clickOpen()
        } else {
            return clickOpen()
        }
    }
    MaterialTheme {
        var offsetX = 0
        var offsetY = 0
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .offset(0.dp, 0.dp)
                .wrapContentSize(Alignment.TopStart)) {
                while (i<=200){
                    IconButton(onClick = { return@IconButton }, modifier = Modifier.offset(offsetX.dp,offsetY.dp)) {
                        Icon(
                            Icons.Default.CheckCircle,
                            tint = colorCard(cardList!!,i))
                    }
                    offsetX += 25
                    if (i%30 == 0) {
                        offsetY += 25
                        offsetX = 0
                    }
                    i++
                }
                i = 0
                aux = 0
            }
        }
    }
}