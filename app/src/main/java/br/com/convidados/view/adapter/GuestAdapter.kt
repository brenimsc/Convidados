package br.com.convidados.view.adapter

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.convidados.R
import br.com.convidados.service.model.GuestModel

class GuestAdapter : RecyclerView.Adapter<GuestAdapter.GuestViewHolder>() {

    private var guestList: List<GuestModel> = arrayListOf()
    private var onClick: ((guest: GuestModel) -> Unit)? = null
    private lateinit var onLongclick: (guest: GuestModel) -> Unit

    fun setOnClickListener(onClick: (guest: GuestModel) -> Unit) {
        this.onClick = onClick
    }

    fun setOnLongClickListener(onLongClick: (guest: GuestModel) -> Unit) {
        this.onLongclick = onLongClick
    }

    companion object {
        var bind = 0
        var create = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        create++
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view, onClick, onLongclick)
    }

    override fun getItemCount(): Int {
        return guestList.size
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        bind++
        val guest = guestList[position]
        holder.bind(guest)
    }

    fun updateGuests(list: List<GuestModel>) {
        guestList = list
        notifyDataSetChanged()
    }


    inner class GuestViewHolder(
        itemView: View,
        val onClick: ((guest: GuestModel) -> Unit)?,
        val onLongClick: (guest: GuestModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        val textName = itemView.findViewById<TextView>(R.id.text_convidado)

        fun bind(guest: GuestModel) {
            Log.e("BRENOL", "${guest.name}")
            itemView.setOnClickListener {
                onClick?.let {
                    it(guest)
                }
            }
            itemView.setOnLongClickListener {
                AlertDialog.Builder(itemView.context)
                    .setTitle(R.string.remocao_convidado)
                    .setMessage(R.string.deseja_remover)
                    .setPositiveButton(R.string.remover) { dialog, whitch ->
                        onLongClick(guest)
                    }
                    .setNeutralButton(R.string.cancelar, null)
                    .show()
                true
            }
            textName.text = guest.name
        }

    }


}