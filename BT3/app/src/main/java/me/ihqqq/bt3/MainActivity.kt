package me.ihqqq.bt3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ihqqq.bt3.ui.theme.BT3Theme

val diem = 8.5

val diemKiemTra = listOf(9.0, 7.0, 5.5, 3.0, 11.0)

private val NavyBlue = Color(0xFF2C4373)
private val ScreenBg = Color(0xFFF3F4F8)
private val GrayText = Color(0xFF6B7280)
private val DividerColor = Color(0xFFEDEEF2)

fun xepLoai(diem: Double): String {
    if (diem < 0 || diem > 10) {
        return "Không hợp lệ"
    } else if (diem >= 8.0) {
        return "Giỏi"
    } else if (diem >= 6.5) {
        return "Khá"
    } else if (diem >= 5.0) {
        return "Trung bình"
    } else {
        return "Yếu"
    }
}

fun mauChu(loai: String): Color {
    if (loai == "Giỏi") {
        return Color(0xFF2E7D32)
    } else if (loai == "Khá") {
        return Color(0xFF1F4E9C)
    } else if (loai == "Trung bình") {
        return Color(0xFFB7791F)
    } else if (loai == "Yếu") {
        return Color(0xFFC0392B)
    } else {
        return GrayText
    }
}

fun mauNen(loai: String): Color {
    if (loai == "Giỏi") {
        return Color(0xFFE3F4E3)
    } else if (loai == "Khá") {
        return Color(0xFFE3ECFA)
    } else if (loai == "Trung bình") {
        return Color(0xFFFCEBCB)
    } else if (loai == "Yếu") {
        return Color(0xFFFBE3E1)
    } else {
        return Color(0xFFEDEEF2)
    }
}

fun formatDiem(d: Double): String {
    if (d == Math.floor(d)) {
        return d.toInt().toString()
    } else {
        return d.toString()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BT3Theme {
                XepLoaiScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XepLoaiScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Xếp loại học lực",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = NavyBlue)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(ScreenBg)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ScoreCard(diem)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "KIỂM TRA THÊM",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                color = GrayText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                for (i in diemKiemTra.indices) {
                    CheckRow(diemKiemTra[i])
                    if (i < diemKiemTra.size - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(DividerColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreCard(d: Double) {
    val loai = xepLoai(d)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Điểm trung bình", fontSize = 14.sp, color = GrayText)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = formatDiem(d),
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1D26)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(mauNen(loai))
                .padding(vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = loai,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = mauChu(loai),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CheckRow(d: Double) {
    val loai = xepLoai(d)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Điểm ${formatDiem(d)}", fontSize = 15.sp, color = Color(0xFF1A1D26))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(mauNen(loai))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(
                text = loai,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = mauChu(loai)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun XepLoaiScreenPreview() {
    BT3Theme {
        XepLoaiScreen()
    }
}