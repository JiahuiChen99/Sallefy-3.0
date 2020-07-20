# Sallefy
Sallefy is a cloud streaming platform. The front-end is created with Java and the data fetched using [Arnau Garcia](https://github.com/arnaugarcia)'s API: 
> https://github.com/arnaugarcia/sallefy

<p align="center">
<b>【YouTube Demo】</b>
</p>

  
[<p align="center"><img src="https://user-images.githubusercontent.com/37770349/87924639-73c26b00-ca7f-11ea-8850-9a40e9a7b6c5.png" width="50%"> </p>](https://youtu.be/1IY30NCmF6c)



## Sallefy Official Logo & Core UI Design Mockups

<p align="center">
<img src="https://user-images.githubusercontent.com/37770349/78971534-19268400-7b0c-11ea-9689-d0eba7dccd5d.png" width="150">
</p>

## Track UI

When a song is selected, this UI will be presented.
The current song thumbnail will be displayed in the middle of the page, also, details about the song are also displayed. For instance, the track name and the artist.

- There are some music controls for playing/pause the track + progress modifiers.
- The user will also be able to go back to the previous song or skip to the next one if desired.
- Like button is avaible for the user to like the song.
- Plus button is avaible for the user to add the song to an owned playlist.
- Share button is available for the user to share some joy to the world.
- Download button is available for the user to download the song.

More details or actions can be selected if the options button is pressed on the right top corner.

> We will also try to integrate Chromecast to our app to play the specified song in a Chromecast enabled device. A CAF (Cast Application Framework) might be developed, otherwise the Default Media Receiver will be used.

Graphical waveform will appear in the bottom of the interface.
<p align="center">
<img src="https://user-images.githubusercontent.com/37770349/78459167-77d8a180-76b7-11ea-8ee7-c058df5f6142.png" width="310">
</p>


## Explore Section UI
Once the user is signed in, it will be redirected to the first page of the footer tabs [Explore].
This page will present those tracks that the user has recently listened to. It will also provide some recommendations and a section of artists that the user might like based on his/her tastes.
These sections will be swipeable.

If any song is currently playing/paused it will be displayed on top of the footer tabs. The user will be able to carry on listening to the song or halt it with the control button on the right. If the the section is pressed it will open the modal of the song.
A little detail to take into consideration is the ability to display a progress bar of the song on top of this section.

On the bottom, there are different pages for the user to select from [Explore - Search - Library - Profile]
<p align="center">
<img src="https://user-images.githubusercontent.com/37770349/78459151-5a0b3c80-76b7-11ea-9b15-35d094d4c076.png" width="310"></p>


## Search Section UI
User can search through all the database looking for new artists, playlists, songs or by genre.

As for now it will contain, these filters:

**CORE FEATURES**
- Search for a specific song | Retrieve all the songs from the database if no data is provided
- Search for an artist | Retrieve all artists if no data is provided
- Search for a specific playlist | Shows all playlists if no data is provided
- Search for a genre | Shows all genres if no data is provided


<p align="center">
<img src="https://user-images.githubusercontent.com/37770349/79155099-e95bd280-7dd0-11ea-96e7-34c95087fd78.png" width="310"> <img src="https://user-images.githubusercontent.com/37770349/79155152-009ac000-7dd1-11ea-88be-627972b9e7d2.png" width="310">
</p>

## Library Section UI

The library section will present user's data, there will be different tabs for the user to be selected from. These are Playlists - Songs Liked - Artist
For instance, if Playlists tab is selected it will show all those playlists that the current user has. The albums cover will be presented as a cover flow and the selected one will be expanded, highlighting the selected one. Its tracks will be displayed underneath.

- Library tabs
- Albums cover 
- Tracks from the selected album

<p align="center">
<img src="https://user-images.githubusercontent.com/37770349/78974287-f39c7900-7b11-11ea-8aa6-9ff49fc86507.png" width="310">
</p>


## User Profile Section UI
The user profile section will present an artist data.
The information revealed at the top of the view will be the artist stage name, first name, last name and country.

Underneath it will reveal information related to that account, for instance the followers and following, there will be a button to follow that account.

At the bottom of the page it will display the artist's albums and songs.

If the user is viewing his/her own user profile there will be a floating button that will display different options for the user to perform such as creating a new track or a new playlist.

**CORE FEATURES**

- [x] User profile data【#24】
- [x] Account followers information 【#24】
- [x] Artist albums 【#22】
- [x] Artist songs【#23】

**FANCY STUFF**
> Cover Flow 【#14】
> Boom Button
> Some fancy UI styling artifacts running in the background

<p align="center">
<img src="https://user-images.githubusercontent.com/37770349/79638676-0a347700-8187-11ea-89b0-17d9110005bc.png" width="350">
</p>
