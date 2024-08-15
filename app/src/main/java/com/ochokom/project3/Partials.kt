package com.ochokom.project3


import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.ochokom.project3.pages.HomeViewModel
import java.text.NumberFormat
import java.util.Locale


@Composable
fun ProfileBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileImg(
            image = painterResource(id = R.drawable.profile),
            size = 70.dp,
            borderColor = colorScheme.onBackground
        )

        Column(
            Modifier
                .padding(10.dp)
                .weight(1f)
                .height(60.dp)
        ) {
            Row(
                Modifier.weight(0.5f),
            ){
                Text(
                    text = stringResource(R.string.welcome_on),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(top = 5.dp),
                    color = colorScheme.onBackground,
                )
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(top = 5.dp),
                    color = colorScheme.onBackground,
                )
            }
            Text(
                text = stringResource(R.string.username),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.5f),
                color = colorScheme.onBackground,
            )
        }
        Box(
            Modifier
                .background(
                    colorScheme.onSecondary,
                    shape = RoundedCornerShape(50)
                )
                .padding(5.dp)
                .size(25.dp),
        ) {
            BellIcon(
                color = colorScheme.onBackground,
                size = 25.dp
            )
        }
    }
}

@Composable
fun ProfileImg(
    image: Painter,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    borderColor: Color = Color.Black,
    borderSize: Dp = 5.dp
) {
    Box(
        modifier = modifier
            .size(size)  // Pour donner de l'espace à la bordure
            .clip(shape = RoundedCornerShape(55.dp))  // La bordure doit être ronde
            .border(
                width = borderSize,  // Épaisseur de la bordure
                color = borderColor,  // Couleur de la bordure
                shape = RoundedCornerShape(50)
            )
    ) {
        Image(
            painter = image,
            contentDescription = "Profile pic",
            modifier = Modifier
                .size(size)  // Taille de l'image légèrement plus petite que la Box
                .clip(shape = RoundedCornerShape(50))
        )
    }
}

@Composable
fun BellIcon(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    color: Color = Color.Black,  // Couleur par défaut de l'icône
    size: Dp = 50.dp
) {
    val bellImg = if (isActive) {
        painterResource(id = R.drawable.bell_fill)
    } else {
        painterResource(id = R.drawable.bell)
    }

    Icon(
        painter = bellImg,
        contentDescription = "Notifications",
        modifier = modifier
            .size(size),  // Utilisation de .size pour simplifier la définition de la taille
        tint = color  // Application de la couleur à l'icône
    )
}


@Composable
fun SearchTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = colorScheme.onSecondary,
                    shape = RoundedCornerShape(40.dp)
                )
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(top = 10.dp, bottom = 10.dp)
                    .size(50.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search icon",
                    modifier = Modifier
                        .size(55.dp)
                )
            }

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        "Rechercher...",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(1f),
            )
            Box(
                modifier = Modifier
                    .background(
                        Color(0xFF1E90FF),
                        shape = RoundedCornerShape(55)
                    )
                    .padding(top = 10.dp, bottom = 10.dp)
                    .size(50.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.tune),
                    contentDescription = "Filtres",
                    modifier = modifier
                        .size(55.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BottomContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .background(
                colorScheme.onSecondary,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .fillMaxSize()
    ) {
        Box(
            Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            content()
        }
    }
}

@Composable
fun ProductList(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val productList = viewModel.productList

    Column(
        modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Populaires",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Filtres",
                color = Color(0xff1e90ff)
            )
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    bottom = 80.dp
                ),
            contentPadding = PaddingValues(0.dp)
        ) {
            if (productList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aucun produit disponible",
                            color = Color.Gray,
                        )
                    }
                }
            } else {
                items((productList.size + 1) / 2) { index ->
                    val startIndex = index * 2
                    val endIndex = startIndex + 1
                    val cardModifier = Modifier
                        .weight(1f)
                        .height(200.dp)
                        .background(colorScheme.background, RoundedCornerShape(10.dp))
                        .padding(5.dp, 7.dp)

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        CardWithActions(
                            modifier = cardModifier, // Assure que la hauteur est celle du contenu
                            index = startIndex,
                            viewModel = viewModel,
                            productName = productList[startIndex].name,
                            productPrice = productList[startIndex].price,
                            productDescription = productList[startIndex].description,
                            productImageResId = productList[startIndex].imageResId,
                            onBuy = {
                                // Action à effectuer lors du clic sur le bouton Acheter
                            }
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        // Vérifier s'il y a un deuxième produit dans la paire
                        if (endIndex < productList.size) {
                            CardWithActions(
                                modifier = cardModifier, // Assure que la hauteur est celle du contenu
                                index = endIndex,
                                viewModel = viewModel,
                                productName = productList[endIndex].name,
                                productPrice = productList[endIndex].price,
                                productDescription = productList[endIndex].description,
                                productImageResId = productList[endIndex].imageResId,
                                onBuy = {
                                    // Action à effectuer lors du clic sur le bouton Acheter
                                    println("Acheter le produit : ${productList[endIndex].name}")
                                }
                            )
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .height(220.dp)
                                    .weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))

                }
            }
        }
    }
}

@Composable
fun FavoriteList(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    // Filtrer les produits "likés"
    val favoriteList = viewModel.productList
        .mapIndexed { index, product -> index to product }
        .filter { it.second.isLiked }
    Column(
        modifier
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Favoris",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Filtres",
                color = colorScheme.primary
            )
        }
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    bottom = 80.dp
                ),
            contentPadding = PaddingValues(0.dp)
        ) {
            if (favoriteList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aucun Favoris",
                            color = Color.Gray,
                        )
                    }
                }
            } else {
                items((favoriteList.size + 1) / 2) { index ->
                    val startPair = favoriteList.getOrNull(index * 2)
                    val endPair = favoriteList.getOrNull(index * 2 + 1)

                    val startIndex = startPair?.first ?: return@items
                    val startProduct = startPair.second


                    val cardModifier = Modifier
                        .weight(1f)
                        .height(200.dp)
                        .background(colorScheme.background, RoundedCornerShape(10.dp))
                        .padding(5.dp, 7.dp)

                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        CardWithActions(
                            modifier = cardModifier, // Assure que la hauteur est celle du contenu
                            index = startIndex,
                            viewModel = viewModel,
                            productName = startProduct.name,
                            productPrice = startProduct.price,
                            productDescription = startProduct.description,
                            productImageResId = startProduct.imageResId,
                            onBuy = {
                                // Action à effectuer lors du clic sur le bouton Acheter
                            }
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        // Vérifier s'il y a un deuxième produit dans la paire
                        if (endPair != null) {
                            val endIndex = endPair.first
                            val endProduct = endPair.second

                            CardWithActions(
                                modifier = cardModifier, // Assure que la hauteur est celle du contenu
                                index = endIndex,
                                viewModel = viewModel,
                                productName = endProduct.name,
                                productPrice = endProduct.price,
                                productDescription = endProduct.description,
                                productImageResId = endProduct.imageResId,
                                onBuy = {
                                    // Action à effectuer lors du clic sur le bouton Acheter
                                    println("Acheter le produit : ${endProduct.name}")
                                }
                            )
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .height(220.dp)
                                    .weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))

                }
            }
        }
    }
}

@Composable
fun CardWithActions(
    modifier: Modifier = Modifier,
    index: Int,
    viewModel: HomeViewModel,
    productName: String? = null,
    productPrice: Int? = null,
    productDescription: String? = null,
    productImageResId: Int? = null,
    onBuy: () -> Unit = {}
) {
    val selectedCardIndex by viewModel.selectedCardIndex
    val selectedCardDetails by viewModel.selectedCardDetails
    val cardData by viewModel.cards.observeAsState(emptyList())

    // Gérer le bouton "Retour"
    if (selectedCardIndex == index) {
        BackHandler {
            viewModel.deselectCard()
        }
    }

    val name = selectedCardDetails?.name ?: productName ?: "Produit"
    val price = selectedCardDetails?.price ?: productPrice ?: 0
    val description = selectedCardDetails?.description ?: productDescription ?: ""
    val imageResId = selectedCardDetails?.imageResId ?: productImageResId ?: R.drawable.image
    val isLiked = cardData.getOrNull(index)?.isLiked ?: false

    val styles = if (selectedCardIndex == index) {
        Modifier
            .fillMaxSize()
            .zIndex(1f)
    } else {
        modifier
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                viewModel.selectCard(index)
            }
    }

    Column(
        modifier = styles
            .fillMaxSize()
            .animateContentSize()
    ) {
        if (selectedCardIndex == index) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 10.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .background(
                            Color(0x60808080),
                            RoundedCornerShape(50)
                        )
                        .size(40.dp)
                        .clickable(
                            onClick = {
                                viewModel.deselectCard()
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    BackBtn(
                        color = Color.White,
                        size = 35.dp
                    )
                }

                var isActive by remember {
                    mutableStateOf(isLiked)
                }

                Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .padding(10.dp)
                        .background(
                            Color(0x60808080),
                            RoundedCornerShape(50)
                        )
                        .size(40.dp)
                        .clickable {
                            isActive = !isActive
                            viewModel.toggleLikeCard(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    val color: Color = if (isActive) Color.Red else Color.White
                    LikeBtn(
                        isActive = isActive,
                        color = color
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            val imageBoxStyles = if (selectedCardIndex == index) {
                Modifier
                    .padding(5.dp)
                    .weight(0.5f)
            } else {
                Modifier
                    .height(150.dp)
            }
            val imageStyles = if (selectedCardIndex == index) {
                Modifier
                    .fillMaxSize()
            } else {
                Modifier
                    .fillMaxWidth()
            }
            Box(
                imageBoxStyles,
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = productName,
                    modifier = imageStyles
                        .clip(RoundedCornerShape(5.dp)),
                )
            }

            val formattedPrice: String = if (price == 0) {
                "Gratuit"
            } else {
                // Utiliser NumberFormat pour formater les montants avec des centimes
                val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
                currencyFormat.maximumFractionDigits = 2
                currencyFormat.minimumFractionDigits = 0

                // Diviser par 100 pour convertir les centimes en dollars
                val dollarPrice = price / 100.0

                // Formater le prix en utilisant le format de devise avec des virgules
                currencyFormat.format(dollarPrice)
            }
            if (selectedCardIndex != index) {
                Column(
                    Modifier
                        .height(50.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = formattedPrice,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
            if (selectedCardIndex == index) {
                BottomContainer(
                    Modifier
                        .weight(0.5f)
                ) {
                    Column(
                        Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 15.dp
                        )
                    ) {
                        Row(
                            Modifier
                                .height(50.dp)
                        ) {
                            Text(
                                text = name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }

                        LazyColumn(
                            Modifier
                                .weight(1f)
                        ) {
                            item {
                                val descriptionColor = if (productDescription == "") {
                                    Color.Gray
                                } else {
                                    colorScheme.onBackground
                                }
                                val descriptionContent = if (description == "") {
                                    "Aucune description"
                                } else {
                                    description
                                }
                                Text(
                                    text = descriptionContent,
                                    fontSize = 16.sp,
                                    color = descriptionColor
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                Modifier
                                    .weight(1f),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Prix",
                                    color = Color.Gray
                                )
                                Text(
                                    formattedPrice,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            }
                            Button(
                                onClick = onBuy,
                                modifier = Modifier
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors()
                            ) {
                                Text(
                                    "Acheter",
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun BackBtn(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    size: Dp = 30.dp
) {
    val painter = painterResource(id = R.drawable.chevron_left)


    Icon(
        painter,
        contentDescription = "Close",
        modifier = modifier
            .size(size),
        tint = color
    )
}

@Composable
fun LikeBtn(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    color: Color = Color.Black,
    size: Dp = 30.dp
) {
    val painter = if (isActive) {
        painterResource(id = R.drawable.favorite_fill)
    } else {
        painterResource(id = R.drawable.favorite)
    }

    Icon(
        painter,
        contentDescription = "Close",
        modifier = modifier
            .size(size),
        tint = color
    )
}

@Preview
@Composable
fun LabeledIconColumn(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.icon),
    label: String = "Text",
    onClick: ()->Unit = {}
) {
    Box(modifier = modifier
        .background(colorScheme.background, RoundedCornerShape(7.dp))
        .size(70.dp)
        .padding(7.dp)
        .clickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(
                painter = painter,
                contentDescription = label,
                tint = colorScheme.onBackground,
                modifier = Modifier
                    .size(30.dp)
            )
            Text(
                text = label,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
fun LabeledIconRow(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.icon),
    label: String = "Text",
    onClick: ()->Unit = {}
) {
    Box(modifier = modifier
        .background(colorScheme.background, RoundedCornerShape(7.dp))
        .fillMaxWidth()
        .padding(10.dp, 7.dp)
        .clickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painter,
                contentDescription = label,
                tint = colorScheme.onBackground,
                modifier = Modifier
                    .size(25.dp)
            )
            Text(
                text = label,
                color = colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }
    }
}