package com.digel.recipe.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.digel.recipe.data.ResponseGetRecipe
import com.digel.recipe.ui.theme.RecipeApplicationTheme
import com.digel.recipe.ui.viewmodel.RecipeViewModel
import com.digel.recipe.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: RecipeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(viewModel: RecipeViewModel) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.primaryVariant, elevation = 3.dp) {
            Text(
                text = "Recipe",
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it.calculateBottomPadding())
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RecipeScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun RecipeScreen(viewModel: RecipeViewModel) {

    val recipeList by viewModel.recipeListFlow.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        recipeList
            .doOnIdle {
                viewModel.getRecipeList()
            }
            .doOnSuccess {
                Log.d("","data recipe is -> $it")

                LazyColumn(
                    content = {
                      items(items = it){
                          ProductItemScreen(recipe = it)
                      }
                    }
                )
            }
            .doOnLoading {
                CircularProgressIndicator()
            }
            .doOnFailure {
                Text(text = it.message.orEmpty())
            }
            .doOnEmpty {
                Text(text = "Not Content!")
            }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItemScreen(recipe: ResponseGetRecipe) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp), elevation = 3.dp) {

        GlideImage(
            model = recipe.thumb,
            contentDescription = "Thumbnail",
        )
    }
}