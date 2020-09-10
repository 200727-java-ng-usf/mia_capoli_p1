function getCurrentUserName(){
    var currentUser;
    // Send a GET request ot this endpoint
    var textBlock = document.querySelector("#welcome-message");
    fetch(path)
        // This promise returns the Object in the body
        .then(response => response.json())
        // Then we so something with that Object, called data here
        .then(data => {console.log(data); 

            
                pokemon = data;
                var paragraph = document.querySelector("p");
                // paragraph.textContent = pokemon.abilities[0].ability.name;
                var newElem = document.createElement("img"); 
                newElem.src = pokemon.sprites.front_default;
                //" alt=\"" + textBlock.value)>
                paragraph.appendChild(newElem);
                return;
            
        })
        .catch(exception => console.log(exception));
    }
        

var button = document.querySelector("#pokeButton");
button.addEventListener("click", getPokemonFetch);

