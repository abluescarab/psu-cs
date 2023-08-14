// Add your code here
let form = document.querySelector("form");
form.addEventListener("submit", event => {
  event.preventDefault();

  if(form.elements.username.value === "" &&
     form.elements.email.value === "" &&
     form.elements.newsletter.value === "" &&
     form.elements.date.value === "") {
    console.warn("You must enter some data to submit this form");
    return;
  }

  console.log("========== Form Submission ==========");

  console.log(form.elements.username.value === "" ?
    "    Username:   No submission" :
    "    Username:   " + form.elements.username.value
  );

  console.log(form.elements.email.value === "" ?
    "    Email:      No submission" :
    "    Email:      " + form.elements.email.value
  );

  console.log(form.elements.newsletter.value === "" ?
    "    Newsletter: No submission" :
    "    Newsletter: " + form.elements.newsletter.value
  );

  console.log(form.elements.date.value === "" ?
    "    Date:       No submission" :
    "    Date:       " + form.elements.date.value
  );
});
