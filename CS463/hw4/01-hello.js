// Add your code here
// create header
let header = document.createElement("h1");
header.innerText = "About Me";
header.style.textAlign = "center";

// create image
let imageContainer = document.createElement("div");
imageContainer.style.textAlign = "center";

let image = document.createElement("img");
image.setAttribute("src", "../images/alana.jpg");
image.setAttribute("alt", "headshot of Alana");

image.style.borderRadius = "50%";
image.style.objectFit = "cover";
image.style.width = "200px";
image.style.height = "200px";

imageContainer.appendChild(image);

// create blurb
let boldSpan = document.createElement("span");
boldSpan.innerText = "Hello, my name is Alana. ";
boldSpan.style.fontWeight = "bold";

let normalSpan = document.createElement("span");
normalSpan.innerText = "I'm a senior computer science " +
                       "student at Portland State University. I enjoy playing " +
                       "video games, reading, and watching movies with my best " +
                       "friend.";

let blurb = document.createElement("p");
blurb.appendChild(boldSpan);
blurb.appendChild(normalSpan);

// append elements to main
const main = document.querySelector("main");
main.style.width = "50vw";
main.style.margin = "0 auto";
main.style.fontFamily = "Open Sans, Helvetica Neue, sans-serif";
main.style.fontSize = "1.2em";

main.appendChild(header);
main.appendChild(imageContainer);
main.appendChild(blurb);
