package com.example.android.swapi.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.swapi.data.character.Character
import androidx.recyclerview.widget.RecyclerView
import com.example.android.swapi.R

/**
 *
 * @author Cole Michaels
 * @email cmichaelsd@gmail.com
 * @date 10/24/20
 *
 */
class MainRecyclerAdapter(private val characters: List<Character>, private val itemListener: CharacterItemListener):
        RecyclerView.Adapter<MainRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById<TextView>(R.id.characterNameTextView)
    }

    interface CharacterItemListener {
        fun onCharacterItemClick(character: Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.character_list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val character = characters[position]
        holder.nameText.text = character.name
        holder.itemView.setOnClickListener {
            itemListener.onCharacterItemClick(character)
        }
    }

    override fun getItemCount() = characters.size
}