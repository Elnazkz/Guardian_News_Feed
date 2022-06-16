package com.elnaz.theguardiannewsfeed.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elnaz.theguardiannewsfeed.MainRepository
import com.elnaz.theguardiannewsfeed.data.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _savedArticle: MutableLiveData<List<Article>> = MutableLiveData()
    val savedArticle: LiveData<List<Article>> = _savedArticle

    fun addArticleToFav(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertArticle(article)
        }
    }

    fun removeArticleFromFav(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteArticle(article)
        }
    }

    fun getArticles(): LiveData<PagingData<Article>> {
        return mainRepository.getArticles().cachedIn(viewModelScope)
    }

    //for pageble database
//    fun getSavedArticles(): LiveData<PagingData<Article>> {
//        return mainRepository.getSavedArticles().cachedIn(viewModelScope)
//    }

    fun getFavouriteArticles() {
        viewModelScope.launch {
            mainRepository.getFavourites().collect {
                _savedArticle.value = it
            }
        }
    }

}