package com.orumgames.ui.detailbeer

import androidx.lifecycle.viewModelScope
import com.orumgames.domain.common.usecase.LoadDetailBeerUseCase
import com.orumgames.domain.common.usecase.flow.onFailure
import com.orumgames.domain.common.usecase.flow.onSuccess
import com.orumgames.domain.models.Beer
import com.orumgames.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBeerViewModel @Inject constructor(
    private val loadDetailBeerUseCase: LoadDetailBeerUseCase
) : BaseViewModel() {

    private val _states = MutableStateFlow<State>(State.Loading(false))
    val states: StateFlow<State> = _states.asStateFlow()

    var idBeer: String? = null
    private var beer: Beer? = null
        set(value) {
            field = value
            idBeer = value?.id.toString()
        }

    fun findBeerById(idBeer: String?) = viewModelScope.launch {
        idBeer?.let {
            loadDetailBeerUseCase
                .execute(idBeer.toInt())
                .onStart { _states.value = State.Loading(true) }
                .catch { }
                .onEach { res ->
                    res.onSuccess {
                        beer = it.first()
                        _states.value = State.BeerReady(it.first())
                    }.onFailure {
                        _states.value = State.Error
                    }
                }
                .onCompletion { _states.value = State.Loading(false) }
                .collect {}
        } ?: run {
            _states.value = State.Error
        }
    }
}


sealed class State {
    data class Loading(val isLoading: Boolean) : State()
    data class BeerReady(val beer: Beer) : State()
    object Error : State()
}