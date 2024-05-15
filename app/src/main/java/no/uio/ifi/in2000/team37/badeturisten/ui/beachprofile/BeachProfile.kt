package no.uio.ifi.in2000.team37.badeturisten.ui.beachprofile

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import no.uio.ifi.in2000.team37.badeturisten.R
import java.util.Locale

@Composable
fun LottieAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fisker))
    val progress by animateLottieCompositionAsState(
        composition = composition, iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}

@Composable
fun rememberDirectionsBus(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "directions_bus",
            defaultWidth = 20.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(10.958f, 34.75f)
                quadToRelative(-0.791f, 0f, -1.354f, -0.562f)
                quadToRelative(-0.562f, -0.563f, -0.562f, -1.355f)
                verticalLineToRelative(-2.708f)
                quadTo(8f, 29.458f, 7.458f, 28.271f)
                quadToRelative(-0.541f, -1.188f, -0.541f, -2.563f)
                verticalLineTo(9.625f)
                quadToRelative(0f, -3.167f, 3.104f, -4.583f)
                quadToRelative(3.104f, -1.417f, 10.021f, -1.417f)
                quadToRelative(6.833f, 0f, 9.937f, 1.417f)
                quadToRelative(3.104f, 1.416f, 3.104f, 4.583f)
                verticalLineToRelative(16.083f)
                quadToRelative(0f, 1.375f, -0.541f, 2.563f)
                quadToRelative(-0.542f, 1.187f, -1.584f, 1.854f)
                verticalLineToRelative(2.708f)
                quadToRelative(0f, 0.792f, -0.562f, 1.355f)
                quadToRelative(-0.563f, 0.562f, -1.396f, 0.562f)
                quadToRelative(-0.792f, 0f, -1.333f, -0.562f)
                quadToRelative(-0.542f, -0.563f, -0.542f, -1.355f)
                verticalLineToRelative(-1.416f)
                horizontalLineToRelative(-14.25f)
                verticalLineToRelative(1.416f)
                quadToRelative(0f, 0.792f, -0.563f, 1.355f)
                quadToRelative(-0.562f, 0.562f, -1.354f, 0.562f)
                close()
                moveToRelative(9.084f, -26.333f)
                horizontalLineToRelative(10.25f)
                horizontalLineTo(9.708f)
                horizontalLineToRelative(10.334f)
                close()
                moveToRelative(7.041f, 11.875f)
                horizontalLineTo(9.542f)
                horizontalLineToRelative(20.916f)
                horizontalLineToRelative(-3.375f)
                close()
                moveTo(9.542f, 17.625f)
                horizontalLineToRelative(20.916f)
                verticalLineToRelative(-6.583f)
                horizontalLineTo(9.542f)
                close()
                moveToRelative(4.291f, 9.167f)
                quadToRelative(0.959f, 0f, 1.625f, -0.667f)
                quadToRelative(0.667f, -0.667f, 0.667f, -1.625f)
                reflectiveQuadToRelative(-0.667f, -1.625f)
                quadToRelative(-0.666f, -0.667f, -1.625f, -0.667f)
                quadToRelative(-0.958f, 0f, -1.625f, 0.667f)
                quadToRelative(-0.666f, 0.667f, -0.666f, 1.625f)
                reflectiveQuadToRelative(0.666f, 1.625f)
                quadToRelative(0.667f, 0.667f, 1.625f, 0.667f)
                close()
                moveToRelative(12.334f, 0f)
                quadToRelative(0.958f, 0f, 1.625f, -0.667f)
                quadToRelative(0.666f, -0.667f, 0.666f, -1.625f)
                reflectiveQuadToRelative(-0.666f, -1.625f)
                quadToRelative(-0.667f, -0.667f, -1.625f, -0.667f)
                quadToRelative(-0.959f, 0f, -1.625f, 0.667f)
                quadToRelative(-0.667f, 0.667f, -0.667f, 1.625f)
                reflectiveQuadToRelative(0.667f, 1.625f)
                quadToRelative(0.666f, 0.667f, 1.625f, 0.667f)
                close()
                moveTo(9.708f, 8.417f)
                horizontalLineToRelative(20.584f)
                quadToRelative(-0.917f, -1f, -3.5f, -1.563f)
                quadToRelative(-2.584f, -0.562f, -6.75f, -0.562f)
                quadToRelative(-4.709f, 0f, -7.167f, 0.541f)
                quadToRelative(-2.458f, 0.542f, -3.167f, 1.584f)
                close()
                moveToRelative(3.209f, 20.375f)
                horizontalLineToRelative(14.166f)
                quadToRelative(1.459f, 0f, 2.417f, -1.084f)
                quadToRelative(0.958f, -1.083f, 0.958f, -2.5f)
                verticalLineToRelative(-4.916f)
                horizontalLineTo(9.542f)
                verticalLineToRelative(4.916f)
                quadToRelative(0f, 1.417f, 0.958f, 2.5f)
                quadToRelative(0.958f, 1.084f, 2.417f, 1.084f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberDirectionsBoat(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "directions_boat",
            defaultWidth = 20.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6.708f, 31.458f)
                lineToRelative(-3f, -10.583f)
                quadToRelative(-0.166f, -0.5f, 0.063f, -1.042f)
                quadToRelative(0.229f, -0.541f, 0.771f, -0.708f)
                lineToRelative(2.333f, -0.792f)
                verticalLineToRelative(-8.75f)
                quadToRelative(0f, -1.083f, 0.771f, -1.854f)
                quadToRelative(0.771f, -0.771f, 1.896f, -0.771f)
                horizontalLineToRelative(5.875f)
                verticalLineTo(3.292f)
                quadToRelative(0f, -0.542f, 0.375f, -0.917f)
                reflectiveQuadTo(16.708f, 2f)
                horizontalLineToRelative(6.584f)
                quadToRelative(0.541f, 0f, 0.916f, 0.375f)
                reflectiveQuadToRelative(0.375f, 0.917f)
                verticalLineToRelative(3.666f)
                horizontalLineToRelative(5.875f)
                quadToRelative(1.084f, 0f, 1.875f, 0.771f)
                quadToRelative(0.792f, 0.771f, 0.792f, 1.854f)
                verticalLineToRelative(8.792f)
                lineToRelative(2.333f, 0.75f)
                quadToRelative(0.584f, 0.208f, 0.792f, 0.708f)
                quadToRelative(0.208f, 0.5f, 0.042f, 1f)
                lineToRelative(-3f, 10.625f)
                quadToRelative(-1.542f, -0.125f, -2.875f, -0.77f)
                quadToRelative(-1.334f, -0.646f, -2.5f, -1.563f)
                quadToRelative(-0.25f, -0.208f, -0.605f, -0.333f)
                quadToRelative(-0.354f, -0.125f, -0.687f, -0.125f)
                quadToRelative(-0.333f, 0f, -0.646f, 0.145f)
                quadToRelative(-0.312f, 0.146f, -0.604f, 0.396f)
                quadToRelative(-1.125f, 1f, -2.5f, 1.625f)
                reflectiveQuadTo(20f, 31.458f)
                quadToRelative(-1.5f, 0f, -2.875f, -0.625f)
                reflectiveQuadToRelative(-2.5f, -1.625f)
                quadToRelative(-0.292f, -0.25f, -0.604f, -0.396f)
                quadToRelative(-0.313f, -0.145f, -0.646f, -0.145f)
                reflectiveQuadToRelative(-0.667f, 0.125f)
                quadToRelative(-0.333f, 0.125f, -0.625f, 0.333f)
                quadToRelative(-1.166f, 0.917f, -2.5f, 1.563f)
                quadToRelative(-1.333f, 0.645f, -2.875f, 0.77f)
                close()
                moveToRelative(-1.875f, 6.584f)
                quadToRelative(-0.541f, 0f, -0.916f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.917f)
                quadToRelative(0f, -0.583f, 0.375f, -0.958f)
                reflectiveQuadToRelative(0.916f, -0.375f)
                horizontalLineToRelative(1.959f)
                quadToRelative(1.458f, 0f, 2.854f, -0.375f)
                quadToRelative(1.396f, -0.375f, 2.687f, -1.084f)
                quadToRelative(0.25f, -0.125f, 0.5f, -0.187f)
                quadToRelative(0.25f, -0.063f, 0.542f, -0.063f)
                quadToRelative(0.292f, 0f, 0.563f, 0.063f)
                quadToRelative(0.27f, 0.062f, 0.479f, 0.187f)
                quadToRelative(1.291f, 0.667f, 2.729f, 1.021f)
                quadToRelative(1.437f, 0.354f, 2.854f, 0.354f)
                reflectiveQuadToRelative(2.854f, -0.354f)
                quadToRelative(1.438f, -0.354f, 2.729f, -1.021f)
                quadToRelative(0.209f, -0.125f, 0.479f, -0.187f)
                quadToRelative(0.271f, -0.063f, 0.563f, -0.063f)
                quadToRelative(0.292f, 0f, 0.542f, 0.063f)
                quadToRelative(0.25f, 0.062f, 0.5f, 0.187f)
                quadToRelative(1.291f, 0.709f, 2.687f, 1.084f)
                quadToRelative(1.396f, 0.375f, 2.854f, 0.375f)
                horizontalLineToRelative(1.959f)
                quadToRelative(0.541f, 0f, 0.916f, 0.395f)
                quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                reflectiveQuadToRelative(-0.916f, 0.375f)
                horizontalLineToRelative(-1.959f)
                quadToRelative(-1.708f, 0f, -3.354f, -0.417f)
                quadToRelative(-1.646f, -0.417f, -3.229f, -1.25f)
                quadToRelative(-1.583f, 0.833f, -3.292f, 1.25f)
                quadToRelative(-1.708f, 0.417f, -3.333f, 0.417f)
                quadToRelative(-1.625f, 0f, -3.333f, -0.417f)
                quadToRelative(-1.709f, -0.417f, -3.292f, -1.25f)
                quadToRelative(-1.583f, 0.833f, -3.229f, 1.25f)
                quadToRelative(-1.646f, 0.417f, -3.354f, 0.417f)
                close()
                moveToRelative(4.709f, -20.584f)
                lineToRelative(9.625f, -3.083f)
                quadToRelative(0.416f, -0.125f, 0.833f, -0.125f)
                reflectiveQuadToRelative(0.833f, 0.125f)
                lineToRelative(9.625f, 3.125f)
                verticalLineTo(9.583f)
                horizontalLineTo(9.542f)
                close()
                moveTo(20f, 28.792f)
                quadToRelative(1.042f, 0f, 1.938f, -0.438f)
                quadToRelative(0.895f, -0.437f, 1.729f, -1.146f)
                quadToRelative(0.666f, -0.625f, 1.416f, -0.937f)
                quadToRelative(0.75f, -0.313f, 1.542f, -0.313f)
                reflectiveQuadToRelative(1.563f, 0.292f)
                quadToRelative(0.77f, 0.292f, 1.479f, 0.875f)
                quadToRelative(0.416f, 0.333f, 0.854f, 0.646f)
                quadToRelative(0.437f, 0.312f, 0.937f, 0.604f)
                lineToRelative(2f, -7.125f)
                lineTo(20f, 16.875f)
                lineTo(6.5f, 21.25f)
                lineToRelative(2.042f, 7.042f)
                quadToRelative(0.5f, -0.25f, 0.937f, -0.563f)
                quadToRelative(0.438f, -0.312f, 0.854f, -0.646f)
                quadToRelative(0.709f, -0.583f, 1.479f, -0.854f)
                quadToRelative(0.771f, -0.271f, 1.563f, -0.229f)
                quadToRelative(0.792f, 0f, 1.563f, 0.292f)
                quadToRelative(0.77f, 0.291f, 1.437f, 0.916f)
                quadToRelative(0.792f, 0.709f, 1.667f, 1.146f)
                quadToRelative(0.875f, 0.438f, 1.958f, 0.438f)
                close()
                moveToRelative(0f, -5.959f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberTrain(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "train",
            defaultWidth = 20.0.dp,
            defaultHeight = 20.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(12.167f, 34.667f)
                quadToRelative(-0.709f, -0.209f, -0.917f, -0.917f)
                quadToRelative(-0.208f, -0.708f, 0.333f, -1.25f)
                lineToRelative(1.042f, -1.042f)
                quadToRelative(-2.417f, 0f, -4.063f, -1.646f)
                quadToRelative(-1.645f, -1.645f, -1.645f, -4.062f)
                verticalLineTo(10.125f)
                quadToRelative(0f, -1.833f, 0.854f, -3.083f)
                quadToRelative(0.854f, -1.25f, 2.521f, -2f)
                quadToRelative(1.666f, -0.75f, 4.104f, -1.084f)
                quadToRelative(2.437f, -0.333f, 5.604f, -0.333f)
                quadToRelative(3.25f, 0f, 5.708f, 0.313f)
                quadTo(28.167f, 4.25f, 29.792f, 5f)
                quadToRelative(1.625f, 0.75f, 2.458f, 2f)
                quadToRelative(0.833f, 1.25f, 0.833f, 3.125f)
                verticalLineTo(25.75f)
                quadToRelative(0f, 2.417f, -1.646f, 4.062f)
                quadToRelative(-1.645f, 1.646f, -4.062f, 1.646f)
                lineToRelative(1.083f, 1.042f)
                quadToRelative(0.5f, 0.542f, 0.292f, 1.25f)
                quadToRelative(-0.208f, 0.708f, -0.917f, 0.917f)
                quadToRelative(-0.333f, 0.083f, -0.666f, 0f)
                quadToRelative(-0.334f, -0.084f, -0.584f, -0.334f)
                lineToRelative(-2.875f, -2.875f)
                horizontalLineToRelative(-7.416f)
                lineToRelative(-2.875f, 2.875f)
                quadToRelative(-0.25f, 0.25f, -0.584f, 0.334f)
                quadToRelative(-0.333f, 0.083f, -0.666f, 0f)
                close()
                moveTo(20f, 6.25f)
                quadToRelative(-4.75f, 0f, -6.958f, 0.583f)
                quadTo(10.833f, 7.417f, 10f, 8.5f)
                horizontalLineToRelative(20.083f)
                quadToRelative(-0.666f, -1f, -3.041f, -1.625f)
                reflectiveQuadTo(20f, 6.25f)
                close()
                moveTo(9.542f, 17.208f)
                horizontalLineToRelative(9.25f)
                verticalLineToRelative(-6.083f)
                horizontalLineToRelative(-9.25f)
                close()
                moveToRelative(17.833f, 2.667f)
                horizontalLineTo(9.542f)
                horizontalLineToRelative(20.916f)
                horizontalLineToRelative(-3.083f)
                close()
                moveToRelative(-5.917f, -2.667f)
                horizontalLineToRelative(9f)
                verticalLineToRelative(-6.083f)
                horizontalLineToRelative(-9f)
                close()
                moveToRelative(-7.375f, 9.459f)
                quadToRelative(0.959f, 0f, 1.625f, -0.646f)
                quadToRelative(0.667f, -0.646f, 0.667f, -1.646f)
                quadToRelative(0f, -0.958f, -0.667f, -1.604f)
                quadToRelative(-0.666f, -0.646f, -1.625f, -0.646f)
                quadToRelative(-0.958f, 0f, -1.625f, 0.646f)
                quadToRelative(-0.666f, 0.646f, -0.666f, 1.604f)
                quadToRelative(0f, 1f, 0.666f, 1.646f)
                quadToRelative(0.667f, 0.646f, 1.625f, 0.646f)
                close()
                moveToRelative(11.834f, 0f)
                quadToRelative(0.958f, 0f, 1.625f, -0.646f)
                quadToRelative(0.666f, -0.646f, 0.666f, -1.646f)
                quadToRelative(0f, -0.958f, -0.666f, -1.604f)
                quadToRelative(-0.667f, -0.646f, -1.625f, -0.646f)
                quadToRelative(-0.959f, 0f, -1.625f, 0.646f)
                quadToRelative(-0.667f, 0.646f, -0.667f, 1.604f)
                quadToRelative(0f, 1f, 0.667f, 1.646f)
                quadToRelative(0.666f, 0.646f, 1.625f, 0.646f)
                close()
                moveToRelative(-13.292f, 2.291f)
                horizontalLineToRelative(14.75f)
                quadToRelative(1.333f, 0f, 2.208f, -0.937f)
                quadToRelative(0.875f, -0.938f, 0.875f, -2.271f)
                verticalLineToRelative(-5.875f)
                horizontalLineTo(9.542f)
                verticalLineToRelative(5.875f)
                quadToRelative(0f, 1.333f, 0.875f, 2.271f)
                quadToRelative(0.875f, 0.937f, 2.208f, 0.937f)
                close()
                moveTo(20f, 8.5f)
                horizontalLineToRelative(10.083f)
                horizontalLineTo(10f)
                horizontalLineToRelative(10f)
                close()
            }
        }.build()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Transportation(beach: BeachUIState) {
    val state = rememberLazyListState()
    Text(
        text = "Kollektivruter",
        modifier = Modifier.padding(10.dp),
        fontWeight = FontWeight.SemiBold
    )
    LazyRow(
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        items(beach.transportationRoutes) {
            val transport: String?
            if (it.transportMode != null) {
                transport = when (it.transportMode) {
                    "bus" -> "Buss"
                    "water" -> "Båt"
                    "rail" -> "Tog"
                    "tram" -> "Trikk"
                    "metro" -> "T-Bane"
                    "coach" -> "Buss"
                    else -> it.transportMode.replaceFirstChar { letter ->
                        if (letter.isLowerCase()) letter.titlecase(
                            Locale.getDefault()
                        ) else letter.toString()
                    }
                }
            } else {
                transport = null
            }
            Card(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .width(160.dp)
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (it.transportMode) {
                            "water" -> {
                                Image(
                                    imageVector = rememberDirectionsBoat(),
                                    contentDescription = "Icon of boat",
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .size(30.dp)
                                        .background(Color.Transparent)
                                )
                            }

                            "rail", "metro", "tram" -> {
                                Image(
                                    imageVector = rememberTrain(),
                                    contentDescription = "Icon of train",
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .size(30.dp)
                                        .background(Color.Transparent)
                                )
                            }

                            else -> {
                                Image(
                                    imageVector = rememberDirectionsBus(),
                                    contentDescription = "Icon of bus",
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .size(30.dp)
                                        .background(Color.Transparent)
                                )
                            }
                        }
                        if (transport != null) {
                            Text(
                                text = "$transport ${it.line ?: ""}",
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 0.dp)
                            )
                        }
                        Text(
                            text = if (it.line != null) it.name else {
                                "Stoppested"
                            },
                            modifier = Modifier
                                .basicMarquee()
                                .padding(horizontal = 6.dp, vertical = 0.dp),
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, top = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Location Sign",
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = it.busStation.name.toString(),
                                modifier = Modifier.basicMarquee(),
                                fontWeight = FontWeight.Medium,
                                fontStyle = FontStyle.Normal,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun Gradient() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val customBlue = Color(0xFF2E4064)
        val brushUp =
            Brush.verticalGradient(listOf(customBlue.copy(alpha = 0.9F), Color.Transparent))
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), onDraw = {
            drawRect(brushUp)
        })
    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun BeachProfile(
    navController: NavController,
) {
    val beachViewModel: BeachViewModel = hiltViewModel()
    val beach = beachViewModel.beachUIState.collectAsState().value
    val isLoading by beachViewModel.isLoading.collectAsState()
    val isFavorite by beachViewModel.isFavorite.collectAsState()
    beach.beach?.let { beachViewModel.checkFavorite(it) }

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val isConnectivityIssue = beachViewModel.isConnectivityIssue.collectAsState()

    LaunchedEffect(isFavorite) {
        // Upon initial composition, check and update the favorites
        beach.beach?.let { beachViewModel.checkFavorite(it) }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Badested") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        if (isConnectivityIssue.value) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = "Kunne ikke laste informasjon.\nVennligst sjekk nettverkstilkoblingen din"
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    item {
                        Surface(
                            modifier = Modifier.fillMaxHeight(),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                        ) {
                            Card(
                                Modifier
                                    .padding(16.dp)
                                    .fillMaxSize(),
                                elevation = CardDefaults.elevatedCardElevation(8.dp)

                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize()

                                ) {
                                    val imageUrl = beach.beachInfo?.imageUrl
                                        ?: "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp"

                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Bilde fra Oslo Kommune",
                                        contentScale = ContentScale.FillWidth,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .align(Alignment.Center)
                                    )
                                    Gradient()
                                    if (imageUrl == "https://i.ibb.co/N9mppGz/DALL-E-2024-04-15-20-16-55-A-surreal-wide-underwater-scene-with-a-darker-shade-of-blue-depicting-a-s.webp") {
                                        LottieAnimation()
                                    }
                                    beach.beach?.let {
                                        Text(
                                            text = it.name,
                                            fontSize = 25.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color.White,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .align(Alignment.TopCenter)
                                                .padding(16.dp),
                                            style = TextStyle(
                                                letterSpacing = 0.4.sp,
                                                color = Color.White,
                                            )
                                        )
                                    }
                                    IconButton(modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(17.dp), onClick = {
                                        beach.beach?.let {
                                            beachViewModel.checkAndUpdateFavorites(it)
                                        }
                                    }) {
                                        Icon(
                                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                                            contentDescription = "Heart",
                                            tint = if (isFavorite) Color.Red else Color.White,
                                            modifier = Modifier.size(50.dp)
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(
                            Modifier.height(10.dp)
                        )
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            elevation = CardDefaults.elevatedCardElevation(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                Column {
                                    if (beach.beach?.waterTemp != null) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Badetemperatur",
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .padding(10.dp),
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Text(
                                                text = "${beach.beach.waterTemp}°C",
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .align(Alignment.CenterVertically)
                                            )
                                        }
                                    }
                                    if (beach.beachInfo?.waterQuality != null) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "Vannkvalitet",
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .align(Alignment.CenterVertically)
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            beach.beachInfo.waterQuality.let {
                                                Text(
                                                    text = it,
                                                    modifier = Modifier
                                                        .padding(10.dp)
                                                        .align(Alignment.CenterVertically),
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        if (beach.beachInfo?.facilitiesInfo != null) {
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxSize()
                                    .defaultMinSize(400.dp, 300.dp),
                                elevation = CardDefaults.elevatedCardElevation(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .fillMaxSize()
                                        .defaultMinSize(400.dp, 300.dp)
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = "Fasiliteter", fontWeight = FontWeight.SemiBold
                                        )
                                        beach.beachInfo.facilitiesInfo.let {
                                            Column(
                                                modifier = Modifier.padding(4.dp)
                                            ) {
                                                Text(
                                                    text = it, style = LocalTextStyle.current.merge(
                                                        TextStyle(
                                                            lineHeight = 2.0.em,
                                                            platformStyle = PlatformTextStyle(
                                                                includeFontPadding = false
                                                            ),
                                                            lineHeightStyle = LineHeightStyle(
                                                                alignment = LineHeightStyle.Alignment.Center,
                                                                trim = LineHeightStyle.Trim.None
                                                            )
                                                        )
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (beach.transportationRoutes.isEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                        } else Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .height(270.dp),
                            elevation = CardDefaults.elevatedCardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.background)
                            ) {
                                Transportation(beach)
                            }
                        }
                    }
                }
            }
        }
    }
}
        