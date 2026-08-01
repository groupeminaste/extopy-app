package com.extopy.presentation.components.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.extopy.resources.Res
import com.extopy.resources.auth_button_login
import com.extopy.resources.auth_description
import com.extopy.resources.auth_title
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.Lottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AuthRootView(
    loginClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/social.json").decodeToString()
        )
    }

    val painter = rememberLottiePainter(
        composition = composition,
        iterations = Compottie.IterateForever,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Spacer(Modifier.weight(1f))

        Lottie(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(Modifier.weight(1f))

        Text(
            text = stringResource(Res.string.auth_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(Res.string.auth_description),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = loginClicked,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large
        ) {
            Text(stringResource(Res.string.auth_button_login))
        }
    }

}

@Preview
@Composable
fun AuthRootViewPreview() {
    AuthRootView(
        loginClicked = {},
    )
}
