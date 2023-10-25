let bigImage = document.getElementById("big-img");

function myTshirt(image) {
    // Get the image filename from the data-image attribute
    var imageName = image.getAttribute("data-image");
    // Construct the full image URL based on the filename
   var imageUrl = "/static/images/" + imageName;
    // Set the image src
    bigImage.src = imageUrl;
}

document.addEventListener("DOMContentLoaded", function() {
            const form = document.getElementById("forgotPasswordForm");
            const newPasswordInput = form.querySelector("#newPassword");
            const confirmPasswordInput = form.querySelector("#confirmPassword");
            const passwordError = form.querySelector("#passwordError");

            form.addEventListener("submit", function(event) {
                if (newPasswordInput.value !== confirmPasswordInput.value) {
                    passwordError.textContent = "Password did not match";
                    event.preventDefault();
                } else {
                    passwordError.textContent = ""; // Clear the error message if passwords match
                }
            });
        });

 



