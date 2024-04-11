package com.example.pokemonapisfetching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
/* Class is for Second page where RecyclerView is held
*/
class SecondActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter // to reuse the same adapter
    private lateinit var backButton : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.secondview)
        backButton = findViewById(R.id.fabBackToMain)
        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.characterRecyclerView)
        pokemonAdapter = PokemonAdapter(mutableListOf())
        recyclerView.addItemDecoration(DividerItemDecoration(this@SecondActivity, LinearLayoutManager.VERTICAL))
        // Set layout manager and adapter to RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pokemonAdapter

        // Call a function to fetch and display data in the RecyclerView
        catchAllpokemons()
        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } // setOnClickListener for floater backButton

    } // AppCompatActivity

    // Function to fetch and display data in the RecyclerView
    // Implement this function according to your data fetching logic
    private fun catchAllpokemons() {
        val client = AsyncHttpClient()
        val url = "https://pokeapi.co/api/v2/pokemon?limit=200&offset=0"

        client[url, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: okhttp3.Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.d("ALLCATCHPOKEMON", response)
            } // onFailure

            override fun onSuccess(statusCode: Int, headers: okhttp3.Headers?, json: JSON) {
                Log.d("FROMALLCATCHPOKEMON", json.toString())
                try {
                    val jsonObject = json.jsonObject
                    val results = jsonObject.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val item = results.getJSONObject(i)
                        val pokemonUrl = item.getString("url")
                        fetchPokemonData(client, pokemonUrl)
                    }
                } catch (e: JSONException) {
                    Log.e("JSON Parsing Error", "Error parsing JSON", e)
                } // try-catch
            } // onSuccess
        }]
    } // catchAllPokemon

    private fun fetchPokemonData(client: AsyncHttpClient, url: String) {
        client[url, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: okhttp3.Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("Pokemon Fetch Error", "Failed to fetch Pokemon data")
            } // onFailure

            override fun onSuccess(statusCode: Int, headers: okhttp3.Headers?, json: JSON) {
                Log.d("Pokemon Fetch Success", json.toString())
                try {
                    val pokemon = parsePokemon(json)
                    // Add the fetched Pokemon to the adapter
                    pokemonAdapter.addPokemon(pokemon)
                } catch (e: JSONException) {
                    Log.e("JSON Parsing Error", "Error parsing Pokemon data", e)
                } // try-catch
            } // onSuccess
        }]
    } // fetchPokemonData

    fun parsePokemon(json: JsonHttpResponseHandler.JSON): Pokemon {
        val jsonObject = json.jsonObject
        val name = jsonObject.getString("name").toUpperCase()
        val weight = jsonObject.getInt("weight").toString()
        val typesArray = jsonObject.getJSONArray("types")
        val types = mutableListOf<String>()
        for (i in 0 until typesArray.length()) {
            val typeObject = typesArray.getJSONObject(i)
            val typeName = typeObject.getJSONObject("type").getString("name")
            types.add(typeName)
        } // for loop
        val spriteUrl = jsonObject.getJSONObject("sprites").getString("front_default")

        return Pokemon(name, weight, types, spriteUrl)
    } // parsePokemon
} // SecondActivity
