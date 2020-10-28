package com.plm.hostifyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plm.hostifyapp.adapters.PokemonListAdapter
import com.plm.hostifyapp.models.PokemonLink
import com.plm.hostifyapp.models.PokemonList
import com.plm.hostifyapp.network.PokemonApiService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var TAG = "Hostify"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var offset = 0
    private var isLoadable = true;
    private val pokemonListAdapter: PokemonListAdapter = PokemonListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val gridLayoutManager = GridLayoutManager(this, 3)

        recyclerView.adapter = pokemonListAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()

                    if (isLoadable) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Llegamos al final")

                            isLoadable = false
                            offset += 20
                            getPokemonData()
                        }
                    }
                }
            }
        })

        getInfoButton.setOnClickListener {
            getPokemonData()
        }
    }

    private fun getPokemonData() {

        val service = retrofit.create<PokemonApiService>(PokemonApiService::class.java)

        service.getPokemonList(20, offset).enqueue(object : Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                isLoadable = true
                if (response.isSuccessful) {
                    val pokemonResponse = response.body()
                    val pokemonList: List<PokemonLink> = pokemonResponse?.results ?: emptyList()

                    pokemonListAdapter.addPokemonList(pokemonList)
                }
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                isLoadable = true
                t.printStackTrace()
            }

        })
    }
}