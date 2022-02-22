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
import databaseinterface.DBIHallTable
import domain.HallTable
import java.util.ArrayList

@Composable
fun hallTableInterface (idCard: MutableState<String>, navItemState: MutableState<NavType>) {
    val hallTableDBI = DBIHallTable()
    var hallTableList = hallTableDBI.getHallTableList()
    var aux = 0
    var i = 1
    var name by remember { mutableStateOf("Selecione uma Mesa") }

    @Composable()
    fun createIconButton(id: Int, offsetX: Int, offsetY: Int){
        for (table in hallTableList!!){
            if (table.id != id){
                continue
            }
            if (table.isInUse){
                IconButton(onClick = {
                    name = "Mesa em uso"
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
                while (i<=50){
                    createIconButton(i, offsetX, offsetY)
                    offsetXText = if (i < 10){
                        offsetX + 20
                    } else {
                        if (i < 100){
                            offsetX + 15
                        } else {
                            offsetX + 10
                        }
                    }
                    Text(i.toString(), modifier = Modifier.offset(offsetXText.dp,(offsetY + 30).dp))
                    offsetX += 38
                    if (i%30 == 0) {
                        offsetY += 70
                        offsetX = 0
                    }
                    i++
                }
                i = 1
                aux = 0
            }
            Text(name, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(0.dp, 600.dp))
        }
    }
}