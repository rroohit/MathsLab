package com.roh.mathslab.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roh.mathslab.R
import com.roh.mathslab.domain.model.ExpressionDetails
import com.roh.mathslab.ui.a_main.MainViewModel
import com.roh.mathslab.ui.util.theme.Purple40

private const val TAG = "HOME_SCREEN"

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(),
) {

    val listExpressions = mutableListOf<String>()

    val focusManager = LocalFocusManager.current

    val currentList by viewModel.currentList.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .semantics {
                contentDescription = "Home_Screen_Container"
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EditTextBox { expressions ->
            listExpressions.clear()
            listExpressions.addAll(expressions)
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.please_press_enter_to_separate_expressions),
            fontSize = 10.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .semantics {
                    contentDescription = "Get_result_button"
                },
            onClick = {
                focusManager.clearFocus()
                viewModel.startEvaluation(listExpressions)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40
            ),
            enabled = !viewModel.isProcessing.collectAsState().value
        ) {
            Text(text = "Get Result", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(currentList.sortedBy { it.index }) { expression ->
                ExpressionDetailItem(expression)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextBox(
    mathExpression: (List<String>) -> Unit
) {

    var text by rememberSaveable {
        mutableStateOf("")
    }

    val list = mutableListOf<String>()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            .semantics {
                contentDescription = "Edit_box_view"
            },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "   Add Expression", color = Color.Black)

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .semantics {
                    contentDescription = "Edit_Text_View"
                },
            value = text,
            onValueChange = {
                list.clear()
                list.addAll(it.split("\n"))
                mathExpression(list)
                text = it
            },
            maxLines = 8,
            label = {},
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.DarkGray,
                /*unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent*/
            ),
            textStyle = TextStyle.Default.copy(
                fontSize = 20.sp,
                letterSpacing = 3.sp,
                lineHeight = 40.sp,
                lineBreak = LineBreak(
                    strategy = LineBreak.Strategy.Simple,
                    strictness = LineBreak.Strictness.Strict,
                    wordBreak = LineBreak.WordBreak.Default
                )
            ),
        )
    }
}

@Composable
fun ExpressionDetailItem(expressionDetails: ExpressionDetails) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(9.dp)
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(text = "${expressionDetails.expression} = ${expressionDetails.result}", maxLines = 1)

    }
}