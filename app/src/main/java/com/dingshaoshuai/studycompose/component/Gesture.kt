package com.dingshaoshuai.studycompose.component

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.dingshaoshuai.studycompose.TAG
import kotlin.math.roundToInt

/**
 * @author: Xiao Bo
 * @date: 5/8/2021
 */

@Composable
fun Gesture1() {
    val count = remember {
        mutableStateOf(0)
    }
    Text(
        text = "点我哈哈 - ${count.value}",
        modifier = Modifier
            .clickable { count.value += 1 }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { Log.i(TAG, "Gesture1: onPress") },
                    onDoubleTap = { Log.i(TAG, "Gesture1: onDoubleTap") },
                    onLongPress = { Log.i(TAG, "Gesture1: onLongPress") },
                    onTap = { Log.i(TAG, "Gesture1: onTap") }
                )
            }
    )
}

@Composable
fun Gesture2() {
    Column(
        modifier = Modifier
            .background(Color.Blue)
            .size(100.dp)
            // 使内容过长可以滚动
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        repeat(100) {
            Text(text = "Item $it", modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun Gesture3() {
    val state = rememberScrollState()
    LaunchedEffect(key1 = Unit, block = {
        state.animateScrollTo(100)
    })

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .padding(horizontal = 8.dp)
            .verticalScroll(state)
    ) {

        repeat(100) {
            Text(text = "Item $it", modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun Gesture4() {
    var offset by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .size(150.dp)
            .scrollable(orientation = Orientation.Vertical, state = rememberScrollableState {
                offset += it
                it
            })
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = offset.toString())
    }
}

@Composable
fun Gesture5() {
    val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Scroll here", modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Gesture6() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Gesture7() {
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}

@Composable
fun Gesture8() {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier
            // apply other transformations like rotation and zoom
            // on the pizza slice emoji
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            // add transformable to listen to multitouch transformation events
            // after offset
            .transformable(state = state)
            .background(Color.Blue)
            .fillMaxSize()
    )
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewGesture() {
//    Box {
//        Column(modifier = Modifier.verticalScroll(state = rememberScrollState())) {
//            Gesture1()
//            Gesture2()
//            Gesture3()
//            Gesture4()
//        }
//    }
    Gesture8()
}