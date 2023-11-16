package com.example.searchfield

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.combine as combine1

class MainViewModel: ViewModel() {
    private val _searchText = MutableStateFlow("")
     val searchText = _searchText.asStateFlow()

    private  val  _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allPersons)

    @OptIn(FlowPreview::class)
    val persons = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine1(_persons){ text, persons ->
        if (text.isBlank()){
            persons
        } else {
            delay(2000L)
            persons.filter{
                it.doesMatchSearchQuery(text)
            }
        }
    }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000 ),
            _persons.value
        )


    fun onSearchTextChange(text:String){
        _searchText.value =text
    }

}

data class Person(
    val firstName: String,
    val lastName: String
){
    fun doesMatchSearchQuery(query: String):Boolean{
        val matchingCombination = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}"
        )
        return matchingCombination.any{
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allPersons = listOf(
    Person(
        firstName = "Blannon",
        lastName = "Ngoge"
    ),
    Person(
        firstName = "Rachami",
        lastName = "3rd"
    ),
    Person(
        firstName = "Tommy",
        lastName = "Egan"
    ),
    Person(
        firstName = "Rambo",
        lastName = "Tv"
    ),
    Person(
        firstName = "Unity",
        lastName = "Power"
    ),)