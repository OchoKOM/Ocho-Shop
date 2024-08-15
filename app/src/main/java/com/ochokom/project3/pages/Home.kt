package com.ochokom.project3.pages


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ochokom.project3.BottomContainer
import com.ochokom.project3.CardWithActions
import com.ochokom.project3.HomeViewModelFactory
import com.ochokom.project3.ProductList
import com.ochokom.project3.ProfileBar
import com.ochokom.project3.R
import com.ochokom.project3.SearchTextField
import com.ochokom.project3.UserPreferences
import com.ochokom.project3.convertJsonToList
import com.ochokom.project3.convertListToJson
import kotlinx.coroutines.launch

class HomeViewModel(private val userPreferences: UserPreferences) : ViewModel() {
    private val _selectedCardIndex = mutableStateOf<Int?>(null)
    val selectedCardIndex: State<Int?> get() = _selectedCardIndex

    private val _selectedCardDetails = mutableStateOf<CardData?>(null)
    val selectedCardDetails: State<CardData?> get() = _selectedCardDetails

    private val _cards = MutableLiveData<List<CardData>>()
    val cards: LiveData<List<CardData>> get() = _cards
    val productList: List<CardData> get() = _cards.value ?: emptyList()

    private val _isNavigationBarVisible = mutableStateOf(true)

    private val initialList = listOf(
        // Ajoutez vos cartes ici
        CardData(
            name = "Surface Pro",
            price = 99900,
            description = "Imagine a device that’s faster and smarter than ever before while also empowering you to complete tasks more efficiently. You don’t have to look any further than Copilot+ PCs from Microsoft Surface. Unlock a new AI era begins with these Copilot+ PCs. They aren’t your run-of-the-mill PCs; they’re next-gen AI PCs that have lightning-fast speeds and AI accelerated power. Learn about the different advantages you’ll have when using a Copilot+ PC.",
            imageResId = R.drawable.surface_pro,
        ),
        CardData(
            name = "Surface Laptop",
            price = 109900,
            description = "Imagine a device that’s faster and smarter than ever before while also empowering you to complete tasks more efficiently. You don’t have to look any further than Copilot+ PCs from Microsoft Surface. Unlock a new AI era begins with these Copilot+ PCs. They aren’t your run-of-the-mill PCs; they’re next-gen AI PCs that have lightning-fast speeds and AI accelerated power. Learn about the different advantages you’ll have when using a Copilot+ PC.",
            imageResId = R.drawable.surface_laptop
        ),
        CardData(
            name = "Galaxy Book 4 Series",
            price = 139900,
            description = "Imagine a device that’s faster and smarter than ever before while also empowering you to complete tasks more efficiently. You don’t have to look any further than Copilot+ PCs from Microsoft Surface. Unlock a new AI era begins with these Copilot+ PCs. They aren’t your run-of-the-mill PCs; they’re next-gen AI PCs that have lightning-fast speeds and AI accelerated power. Learn about the different advantages you’ll have when using a Copilot+ PC.",
            imageResId = R.drawable.samsung_galaxy_book4
        ),
        CardData(
            name = "Galaxy S24 Ultra",
            price = 119999,
            description = "Samsung Galaxy S24 Ultra avec écran AMOLED et caméra 200MP.",
            imageResId = R.drawable.samsung_galaxy_s24u
        ),
        CardData(
            name = "iPhone 15 Pro",
            price = 109999,
            description = "IPhone 15 pro gris avec écran AMOLED et caméra 48MP.",
            imageResId = R.drawable.iphone_15_pro
        ),
        CardData(
            name = "iPhone 14 Pro Max",
            price = 99999,
            description = "Smartphone avec écran AMOLED et caméra 48MP.",
            imageResId = R.drawable.iphone_14_pro_max
        ),
        CardData(
            name = "Casque Shiny Golden",
            price = 1999,
            description = "Casque antibruit avec connectivité Bluetooth.",
            imageResId = R.drawable.headphones
        ),
        CardData(
            name = "Surface Pro 7",
            price = 119999,
            description = "Laptop haute performance avec processeur Intel Core i7.",
            imageResId = R.drawable.laptop
        ),
    )

    init {
        if(this.productList.isEmpty()){
            _cards.value = initialList
        }

        viewModelScope.launch {
            userPreferences.likeStateFlow.collect { likeStateJson ->
                if (likeStateJson.isEmpty()) {
                    _cards.value = initialList
                }else{
                    try {
                        _cards.value = convertJsonToList(likeStateJson)
                    } catch (e: Exception) {
                        _cards.value = initialList
                    }
                }
            }
        }
    }

    fun selectCard(index: Int) {
        _selectedCardIndex.value = index
        _selectedCardDetails.value = _cards.value?.get(index)
        _isNavigationBarVisible.value = false
    }

    fun deselectCard() {
        _selectedCardIndex.value = null
        _selectedCardDetails.value = null
        _isNavigationBarVisible.value = true
    }

    fun toggleLikeCard(index: Int) {
        val updatedCards = _cards.value?.toMutableList()
        updatedCards?.let {
            val card = it[index]
            it[index] = card.copy(isLiked = !card.isLiked)
            _cards.value = it
        }
        saveCardsToDataStore()
    }

    private fun saveCardsToDataStore() {
        viewModelScope.launch {
            _cards.value?.let { cards ->
                val cardsJson = convertListToJson(cards)
                userPreferences.saveLikeState(cardsJson)
            }
        }
    }
}



data class CardData(
    val name: String,
    val price: Int,
    val description: String,
    val imageResId: Int,
    var isLiked: Boolean = false
)

@Preview
@Composable
fun HomePage(
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
                ProfileBar()
                SearchTextField()
                BottomContainer(
                    Modifier.padding(top = 20.dp)
                ) {
                    ProductList(
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
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(1f),
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





