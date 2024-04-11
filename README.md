# AND101 Project 7 - CYOAPI Part 3: Beautified
New personal Project 

Submitted by: Dinesha Shair

Time spent: 8 hours spent in total

## Summary


Pokémon APIs Fetching is an Android application that utilizes the Pokémon API to provide users with data about various Pokémon. 
Featuring a vibrant theme inspired by the Pokémon universe, the app offers several functionalities. 
Users can fetch information about individual Pokémon by clicking the "Next" button or searching for specific Pokémon using the search bar. 
Additionally, a switch button allows users to toggle between dark and light modes for optimal viewing comfort. 
The app also includes a feature to retrieve data for multiple Pokémon at once, presenting them in a RecyclerView within a secondary activity. 
Seamlessly navigating between activities is made possible with a floating button. 
Powered by technologies such as Kotlin, RecyclerView, and Glide, the app offers an engaging experience for Pokémon enthusiasts.
If I had to describe this project in three (3) emojis, they would be: 😎🥳🤩

## Application Features

<!-- (This is a comment) Please be sure to change the [ ] to [x] for any features you completed.  If a feature is not checked [x], you might miss the points for that item! -->

The following REQUIRED features are completed:

- [x] App contains a RecyclerView that displays a list of scrollable data
- [x] App displays at least two (2) pieces of data for each RecyclerView item
- [x] Use a downloadable font with custom color and size
- [x] Modify the theme of the app in `themes.xml`
- [x] Define and apply at least one style in `themes.xml` 

The following STRETCH features are implemented:

- [x] Update the night theme to use different versions of styles when in dark mode
- [x] Use different drawables when in dark mode

The following EXTRA features are implemented:

- [x] List anything else that you added to improve the app!
  - Implemented a second page
  - Pulling the same API data 3 different ways
    -  Incorporating a typed in to search typed item
  - customized switch button for dark mode on Main Activity
  - adding background images
## Video Demo

Here's a video / GIF that demos all of the app's implemented features:
<https://www.loom.com/share/64b581fec1bb4bcd8b944b02ef9eba65/>

GIF DEMO
<https://i.imgur.com/m1b7x4L.gifv/>



<!-- Recommended tools:
- [Kap](https://getkap.co/) for macOS
- [ScreenToGif](https://www.screentogif.com/) for Windows
- [peek](https://github.com/phw/peek) for Linux. -->

## Notes

Main Activity (MainActivity):

Initialization of UI elements and listeners.
Handling button clicks to fetch Pokemon data and toggle dark mode.
Utilizing AsyncHttpClient to fetch data from the PokeAPI.
Parsing JSON responses and updating UI accordingly.

Pokemon Adapter (PokemonAdapter):

Implementing RecyclerView.Adapter to manage data binding.
Inflating the layout for each item in the RecyclerView.
Binding Pokemon data to views and setting click listeners.

Second Activity (SecondActivity):

Setting up RecyclerView and adapter for displaying Pokemon data.
recyclerView.addItemDecoration(DividerItemDecoration(this@SecondActivity, LinearLayoutManager.VERTICAL))
  class is used to add visual dividers between items in the RecyclerView. 
  This improves the visual separation and organization of the list items, 
  making it easier for users to distinguish between them.
Fetching Pokemon data from the PokeAPI.
Parsing JSON responses and adding Pokemon objects to the adapter.
Providing a floating action button to navigate back to the MainActivity.

Reference of Some Used pictures in the application:

https://www.pokemon.com/static-assets/content-assets/cms2/img/watch-pokemon-tv/seasons/season15/season15_ep36_ss01.jpg
https://www.deviantart.com/amitlu89/art/Who-s-that-Pokemon-254910939



## License

Copyright Dinesha Shair

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
