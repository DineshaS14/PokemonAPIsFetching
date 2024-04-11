package com.example.pokemonapisfetching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
/* Adapter for my RecyclerView for Second page Activity
 */
class PokemonAdapter(private val pokemonList: MutableList<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false)
        return PokemonViewHolder(view)
    } // onCreateViewHolder

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        holder.textViewName.text = currentItem.name
        holder.textViewWeight.text = "Wt. " + currentItem.weight
        holder.textViewType.text = "Type/s: " + currentItem.types.toString()
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .placeholder(R.drawable.pokemonimg)
            .error(R.drawable.pokemonimg)
            .into(holder.imageView)
        // clicking on each recyclerview tab as button
        holder.itemView.setOnClickListener {
            val clickedCharacter = pokemonList[position]
            val toastMessage = "Clicked on ${clickedCharacter.name} at position $position"
            Toast.makeText(holder.itemView.context, toastMessage, Toast.LENGTH_SHORT).show()
        } // setOnclickListener for each card of RecyclerView like a button
    } // onBindViewHolder

    override fun getItemCount() = pokemonList.size

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.recyclerviewImageView)
        val textViewName: TextView = itemView.findViewById(R.id.recyclerTypeView) // names flipped
        val textViewType: TextView = itemView.findViewById(R.id.textViewName) // names flipped
        val textViewWeight: TextView = itemView.findViewById(R.id.recyclerWeightView)
    } // Inner class PokemonViewHolder
    // Function to add a new Pokemon to the list
    // Need this to be able to call using the adapter instanced variable
    // to add in Mutable pokemonList
    fun addPokemon(pokemon: Pokemon) {
        pokemonList.add(pokemon)
        notifyItemInserted(pokemonList.size - 1)
    } // addPokemon
} // PokemonAdapter
/* Pokemon class is a data class to put data into pokemonList Mutable
* */
data class Pokemon(
    val name: String,
    val weight: String,
    val types: List<String>,
    val imageUrl: String
) // Pokemon