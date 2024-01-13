package me.nathanfallet.extopy.viewmodels.users

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.asStateFlow
import me.nathanfallet.extopy.models.posts.Post
import me.nathanfallet.extopy.models.users.User
import me.nathanfallet.extopy.usecases.posts.IFetchUserPostsUseCase
import me.nathanfallet.extopy.usecases.users.IFetchUserUseCase

class ProfileViewModel(
    private val id: String,
    private val fetchUserUseCase: IFetchUserUseCase,
    private val fetchUserPostsUseCase: IFetchUserPostsUseCase,
) : KMMViewModel() {

    // Properties

    private val _user = MutableStateFlow<User?>(viewModelScope, null)
    private val _posts = MutableStateFlow<List<Post>?>(viewModelScope, null)

    @NativeCoroutinesState
    val user = _user.asStateFlow()

    @NativeCoroutinesState
    val posts = _posts.asStateFlow()

    // Methods

    @NativeCoroutines
    suspend fun fetchUser() {
        _user.value = fetchUserUseCase(id)
        _posts.value = fetchUserPostsUseCase(id)
    }

}
