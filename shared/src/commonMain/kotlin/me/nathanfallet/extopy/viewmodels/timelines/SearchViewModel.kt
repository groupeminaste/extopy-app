package me.nathanfallet.extopy.viewmodels.timelines

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(

) : KMMViewModel() {

    // Properties

    private val _search = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    val search = _search.asStateFlow()

    // Methods

    fun updateSearch(value: String) {
        _search.value = value
    }


}
