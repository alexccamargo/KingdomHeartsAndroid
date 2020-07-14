package acdc.kingdomhearts.ui.kh3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KingdomHearts3ViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "¯\\_(ツ)_/¯"
    }
    val text: LiveData<String> = _text
}