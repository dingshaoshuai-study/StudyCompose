package com.dingshaoshuai.studycompose.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dingshaoshuai.studycompose.TAG
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


/**
 * @author: Xiao Bo
 * @date: 3/8/2021
 */

@Composable
fun List1() {
    // 1. 无法滚动
    // 2. 直接加载所有 item（包括在屏幕中不可见的）
    Column {
        list.forEachIndexed { index, s ->
            Text(
                text = s,
                fontSize = 30.sp,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(if (index % 2 == 0) Color.Blue else Color.Red)
            )
        }
    }
}

@Composable
fun List2() {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        // 整体内容的内边距
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 20.dp),
        // 每个 item 之间的间距
        // 如果是 LazyRow，则应该是 horizontalArrangement
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Text(text = "我是头部")
        }
        items(200) {
            Text(text = "我是内容区域 $it")
        }
        item {
            Text(text = "我是底部")
        }
    }
}

@Composable
fun List3() {
    LazyColumn {
        items(list) {
            Text(text = it)
        }
    }
}

/**
 * 类似于联系人列表
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List4() {
    val grouped = list.groupBy { it.first() }
    LazyColumn {
        grouped.forEach { (initial, contactsForInitial) ->
            stickyHeader {
                Text(
                    text = initial.toString(),
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                )
            }
            items(contactsForInitial) {
                Text(
                    text = it,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    fontSize = 30.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List5() {
    // TODO 使用 GridCells.Adaptive 将每列设置为至少 128.dp 宽，为什么需要至少 128.dp
    // 此示例运行出来是 3 列
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 128.dp)) {
        itemsIndexed(list) { index, it ->
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(if (index % 2 == 0) Color.Blue else Color.Red),
                fontSize = 30.sp
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List6() {
    LazyVerticalGrid(cells = GridCells.Fixed(5)) {
        itemsIndexed(list2) { index, it ->
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(if (index % 2 == 0) Color.Blue else Color.Red),
                fontSize = 30.sp
            )
        }
    }
}

/**
 * 滚动位置监听
 */
@OptIn(ExperimentalAnimationApi::class) // AnimatedVisibility
@Composable
fun List7() {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            itemsIndexed(list2) { index, s ->
                Text(
                    text = s,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .background(if (index % 2 == 0) Color.Blue else Color.Red)
                )
            }
        }

        // Show the button if the first visible item is past
        // the first item. We use a remembered derived state to
        // minimize unnecessary compositions
        val showButton by remember {
            Log.i(TAG, "List7: derivedStateOf - 1")
            derivedStateOf {
                // 滑动的时候，会不断的触发此代码块
                Log.i(TAG, "List7: derivedStateOf - 2")
                listState.firstVisibleItemIndex > 0
            }
        }

        AnimatedVisibility(visible = showButton) {
            // showButton 一旦变为 true，则会走进此代码块
            Log.i(TAG, "List7: AnimatedVisibility")
            Text(
                text = "假装自己是一个按钮",
                modifier = Modifier.background(Color.Yellow)
            )
        }

    }
}

/**
 * 滚动位置监听
 */
@Composable
fun List8() {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        itemsIndexed(list2) { index, s ->
            Text(
                text = s,
                fontSize = 18.sp,
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .background(if (index % 2 == 0) Color.Blue else Color.Red)
            )
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> index > 0 }
            .distinctUntilChanged()
            .filter { it }
            .collect {
                // 移动到顶部
            }
    }
}

@Composable
fun List9() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        itemsIndexed(list2) { index, s ->
            Text(
                text = s,
                fontSize = 18.sp,
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
                    .background(if (index % 2 == 0) Color.Blue else Color.Red)
            )
        }
    }

    Button(
        onClick = {
            coroutineScope.launch {
                // Animate scroll to the first item
                listState.animateScrollToItem(index = 0)
            }
        }
    ) {

    }
}

private val list =
    listOf("A1", "B1", "B2", "C1", "C2", "C3", "D1", "D2", "D3", "D4", "E1", "E2", "E3", "E4", "E5")

private val list2 = mutableListOf<String>().apply {
    repeat(100) {
        add("item")
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewList() {
    List7()
}