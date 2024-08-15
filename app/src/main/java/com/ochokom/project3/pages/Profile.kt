package com.ochokom.project3.pages


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ochokom.project3.BottomContainer
import com.ochokom.project3.LabeledIconColumn
import com.ochokom.project3.LabeledIconRow
import com.ochokom.project3.ProfileImg
import com.ochokom.project3.R


@Preview
@Composable
fun ProfilePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            Row(
                Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                /* Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .background(
                            hexColor("#60808080"),
                            RoundedCornerShape(50)
                        )
                        .size(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BackBtn(
                        color = Color.White,
                        size = 35.dp
                    )
                } */
                Spacer(modifier = Modifier.size(40.dp))
                Text(
                    text = stringResource(R.string.profile),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(40.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImg(
                    image = painterResource(id = R.drawable.profile),
                    size = 150.dp,
                    borderColor = MaterialTheme.colorScheme.onBackground,
                    borderSize = 3.dp
                )
                Text(
                    text = stringResource(R.string.username),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            BottomContainer(
                modifier = Modifier
                    .weight(1f)
            ) {
                data class TabItem(
                    val icon: Int,
                    val label: String,
                    val content: @Composable () -> Unit
                )

                val tabs = listOf(
                    TabItem(
                        icon = R.drawable.account_circle_fill,
                        label = "Compte",
                        content = {
                            Column{
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(15.dp, 2.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ){
                                    Text(
                                        text = "Options du compte",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    LazyColumn(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        item {
                                            LabeledIconRow(
                                                painter = painterResource(id = R.drawable.camera),
                                                label = "Modifier la photo de profil",
                                                onClick = { /*TODO*/ }
                                            )
                                        }
                                        item {
                                            LabeledIconRow(
                                                painter = painterResource(id = R.drawable.id_card),
                                                label = "Confirmer mon identitée",
                                                onClick = { /*TODO*/ }
                                            )
                                        }
                                        item {
                                            LabeledIconRow(
                                                painter = painterResource(id = R.drawable.person_edit),
                                                label = "Modifier le nom d'utilisateur",
                                                onClick = { /*TODO*/ }
                                            )
                                        }
                                        item {
                                            LabeledIconRow(
                                                painter = painterResource(id = R.drawable.lock),
                                                label = "Modifier le mot de passe",
                                                onClick = { /*TODO*/ }
                                            )
                                        }
                                        item {
                                            LabeledIconRow(
                                                painter = painterResource(id = R.drawable.no_accounts),
                                                label = "Demander à supprimer le compte",
                                                onClick = { /*TODO*/ }
                                            )
                                        }
                                    }

                                }
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    onClick = { /*TODO*/ }
                                ) {
                                    Text(
                                        text = "Déconnexion",
                                    )
                                }
                            }
                        }
                    ),
                    TabItem(
                        icon = R.drawable.cart,
                        label = "Panier",
                        content = {
                            Text(text = "Contenu du panier")
                        }
                    ),
                    TabItem(
                        icon = R.drawable.sell,
                        label = "Produits",
                        content = {
                            Text(text = "Liste des produits")
                        }
                    )
                )

                var selectedTab by remember {
                    mutableIntStateOf(0)
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 15.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                        ){
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            tabs.forEachIndexed { index, tab ->
                                LabeledIconColumn(
                                    painter = painterResource(id = tab.icon),
                                    label = tab.label,
                                    onClick = { selectedTab = index }
                                )
                            }
                        }
                        // Contenu de l'onglet sélectionné
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            tabs[selectedTab].content()
                        }
                    }
                }
            }
        }
    }
}