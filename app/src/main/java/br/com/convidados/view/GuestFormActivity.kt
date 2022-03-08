package br.com.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.convidados.viewmodel.GuestFormViewModel
import br.com.convidados.databinding.ActivityGuestFormBinding
import br.com.convidados.service.constants.GuestConstants
import br.com.convidados.service.model.GuestModel

class GuestFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuestFormBinding
    private lateinit var viewModel: GuestFormViewModel
    private var guestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestFormBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)
        setListeners()
        observer()
        loadData()
        binding.radioPresent.isChecked = true
    }

    private fun loadData() {
//        intent?.let {
//            if (it.hasExtra(GuestConstants.GUESTID)) {
//                val id = it.getIntExtra(GuestConstants.GUESTID, 0)
//                Log.e("BRENOL", "tem $id")
//            }
//        }
        // ou pode ser feito assim
        val bundle = intent.extras
        bundle?.let {
            val id = it.getInt(GuestConstants.GUESTID)
            viewModel.load(id)
        }
    }

    private fun observer() {
        viewModel.saveGuest.observe(this) {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        viewModel.guest.observe(this) {
            guestId = it.id
            binding.editName.setText(it.name)
            if (it.presence) {
                binding.radioPresent.isChecked = true
            } else {
                binding.radioAbsent.isChecked = true
            }
        }
    }

    private fun setListeners() {
        binding.buttonSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val presence = binding.radioPresent.isChecked
            viewModel.save(guestId, name, presence)
        }
    }
}