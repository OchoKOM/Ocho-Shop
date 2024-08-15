package com.ochokom.project3.pages

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ochokom.project3.BottomContainer
import com.ochokom.project3.CardWithActions
import com.ochokom.project3.FavoriteList
import com.ochokom.project3.HomeViewModelFactory
import com.ochokom.project3.SearchTextField


@Preview
@Composable
fun FavoritesPage(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(LocalContext.current))
) {
    val selectedCardIndex by viewModel.selectedCardIndex
    LaunchedEffect(selectedCardIndex) {
        // Réagit aux changements d'état
    }

    // Gérer le bouton "Retour" au niveau de la page d'accueil
    BackHandler(enabled = selectedCardIndex != null) {
        viewModel.deselectCard()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            if (selectedCardIndex == null) {
                Box(modifier = Modifier.padding(top = 20.dp)){
                    SearchTextField()
                }
                BottomContainer(
                    Modifier.padding(top = 20.dp)
                ) {
                    FavoriteList(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        viewModel = viewModel
                    )
                }
            } else {
                val selectedCardDetails by viewModel.selectedCardDetails
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CardWithActions(
                        modifier = Modifier.fillMaxSize(),
                        index = selectedCardIndex!!,
                        viewModel = viewModel,
                        productName = selectedCardDetails?.name,
                        productPrice = selectedCardDetails?.price,
                        productDescription = selectedCardDetails?.description,
                        productImageResId = selectedCardDetails?.imageResId
                    )
                }
            }
        }
    }
}