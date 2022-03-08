package br.com.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.convidados.databinding.FragmentPresentBinding
import br.com.convidados.service.constants.GuestConstants
import br.com.convidados.view.adapter.GuestAdapter
import br.com.convidados.viewmodel.AllGuestsViewModel

class PresentsFragment : Fragment() {

    private lateinit var binding: FragmentPresentBinding
    private lateinit var viewModel: AllGuestsViewModel
    private val adapter = GuestAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPresentBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(this).get(AllGuestsViewModel::class.java)

        val recycler = binding.recyclerPresentsGuests
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        observer()
        viewModel.load(GuestConstants.FILTER.PRESENT)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(GuestConstants.FILTER.PRESENT)
    }

    private fun observer() {
        viewModel.guestList.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
        adapter.setOnClickListener {
            val intent = Intent(context, GuestFormActivity::class.java)

            val bundle = Bundle()
            bundle.putInt(GuestConstants.GUESTID, it.id)

            intent.putExtra(GuestConstants.GUESTID, it.id)
            startActivity(intent)
        }
        adapter.setOnLongClickListener {
            viewModel.delete(it.id)
            viewModel.load(GuestConstants.FILTER.PRESENT)
        }
    }
}