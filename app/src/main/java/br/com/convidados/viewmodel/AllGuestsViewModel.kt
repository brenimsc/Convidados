package br.com.convidados.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.convidados.service.constants.GuestConstants
import br.com.convidados.service.model.GuestModel
import br.com.convidados.service.repository.GuestRepository

class AllGuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val guestRepository = GuestRepository.getInstance(application.applicationContext)

    private val _guestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = _guestList

    fun load(filter : Int) {

        when (filter) {
            GuestConstants.FILTER.EMPTY -> _guestList.value = guestRepository.getAll()
            GuestConstants.FILTER.PRESENT -> _guestList.value = guestRepository.getPresent()
            else -> _guestList.value = guestRepository.getAbsent()
        }
    }

    fun delete(id: Int) {
        guestRepository.delete(id)
    }
}