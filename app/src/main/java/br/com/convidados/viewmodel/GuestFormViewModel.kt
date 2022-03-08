package br.com.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.convidados.service.model.GuestModel
import br.com.convidados.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val guestRepository: GuestRepository = GuestRepository.getInstance(context)

    private var _saveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> get() = _saveGuest

    private var _guest = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> get() = _guest

    fun save(id: Int, name: String, presence: Boolean) {
        val guest = GuestModel(id, name, presence)
        if (id == 0) {
            _saveGuest.value = guestRepository.save(guest)
        }
        else {
            _saveGuest.value = guestRepository.update(guest)
        }
    }

    fun load(id: Int) {
        _guest.value = guestRepository.get(id)
    }
}