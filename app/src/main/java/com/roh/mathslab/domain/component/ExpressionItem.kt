package com.roh.mathslab.domain.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roh.mathslab.domain.model.ExpressionDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpressionItem(
    key: Long,
    expressionDetails: List<ExpressionDetails>
) {

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp)
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(12.dp))
            .clickable {
                isExpanded = !isExpanded
            }
            .animateContentSize(
                animationSpec = tween(durationMillis = 300)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "${convertLongToDateTime(key)}  :: Total Expressions = ${expressionDetails.size}",
            fontSize = 14.sp,
            maxLines = 1

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${expressionDetails.firstOrNull()?.expression} = ${expressionDetails.firstOrNull()?.result}",
            fontSize = 20.sp,
            maxLines = 1

        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isExpanded) {
            for (i in 1..expressionDetails.lastIndex) {
                val details = expressionDetails[i]
                Text(
                    text = "${details.expression} = ${details.result}",
                    fontSize = 20.sp,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


fun convertLongToDateTime(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM hh:mm", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}