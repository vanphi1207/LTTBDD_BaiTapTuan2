package me.ihqqq.bt4

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ihqqq.bt4.ui.theme.BT4Theme
import java.util.Locale

class Student(
    val name: String,
    val score: Double?
)

val students = listOf(
    Student("Nguyễn Văn An", 9.0),
    Student("Trần Thị Bình", 7.2),
    Student("Lê Minh Chi", null),
    Student("Phạm Quốc Dũng", 4.5),
    Student("Võ Thanh Em", 5.8)
)

private val NavyBlue = Color(0xFF2C4373)
private val ScreenBg = Color(0xFFF3F4F8)
private val GrayText = Color(0xFF6B7280)

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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BT4Theme {
                BangDiemScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BangDiemScreen() {
    var tongDiem = 0.0
    var soCoDiem = 0
    var soGioi = 0
    var soChuaCoDiem = 0
    var caoNhat: Double? = null

    for (student in students) {
        val score = student.score
        if (score == null) {
            soChuaCoDiem++
        } else {
            tongDiem += score
            soCoDiem++
            if (xepLoai(score) == "Giỏi") {
                soGioi++
            }
            if (caoNhat == null || score > caoNhat) {
                caoNhat = score
            }
        }
    }

    val diemTrungBinh = if (soCoDiem > 0) tongDiem / soCoDiem else 0.0

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bảng điểm lớp",
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
            SummaryCard(
                diemTrungBinh = diemTrungBinh,
                soGioi = soGioi,
                soChuaCoDiem = soChuaCoDiem,
                caoNhat = caoNhat
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "DANH SÁCH · ${students.size} SINH VIÊN",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                color = GrayText
            )

            Spacer(modifier = Modifier.height(8.dp))

            for (student in students) {
                StudentRow(student)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SummaryCard(
    diemTrungBinh: Double,
    soGioi: Int,
    soChuaCoDiem: Int,
    caoNhat: Double?
) {
    val caoNhatText = if (caoNhat != null) {
        String.format(Locale.US, "%.1f", caoNhat)
    } else {
        "—"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NavyBlue)
            .padding(16.dp)
    ) {
        Text(text = "Điểm trung bình lớp", fontSize = 13.sp, color = Color(0xFFC9D3E6))
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = String.format(Locale.US, "%.2f", diemTrungBinh),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.White.copy(alpha = 0.2f))
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            StatItem("Giỏi", "$soGioi", Modifier.weight(1f))
            StatItem("Chưa có điểm", "$soChuaCoDiem", Modifier.weight(1f))
            StatItem("Cao nhất", caoNhatText, Modifier.weight(1f))
        }
    }
}

@Composable
fun StatItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = label, fontSize = 12.sp, color = Color(0xFFC9D3E6))
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun StudentRow(student: Student) {
    val score = student.score

    val loai = if (score == null) "Chưa có điểm" else xepLoai(score)
    val mauLoai = if (score == null) GrayText else mauChu(loai)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = student.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1D26)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = loai,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = mauLoai
            )
        }

        if (score == null) {
            Text(text = "—", fontSize = 22.sp, color = Color(0xFFB0B5BF))
        } else {
            Text(
                text = String.format(Locale.US, "%.1f", score),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1D26)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BangDiemScreenPreview() {
    BT4Theme {
        BangDiemScreen()
    }
}