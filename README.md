# Cocktail App

Cocktail App is an Android app that allows users to explore and discover different cocktails. Users can search for cocktails by name or ingredients, view details about each cocktail, and add cocktails to their favorites list for quick access later.

## Main features

- **Cocktail Search**: Search cocktails by name or ingredients.
- **Show Details**: View detailed information about each cocktail.
- **Manage Favorites**: Add or remove cocktails from the favorites list.
- **API Integration**: Uses an external API to retrieve cocktail data.
- **MVVM Architecture**: Implementation based on Model-View-ViewModel architecture to separate logic from GUI.


Technologies Used

- **Kotlin**: The main programming language.
- **Android SDK**
- **RecyclerView**
- **Firebase Authentication**
- **Cocktail API**
- **Navigation Component**
- **SharedPreferences**
- **Retrofit**: For API calls and data manipulation from the server.
- **Gson**: Serialize and deserialize JSON objects.
- **LiveData** and **ViewModel**: Manage and observe application data.

## How to run the project

1. Clone this repository:
 ```bash
 git clone https://github.com/username/cocktail-app.git
 ```
2. Open the project in Android Studio.
3. Get an API Key for the cocktail service:**
  - You will need an API key to use the cocktail service. Follow these steps to obtain one:
  
  - Visit API Ninjas - API Cocktail.
  - Create a free account or log in if you already have one.
  - Once logged in, navigate to the Cocktail API section.
  - Generate your API key.
  - Save this API key for later.
5. Add the API key in `CocktailApiService.kt` to the line `@Headers("X-Api-Key: YOUR_API_KEY_HERE")`.
6. Run the app on an emulator or physical device.
