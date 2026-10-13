package me.ihqqq.bt2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.ihqqq.bt2.ui.theme.BT2Theme

class Student(
    val name: String,
    val email: String?,
    val phone: String?
)

val students = listOf(
    Student("Nguyễn Văn An", "an@sv.edu.vn", "0901 234 567"),
    Student("Trần Thị Bình", null, "0912 888 999"),
    Student("Lê Minh Chi", "chi@sv.edu.vn", null),
    Student("Phạm Quốc Dũng", null, null),
    Student("Võ Thanh Em", "em@sv.edu.vn", "0933 111 222")
)

private val NavyBlue = Color(0xFF2C4373)
private val ScreenBg = Color(0xFFF3F4F8)
private val GrayText = Color(0xFF555B66)
private val RedText = Color(0xFFC0392B)
private val SummaryBg = Color(0xFFE6ECF5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BT2Theme {
                StudentListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Danh sách sinh viên",
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
            for (student in students) {
                StudentCard(student)
                Spacer(modifier = Modifier.height(12.dp)) // SizedBox
            }

            var missingEmail = 0
            for (student in students) {
                if (student.email == null) {
                    missingEmail++
                }
            }

            SummaryBar(total = students.size, missingEmail = missingEmail)
        }
    }
}

@Composable
fun StudentCard(student: Student) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = student.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1D26)
            )
            Spacer(modifier = Modifier.height(6.dp))
            InfoRow(label = "Email: ", value = student.email)
            Spacer(modifier = Modifier.height(2.dp))
            InfoRow(label = "SĐT: ", value = student.phone)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String?) {
    val isMissing = value == null
    val display = value ?: "Chưa cập nhật"

    Row {
        Text(text = label, fontSize = 14.sp, color = GrayText)
        Text(
            text = display,
            fontSize = 14.sp,
            color = if (isMissing) RedText else GrayText,
            fontStyle = if (isMissing) FontStyle.Italic else FontStyle.Normal
        )
    }
}

@Composable
fun SummaryBar(total: Int, missingEmail: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SummaryBg)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(text = "Tổng: ", fontSize = 14.sp, color = NavyBlue)
            Text(
                text = "$total sinh viên",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = NavyBlue
            )
        }
        Row {
            Text(text = "Thiếu email: ", fontSize = 14.sp, color = NavyBlue)
            Text(
                text = "$missingEmail",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = RedText
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentListScreenPreview() {
    BT2Theme {
        StudentListScreen()
    }
}