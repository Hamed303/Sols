package com.sols.roleplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF080812)
private val Panel = Color(0xFF141426)
private val Purple = Color(0xFF9B30FF)
private val Pink = Color(0xFFE040FB)
private val Muted = Color(0xFFAAA8C2)
private const val SERVER = "5.57.32.92:7777"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme(colorScheme = darkColorScheme(primary = Purple, background = Bg, surface = Panel)) { LauncherApp() } }
    }
}

@Composable
private fun LauncherApp() {
    var page by remember { mutableStateOf("home") }
    var quality by remember { mutableStateOf("بالا") }
    var resolution by remember { mutableStateOf("1280×720") }
    var fps by remember { mutableFloatStateOf(60f) }
    var download by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("برای دانلود دیتا، ابتدا باید آدرس امن فایل‌ها تنظیم شود.") }

    Column(Modifier.fillMaxSize().background(Bg)) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(42.dp).background(Brush.linearGradient(listOf(Purple, Pink)), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Text("S", color = Color.White, fontSize = 29.sp, fontWeight = FontWeight.Black)
            }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text("SoLs", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                Text("ROLEPLAY  •  ANDROID", color = Muted, fontSize = 9.sp, letterSpacing = 2.sp)
            }
            IconButton(onClick = { page = "settings" }) { Icon(Icons.Default.Settings, "تنظیمات", tint = Color.White) }
        }
        when (page) {
            "settings" -> SettingsPage(quality, { quality = it }, resolution, { resolution = it }, fps, { fps = it }, { page = "home" })
            "account" -> AccountPage(username, { username = it }, password, { password = it }, { page = "home" }, { message = "صفحه نمایشی است؛ برای ورود واقعی باید API امن حساب کاربری راه‌اندازی شود." })
            "download" -> DownloadPage(download, { download = it }, message, { message = it }, { page = "home" })
            else -> HomePage(message, { page = "download" }, { page = "account" }, { page = "settings" })
        }
        Spacer(Modifier.weight(1f))
        NavigationBar(containerColor = Color(0xFF0D0D1A), contentColor = Purple) {
            NavigationBarItem(selected = page == "home", onClick = { page = "home" }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("خانه") })
            NavigationBarItem(selected = page == "download", onClick = { page = "download" }, icon = { Icon(Icons.Default.Download, null) }, label = { Text("دیتا") })
            NavigationBarItem(selected = page == "settings", onClick = { page = "settings" }, icon = { Icon(Icons.Default.Tune, null) }, label = { Text("تنظیمات") })
            NavigationBarItem(selected = page == "account", onClick = { page = "account" }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("حساب") })
        }
    }
}

@Composable
private fun HomePage(message: String, onDownload: () -> Unit, onAccount: () -> Unit, onSettings: () -> Unit) {
    Column(Modifier.fillMaxWidth().weight(1f, fill = false).verticalScroll(rememberScrollState()).padding(horizontal = 18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(Modifier.fillMaxWidth().height(180.dp).background(Brush.linearGradient(listOf(Color(0xFF31105D), Color(0xFF111126), Color(0xFF4C087A))), RoundedCornerShape(24.dp)).padding(20.dp)) {
            Column(Modifier.align(Alignment.CenterStart)) {
                Text("ENTER THE CITY", color = Pink, fontSize = 10.sp, letterSpacing = 3.sp)
                Text("SoLs RolePlay", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                Text("دنیای نقش‌آفرینی تو از اینجا شروع می‌شود", color = Color(0xFFE4DDF4), fontSize = 12.sp)
            }
            Text("S", Modifier.align(Alignment.CenterEnd), color = Color.White.copy(alpha = .12f), fontSize = 100.sp, fontWeight = FontWeight.Black)
        }
        Button(onClick = { /* نیازمند کلاینت بازی سازگار */ }, modifier = Modifier.fillMaxWidth().height(58.dp), shape = RoundedCornerShape(18.dp), colors = ButtonDefaults.buttonColors(containerColor = Purple)) {
            Icon(Icons.Default.PlayArrow, null); Spacer(Modifier.width(8.dp)); Text("PLAY", fontSize = 19.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
        }
        Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) { Box(Modifier.size(9.dp).background(Color(0xFFFFB74D), CircleShape)); Spacer(Modifier.width(8.dp)); Text("وضعیت سرور", color = Color.White, fontWeight = FontWeight.Bold); Spacer(Modifier.weight(1f)); Text("بررسی‌نشده", color = Color(0xFFFFB74D), fontSize = 12.sp) }
                Text(SERVER, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text("وضعیت آنلاین و تعداد بازیکنان هنوز به سرویس وضعیت متصل نیست.", color = Muted, fontSize = 11.sp)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickAction("دانلود دیتا", Icons.Default.Download, onDownload, Modifier.weight(1f))
            QuickAction("حساب کاربری", Icons.Default.Person, onAccount, Modifier.weight(1f))
            QuickAction("گرافیک", Icons.Default.Tune, onSettings, Modifier.weight(1f))
        }
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF171126)), shape = RoundedCornerShape(16.dp)) {
            Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Info, null, tint = Pink); Spacer(Modifier.width(10.dp)); Text(message, color = Muted, fontSize = 11.sp) }
        }
    }
}

@Composable
private fun QuickAction(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier.clickable { onClick() }, colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.fillMaxWidth().padding(vertical = 17.dp, horizontal = 4.dp), horizontalAlignment = Alignment.CenterHorizontally) { Icon(icon, null, tint = Pink); Spacer(Modifier.height(8.dp)); Text(title, color = Color.White, fontSize = 11.sp, textAlign = TextAlign.Center) }
    }
}

@Composable
private fun SettingsPage(quality: String, setQuality: (String) -> Unit, resolution: String, setResolution: (String) -> Unit, fps: Float, setFps: (Float) -> Unit, back: () -> Unit) {
    Column(Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()).padding(18.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("تنظیمات گرافیکی", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("کیفیت گرافیک", color = Muted)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("پایین", "متوسط", "بالا", "خیلی بالا").forEach { value -> FilterChip(selected = quality == value, onClick = { setQuality(value) }, label = { Text(value) }) } }
        Text("رزولوشن", color = Muted)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("800×600", "1280×720", "1920×1080").forEach { value -> FilterChip(selected = resolution == value, onClick = { setResolution(value) }, label = { Text(value, fontSize = 10.sp) }) } }
        Text("محدودیت FPS: ${fps.toInt()}", color = Color.White)
        Slider(value = fps, onValueChange = setFps, valueRange = 30f..120f, steps = 2)
        Text("این گزینه‌ها فعلاً در لانچر ذخیره می‌شوند؛ اعمال واقعی آن‌ها به پشتیبانی کلاینت بازی وابسته است.", color = Muted, fontSize = 12.sp)
        Button(onClick = back, modifier = Modifier.fillMaxWidth()) { Text("ذخیره و بازگشت") }
    }
}

@Composable
private fun AccountPage(username: String, setUsername: (String) -> Unit, password: String, setPassword: (String) -> Unit, back: () -> Unit, info: () -> Unit) {
    Column(Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("ورود / ثبت‌نام", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Text("برای فعال شدن حساب کاربری باید API امن سرور ساخته شود.", color = Muted, fontSize = 12.sp)
        OutlinedTextField(value = username, onValueChange = setUsername, modifier = Modifier.fillMaxWidth(), label = { Text("نام کاربری یا ایمیل") }, singleLine = true)
        OutlinedTextField(value = password, onValueChange = setPassword, modifier = Modifier.fillMaxWidth(), label = { Text("رمز عبور") }, singleLine = true)
        Button(onClick = info, modifier = Modifier.fillMaxWidth()) { Text("ورود") }
        OutlinedButton(onClick = info, modifier = Modifier.fillMaxWidth()) { Text("ساخت حساب جدید") }
        Text("در نسخه فعلی اطلاعات ورود به هیچ سروری ارسال نمی‌شود.", color = Muted, fontSize = 11.sp)
        TextButton(onClick = back) { Text("بازگشت") }
    }
}

@Composable
private fun DownloadPage(active: Boolean, setActive: (Boolean) -> Unit, message: String, setMessage: (String) -> Unit, back: () -> Unit) {
    Column(Modifier.weight(1f, fill = false).verticalScroll(rememberScrollState()).padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("دانلود و به‌روزرسانی دیتا", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(Icons.Default.CloudDownload, null, tint = Pink, modifier = Modifier.size(38.dp))
                Text("فایل‌های بازی", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                LinearProgressIndicator(progress = { if (active) 0.05f else 0f }, modifier = Modifier.fillMaxWidth(), color = Purple)
                Text(if (active) "در انتظار تنظیم منبع دانلود امن…" else "منبع دانلود تنظیم نشده", color = Muted, fontSize = 12.sp)
                Button(onClick = { setActive(true); setMessage("دانلود واقعی فعال نیست: لینک HTTPS و فایل manifest هنوز تعریف نشده‌اند.") }, modifier = Modifier.fillMaxWidth()) { Text("بررسی دیتا") }
            }
        }
        Text("برای فعال‌سازی لازم است لینک HTTPS فایل‌ها و یک manifest شامل نسخه، حجم و SHA-256 فراهم شود. هیچ فایل بازی در این پروژه گنجانده نشده است.", color = Muted, fontSize = 12.sp)
        TextButton(onClick = back) { Text("بازگشت") }
    }
}
