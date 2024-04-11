package com.example.pokemonapisfetching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.Headers
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler

class MainActivity : AppCompatActivity() {

    // Index to keep track of the current Pokémon being displayed
    private var currentIndex = 1
    private lateinit var nextButton: Button
    private lateinit var pokemonNameTextView: TextView
    private lateinit var pokemonImageView: ImageView
    private lateinit var pokemonWeightView: TextView
    private lateinit var pokemonTypeView: TextView
    private lateinit var typedCharacterButton: Button
    private lateinit var recyclerViewButton: Button
    private lateinit var typeInEditText : EditText
    private lateinit var darkModeSwitch : Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // connecting variables to its matched  view items from XML
        nextButton = findViewById<Button>(R.id.nextButton) // 1st screen first button
        pokemonNameTextView = findViewById<TextView>(R.id.pokemonNameTextView)
        pokemonImageView = findViewById(R.id.pokemonImageView)
        pokemonWeightView = findViewById(R.id.weightView)
        pokemonTypeView = findViewById(R.id.typesView)
        typeInEditText = findViewById(R.id.inputTextForChar)
        typedCharacterButton = findViewById(R.id.toSearchType) // 1st screen second button
        recyclerViewButton = findViewById(R.id.recyclerViewFetcher) // 1st screen third button
        darkModeSwitch = findViewById(R.id.switch1)
        // Below all the Listeners and checkers set

        recyclerViewButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        } // setOnClickListener for second page which is recyclerview

        // Assuming you have a button named "nextButton" in your layout
        nextButton.setOnClickListener {
            pokemonImageView.setBackgroundResource(R.drawable.whoisthat)
            getPokemons(currentIndex)
            currentIndex = currentIndex + 1
        } // setOnClickListener for next Button
        typedCharacterButton.setOnClickListener{
            typedCharacterSearch()
        } // setOnClickListener for typed character button

        // Set a listener on the switch to change the theme when toggled
        darkModeSwitch.setOnCheckedChangeListener{ _, isChecked ->
            if (!isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } // if-else
        } // setOnCheckedChangeListener for Theme switch
    } // onCreate

    fun getPokemons(i: Int) {
        val client = AsyncHttpClient()
        val url = "https://pokeapi.co/api/v2/pokemon/$i/"
        client[url, object : JsonHttpResponseHandler() {

            override fun onFailure(
                statusCode: Int,
                headers: okhttp3.Headers?,
                response: String,
                throwable: Throwable?
            ) {
                Log.d("TAG", response)
            } // onFailure

            override fun onSuccess(statusCode: Int, headers: okhttp3.Headers?, json: JSON) {
                Log.d("TAG", json.toString())
                val jsonObject = json.jsonObject
                val name = jsonObject.getString("name")
                val weight = jsonObject.getInt("weight")
                val typeJsonArray = jsonObject.getJSONArray("types")
                val types = mutableListOf<String>()
                for (i in 0 until typeJsonArray.length()) {
                    val typeObject = typeJsonArray.getJSONObject(i)
                    val typeName = typeObject.getJSONObject("type").getString("name")
                    types.add(typeName)
                } // for loop
                val spriteUrl = jsonObject.getJSONObject("sprites").getString("front_default")


                updateUI(name, types.joinToString(", "), weight.toString(), spriteUrl)

            } // onSuccess
        }] // client

    } // getPokemon

    fun typedCharacterSearch() {
        val client = AsyncHttpClient()
        val pokemonCharacter = typeInEditText.text.toString()
        // Check if the EditText is empty
        if (pokemonCharacter.isEmpty()) {
            Toast.makeText(this@MainActivity, "Please enter a Pokemon character name", Toast.LENGTH_LONG).show()
            return
        } // if null
        val url = "https://pokeapi.co/api/v2/pokemon/$pokemonCharacter"
        client[url, object : JsonHttpResponseHandler() {

            override fun onFailure(
                statusCode: Int,
                headers: okhttp3.Headers?,
                response: String,
                throwable: Throwable?
            ) {
                val toastMessage = "Pokemon character name $pokemonCharacter is not Valid"
                Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_LONG).show()
                Log.d("TAG", response)
            } // onFailure

            override fun onSuccess(statusCode: Int, headers: okhttp3.Headers?, json: JSON) {
                Log.d("TAG", json.toString())
                val jsonObject = json.jsonObject
                val name = jsonObject.getString("name")
                val weight = jsonObject.getInt("weight")
                val typeJsonArray = jsonObject.getJSONArray("types")
                val types = mutableListOf<String>()
                for (i in 0 until typeJsonArray.length()) {
                    val typeObject = typeJsonArray.getJSONObject(i)
                    val typeName = typeObject.getJSONObject("type").getString("name")
                    types.add(typeName)
                }
                val spriteUrl = jsonObject.getJSONObject("sprites").getString("front_default")


                updateUI(name, types.joinToString(", "), weight.toString(), spriteUrl)

            }
        }] // client
    } // typedCharacterSearch

    private fun parsePokemon(json: JsonHttpResponseHandler.JSON): Pokemon {
        val jsonObject = json.jsonObject
        val name = jsonObject.getString("name")
        val weight = jsonObject.getInt("weight").toString()
        val typesArray = jsonObject.getJSONArray("types")
        val types = mutableListOf<String>()
        for (i in 0 until typesArray.length()) {
            val typeObject = typesArray.getJSONObject(i)
            val typeName = typeObject.getJSONObject("type").getString("name")
            types.add(typeName)
        }
        val spriteUrl = jsonObject.getJSONObject("sprites").getString("front_default")

        return Pokemon(name, weight, types, spriteUrl)
    } // parsePokemon
     fun updateUI(name: String, types: String, weight: String, spriteUrl: String) {
        // Update TextView and ImageView here
        pokemonNameTextView.text = name
        pokemonWeightView.text = "Weight: "+ weight
        pokemonTypeView.text = "Type: " +types
        Glide.with(this)
            .load(spriteUrl)
            .placeholder(R.drawable.pokemonimg)
            .error(R.drawable.pokemonimg)
            .into(pokemonImageView)
    } // updateUI
} // MainActivity

