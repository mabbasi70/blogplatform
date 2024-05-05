"use strict";



document.getElementById('imageFile')?.addEventListener('change', function(event) {
    var reader = new FileReader();
    reader.onload = function() {
        var output = document.getElementById('imagePreview');
        output.src = reader.result;
        output.style.display = 'block'; // Make the image visible
    };
    reader.readAsDataURL(event.target.files[0]);
});


const loginModal = document.querySelector(".modal");
const loginCancelIcon = document.querySelector(".icon");
if(loginModal){
    loginCancelIcon.addEventListener("click", (event)=>{
        loginModal.style.display = "none";
    });
}

document.querySelector(".hamburger-link")?.addEventListener('click',(event)=>{
    let mobileMenu = document.querySelector('.menu-mobile');
    let icon = document.querySelector('.hamburger');
    mobileMenu.classList.toggle("--menu-mobile--open");

    if (icon.classList.contains('fa-bars')) {
        icon.classList.remove('fa-bars');
        icon.classList.add('fa-xmark');
     } else {
        icon.classList.remove('fa-xmark');
        icon.classList.add('fa-bars');
     }
    
    
});
