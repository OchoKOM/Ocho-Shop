package com.ochokom.project3.pages


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.ochokom.project3.R

@Composable
fun AddPage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ProductEntryForm()
    }
}

@SuppressLint("InlinedApi")
@Preview(showBackground = true)
@Composable
fun ProductEntryForm() {
    var productName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priceInCents by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val productNameFocusRequester = remember { FocusRequester() }
    val priceFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    val context = LocalContext.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasPermission = isGranted
            if (isGranted) {
                launcher.launch("image/*")
            } else {
                Toast.makeText(context, "Permission denied to access storage", Toast.LENGTH_SHORT).show()
            }
        }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = colorScheme.onSecondary,
        unfocusedContainerColor = colorScheme.onSecondary,
        focusedBorderColor = colorScheme.primary,
        unfocusedBorderColor = Color.Transparent
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
            .padding(20.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sell),
                contentDescription = "Sell icon"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Nouveau produit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            item {
                Column(
                    Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .clickable {
                            if (!hasPermission) {
                                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            } else {
                                launcher.launch("image/*")
                            }
                        },
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    val imagePainter: Painter = selectedImageUri?.let { uri ->
                        rememberImagePainter(uri)
                    } ?: painterResource(id = R.drawable.image)

                    Image(
                        painter = imagePainter,
                        contentDescription = "Image placeholder",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(200.dp)
                    )
                    Text(
                        text = "Cliquez pour sélectionner une image",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Nom du produit") },
                    singleLine = true,
                    colors = textFieldColors,
                    shape = RoundedCornerShape(30.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { priceFocusRequester.requestFocus() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(productNameFocusRequester)
                )
                OutlinedTextField(
                    value = priceInCents,
                    onValueChange = { priceInCents = it },
                    label = { Text("Prix (en centimes)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { descriptionFocusRequester.requestFocus() }
                    ),
                    singleLine = true,
                    colors = textFieldColors,
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(priceFocusRequester)
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description du produit") },
                    colors = textFieldColors,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(descriptionFocusRequester),
                    minLines = 5,
                    maxLines = 8,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Vendre")
                    }
                }
            }
        }
    }
}