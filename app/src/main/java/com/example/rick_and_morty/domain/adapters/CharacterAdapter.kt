package com.example.rick_and_morty.domain.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.dispose
import coil.load
import coil.transform.CircleCropTransformation
import com.example.homework_2_6m.R
import com.example.homework_2_6m.databinding.ItemRickAndMortyBinding
import com.example.rick_and_morty.domain.models.Character

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding: ItemRickAndMortyBinding) :
        RecyclerView.ViewHolder(binding.root)

     val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemRickAndMortyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "LogNotTimber")
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.binding.apply {
            characterName.text = character.name
            lastKnownLocation.text = character.location?.name
            firstSeenIn.text = character.origin?.name

            characterImage.load(character.image) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            characterSpeciesAndStatus.text = "${character.status} - ${character.species}"

            colorIndicator.setImageResource(
                when {
                    character.status?.contains("Dead") == true -> R.drawable.ic_circle_red
                    character.status?.contains("Alive") == true -> R.drawable.ic_circle_green
                    else -> R.drawable.ic_circle_grey
                }
            )

            root.setOnClickListener {
                onItemClickListener?.invoke(character)
                Log.d("TAG", "${character.id}")
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Character) -> Unit)? = null

    fun setOnItemClickListener(listener: (Character) -> Unit) {
        onItemClickListener = listener
    }

    fun submitList(list: List<Character>) {
        differ.submitList(list)
    }

    override fun onViewRecycled(holder: CharacterViewHolder) {
        super.onViewRecycled(holder)
        holder.binding.characterImage.dispose()
    }
}
