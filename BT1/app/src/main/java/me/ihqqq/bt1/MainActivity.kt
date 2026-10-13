package me.ihqqq.bt1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

private fun calculate(num1: String, num2: String, op: String?): String {
    if (op == null) return ""
    val a = num1.trim().toDoubleOrNull()
    val b = num2.trim().toDoubleOrNull()
    if (a == null || b == null) return "Vui lòng nhập số hợp lệ"

    val value = when (op) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> {
            if (b == 0.0) return "Không thể chia cho 0"
            a / b
        }
        else -> return ""
    }
    return if (value == Math.floor(value) && !value.isInfinite()) {
        value.toLong().toString()
    } else {
        String.format("%.4f", value).trimEnd('0').trimEnd('.')
    }
}

@Composable
fun CalculatorScreen() {
    var num1 by rememberSaveable { mutableStateOf("") }
    var num2 by rememberSaveable { mutableStateOf("") }
    var selectedOp by rememberSaveable { mutableStateOf<String?>(null) }

    val result = calculate(num1, num2, selectedOp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(120.dp))

        Text(
            text = "Thực hành 03",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(80.dp))

        OutlinedTextField(
            value = num1,
            onValueChange = { num1 = it },
            placeholder = { Text("Nhập số thứ nhất") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OpButton("+", Color(0xFFE05252), selectedOp == "+") { selectedOp = "+" }
            OpButton("-", Color(0xFFDDAA48), selectedOp == "-") { selectedOp = "-" }
            OpButton("*", Color(0xFF5B3CDD), selectedOp == "*") { selectedOp = "*" }
            OpButton("/", Color(0xFF1E1E1E), selectedOp == "/") { selectedOp = "/" }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = num2,
            onValueChange = { num2 = it },
            placeholder = { Text("Nhập số thứ hai") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Kết quả: $result", fontSize = 16.sp)
    }
}

@Composable
fun OpButton(
    label: String,
    color: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color)
            .then(
                if (selected) Modifier.border(
                    BorderStroke(3.dp, Color.Black),
                    RoundedCornerShape(10.dp)
                ) else Modifier
            )
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}