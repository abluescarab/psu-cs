function formatName(str) {
  const letter = str[0].toLowerCase();
  const vowels = "aeiou";

  return `${vowels.includes(letter) ? `an` : `a`} ${str}`;
}

function addDrink(drink, app) {
  const parent = $("<div></div>", {
    class: "figure",
  }).appendTo(app);

  $("<img/>", {
    src: drink.strDrinkThumb,
    alt: `An image of ${formatName(drink.strDrink)}`,
    class: "drink",
  }).appendTo(parent);

  $("<p></p>", {
    text: drink.strDrink,
  }).appendTo(parent);
}

function displayError(err, app) {
  const error = $("<p></p>", {
    class: "error",
    text: "An error occurred. Please try again.",
  }).appendTo(app);

  error.clone().text(err).appendTo(app);
}

$(document).ready(function () {
  const url =
    "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic";
  const app = $("#results");

  // uses new jquery done/fail/always syntax
  const fetchData = (url) => {
    $.ajax({
      type: "GET",
      url: url,
    })
      .done((data) => {
        if (!data) {
          displayError("No data returned", app);
        } else {
          data.drinks.forEach((v) => addDrink(v, app));
        }
      })
      .fail((e) => {
        displayError(e, app);
      })
      .always(() => $("#loading").remove());
  };

  fetchData(url);
});
