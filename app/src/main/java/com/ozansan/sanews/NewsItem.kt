package com.ozansan.sanews

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.ozansan.sanews.data.models.News
import com.ozansan.sanews.util.DateTimeHelper
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun NewsItem(item: News) {
    val itemId = item.id?.toString() ?: "0"
    val baseUrl = "https://news.ycombinator.com/item?id="
    val targetUrl = baseUrl + itemId
    val uri: Uri =
        Uri.parse(targetUrl) // missing 'http://' will cause crashed
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, uri)
    Box(
        modifier = Modifier
            .clickable(enabled = true,
                onClick = {
                    startActivity(context, intent, null)
                })
            .background(color = Color(0xFFFAFAFA), shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        val timeString = DateTimeHelper.getDateString(item.time ?: 0)
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (item.by != null) {
                    Text(
                        item.by!!,
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "•",
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.width(4.dp))
                }
                if (item.url != null) {
                    val linkedUri: Uri = Uri.parse(item.url!!)
                    if (linkedUri.host != null) {
                        val editMode = LocalInspectionMode.current
                        if (!editMode) {
                            GlideImage(
                                imageModel = "https://icons.duckduckgo.com/ip3/${linkedUri.host!!}.ico",
                                modifier = Modifier.size(10.dp),
                                placeHolder = painterResource(id = R.drawable.ic_baseline_language_24),
                                error = painterResource(id = R.drawable.ic_baseline_language_24)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_language_24),
                                contentDescription = "",
                                modifier = Modifier.size(10.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                        Text(
                            linkedUri.host!!,
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "•",
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                        Spacer(Modifier.width(4.dp))
                    }
                    if (item.time != null) {
                        Text(
                            timeString,
                            style = MaterialTheme.typography.subtitle1,
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.width(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_thumb_up_24),
                        contentDescription = "Up Arrow",
                        modifier = Modifier.size(12.dp),
                        tint = Color(0xFF00AA00)
                    )
                    Text(
                        item.score?.toString() ?: "",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_thumb_down_24),
                        contentDescription = "Down Arrow",
                        modifier = Modifier.size(12.dp),
                        tint = Color(0xFFAA0000)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        item.title ?: "",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.h5,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (item.descendants != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_question_answer_24),
                                    contentDescription = "Comments",
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    item.descendants.toString(),
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )
                            }

                        }

                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun NewsItemPreview() {
    val newsItem = News(
        id = 1,
        title = "This is a really long text. This is a really long text, believe me, with \nmultiple lines!",
        url = "https://google.com",
        deleted = false,
        type = "story",
        by = "ozan-san",
        time = 1631443769,
        text = null,
        score = 126,
        descendants = 15
    )
    NewsItem(newsItem)
}