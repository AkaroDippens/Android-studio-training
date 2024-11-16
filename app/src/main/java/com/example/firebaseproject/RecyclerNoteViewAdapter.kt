package com.example.firebaseproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerNoteViewAdapter(
    private val context: Context,
    private var noteList: List<Note>?,
    private val recyclerViewInterface: RecyclerViewInterface
) : RecyclerView.Adapter<RecyclerNoteViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList!![position]
        holder.noteName.text = note.name
        holder.noteDesc.text = note.desc
        holder.deleteButton.setOnClickListener { recyclerViewInterface.onDeleteClick(note) }
        holder.itemView.setOnClickListener { recyclerViewInterface.onItemClick(note) }
    }

    fun updateData(newNotes: List<Note>) {
        noteList = newNotes
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return noteList!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteName: TextView = itemView.findViewById(R.id.note_name)
        val noteDesc: TextView = itemView.findViewById(R.id.note_desc)
        val deleteButton:ImageButton = itemView.findViewById(R.id.deleteNoteButton)
    }
}

interface RecyclerViewInterface {
    fun onItemClick(note:Note?)
    fun onDeleteClick(note: Note?)
}