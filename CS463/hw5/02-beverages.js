const url =
  "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic";

let app = document.querySelector("#results");

function formatName(str) {
  const letter = str[0].toLowerCase();
  const vowels = "aeiou";

  return `${vowels.includes(letter) ? "an" : "a"} ${str}`;
}

const fetchData = (url) => {
  fetch(url)
    .then((r) => {
      if (r.ok) {
        return r.json();
      }
    })
    .then((d) => {
      d.drinks.forEach((v) => {
        const drink = document.createElement("div");
        const img = document.createElement("img");
        const name = document.createElement("p");

        img.src = v.strDrinkThumb;
        img.alt = `An image of ${formatName(v.strDrink)}`;
        img.classList.add("drink");
        name.innerText = v.strDrink;

        drink.appendChild(img);
        drink.appendChild(name);
        drink.classList.add("figure");

        app.appendChild(drink);
      });
    })
    .catch((e) => {
      const err = document.createElement("p");
      const errMessage = document.createElement("p");

      err.innerText = "An error occurred. Please try again.";
      err.classList.add("error");

      errMessage.innerText = e;
      errMessage.classList.add("error");

      app.appendChild(err);
      app.appendChild(errMessage);
    })
    .finally((e) => {
      const loading = document.querySelector("#loading");
      loading.remove();
    });
};

fetchData(url);
