package com.example.searchfield

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.searchfield.ui.theme.SearchFieldTheme

class MainActivity1 : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchFieldTheme {
               SearchFieldTheme {
                   val viewModel = viewModel<MainViewModel>()
                   val searchText by viewModel.searchText.collectAsState()
                   val persons by viewModel.persons.collectAsState()
                   val isSearching by viewModel.isSearching.collectAsState()

                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                   ) {
                        TextField(
                            value =searchText ,
                            onValueChange = viewModel::onSearchTextChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            placeholder = { Text(text = "Search")}
                        )
                       Spacer(modifier = Modifier.height(16.dp))
                       if (isSearching){
                           Box(
                               modifier = Modifier
                                   .fillMaxSize()){
                               CircularProgressIndicator(
                                   modifier = Modifier
                                       .align(Alignment.Center)
                               )
                           }
                       }
                       LazyColumn(
                           modifier = Modifier
                               .fillMaxWidth()
                               .weight(1f)
                       ){
                           items(persons){ persons ->
                               Text(
                                   text = "${persons.firstName} ${persons.lastName}",
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(vertical = 16.dp))

                           }
                       }
                   }
               }
            }
        }
    }
}

