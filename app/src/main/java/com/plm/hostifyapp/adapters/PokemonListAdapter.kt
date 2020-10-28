package com.plm.hostifyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.plm.hostifyapp.R
import com.plm.hostifyapp.models.PokemonLink

class PokemonListAdapter(private var context: Context) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    private var dataSet: MutableList<PokemonLink> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dataSet.size > 0) {
            val pokemonLink = dataSet.get(position)
            holder.pokemonName.text = pokemonLink.name

            Glide.with(context)
                .load("""https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemonLink.getId()}.png""")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pokemonImage)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun addPokemonList(pokemonList: List<PokemonLink>) {
        dataSet.addAll(pokemonList)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        var pokemonImage: ImageView = itemView.findViewById<ImageView>(R.id.pokemonImage)
        var pokemonName: TextView = itemView.findViewById<TextView>(R.id.pokemonName)

    }
}