package com.dingshaoshuai.studycompose.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dingshaoshuai.studycompose.R
import com.dingshaoshuai.studycompose.TAG

/**
 * @author: Xiao Bo
 * @date: 2/8/2021
 */

@Composable
fun Text1() {
    Text(
        text = stringResource(id = R.string.hello_android),
        color = Color.Blue,
        modifier = Modifier.background(Color.Gray),
        fontSize = 30.sp,
        // 斜体
        fontStyle = FontStyle.Italic,
        // 粗体
        fontWeight = FontWeight.Bold,
        // 字体居中
        textAlign = TextAlign.Center
    )
}

@Composable
fun Text2() {
    Text(
        buildAnnotatedString {
            append("我是")
            withStyle(style = SpanStyle(color = Color.Blue, fontSize = 18.sp)) {
                append("一只")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    fontSize = 28.sp
                )
            ) {
                append("牛蛙")
            }
            append("!!!")
        }
    )
}

@Composable
fun Text3() {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 100.sp)) {
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("第一行\n")
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                    append("第二行\n")
                }
                append("第三行")
            }
        }
    )
}

@Composable
fun Text4() {
    Column {
        // overflow 溢出文字末尾 ...
        Text(text = "Hello  ".repeat(100), maxLines = 3, overflow = TextOverflow.Ellipsis)
        // 被包裹的 Text 可以长按选择文字
        SelectionContainer {
            Column {
                Text(text = "Android  ".repeat(100), maxLines = 3, overflow = TextOverflow.Ellipsis)
                // 禁止选中
                DisableSelection {
                    Text(
                        text = "Java  ".repeat(100),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(text = "Kotlin   ".repeat(100), maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun Text5() {
    // 获取点击的文字位置
    ClickableText(
        text = AnnotatedString("Hello Android", spanStyle = SpanStyle(fontSize = 50.sp)),
        onClick = {
            // 点击 e 输出 2
            Log.i(TAG, "Text5: $it")
        })
}

@Composable
fun Text6() {
    val annotatedText = buildAnnotatedString {
        append("发现一个好网站：")
        pushStringAnnotation("TAG_URL", "https://www.baidu.com")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp
            )
        ) {
            append("度娘")
        }
        // TODO 尝试注释这个也没问题，暂时不知道是起什么作用
        // pop()
    }
    ClickableText(text = annotatedText, onClick = { offset ->
        // TODO offset 我分别 -1 -2，不要同时修改，点击后打印结果也没问题，不知何用
        annotatedText.getStringAnnotations(tag = "TAG_URL", start = offset, end = offset)
            .firstOrNull()?.let {
                Log.i(TAG, "Text6: ${it.item}")
            }
    })
}

@Composable
fun Text7() {
    var text by remember { mutableStateOf("Hello") }

    Column {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )


        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = {
                Text("Label")
            }
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = {
                Row {
                    Text(text = "Label - 1")
                    Text(text = "Label - 2")
                }
            }
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") },
            maxLines = 2,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )

        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .padding(10.dp)
                .background(Color.Gray)
        )

        OutlinedTextField(
            value = text, onValueChange = { text = it },
            label = { Text("password") },
            visualTransformation = PasswordVisualTransformation(),
            // TODO 下面这行不加，也可以实现效果，先问号
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

    }


}

@Preview(
    // 显示背景
    showBackground = true,
    // 背景颜色
    backgroundColor = 0xFFFFFF,
    // 语言区域
    locale = "zh",
    // 显示系统界面
    showSystemUi = true,
)
@Composable
fun PreviewText() {
    Text7()
}