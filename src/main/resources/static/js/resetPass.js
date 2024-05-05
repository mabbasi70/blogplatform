'use strict'


const email = document.getElementById("email");
const emailForm = document.querySelector(".password-reset-form");
const emailError = document.getElementById('emailError');
const codeError = document.getElementById('codeError');
const passwordsError = document.getElementById('passwordError');
const codeInputForm = document.getElementById('codeInputContainer');
const codeInputs = document.querySelectorAll('.code-input');
const newPassword = document.getElementById('newPasswordContainer');




async function tokenMatch(token, email){
    const url =  `/password-reset/${encodeURIComponent(email)}/send/check`;
    const response = await fetch(url,{
        method:"POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ token: token }),

    })
 
    if(!response.ok){
        console.log('not ok')
        return null
    }
    const data = await response.json();
    console.log("before return")
    return data;
}

async function sendTokenToEmail(email){
    const url = `/password-reset/${encodeURIComponent(email)}/send`;

    const response = await fetch(url,{
        method:"POST"
    });
    if(!response.ok){
        return null;
    }
    const data = await response.json();
    return data;
}

async function fetchUserByEmail(email){
    const url = `/password-reset/${encodeURIComponent(email)}`;
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`HTTP status is not OK: ${response.status}`);
        }
        const data = await response.json();  
        return data;
    } catch (error) {
        return null;
    }
}

// async function changePassword(email, password, confirmPassword) {
//     const url = `/password-reset/${encodeURIComponent(email)}/send/check/change`;
//     try {
//         const response = await fetch(url, {
//             method: "POST",
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({ password: password, confirmPassword: confirmPassword })
//         });
//         if (!response.ok) throw new Error('Failed to change password');
//         // Handle successful password change, e.g., redirect or display a success message
//         console.log('Password changed successfully!');
//     } catch (error) {
//         console.error('Error changing password:', error);
//     }
// }

async function changePassword(email, password, confirmPassword) {
    const url = `/password-reset/${encodeURIComponent(email)}/send/check/change`;
    try {
        const response = await fetch(url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ password: password, confirmPassword: confirmPassword })
        });
        const data = await response.json();
        if (response.ok) {
            // Redirect if password change was successful
            window.location.href = data.redirectUrl;
        } else {
            // Handle errors, e.g., passwords do not match or server error
            console.error('Error changing password:', data.message);
        }
    } catch (error) {
        console.error('Network or other error:', error);
    }
}


function moveFocus(currentElement, nextElement) {
    if (currentElement.value.length === 1 && nextElement) {
        nextElement.focus();  
    }
}


codeInputForm.addEventListener('submit', async (event)=>{
    event.preventDefault();
    let token ='';
    codeInputs.forEach( char =>{
        token = token + String(char.value);
    })
    if(!token.trim().length === 0){
        return;
    }
    const isMatch = await tokenMatch(token, email.value);
    console.log(isMatch)
    if(!isMatch){
        passwordsError.textContent = "Email has not sent please try again later."
        return;
    }
    newPassword.style.display = 'flex';


})

emailForm.addEventListener('submit',async (event)=>{
    event.preventDefault();
    const user = await fetchUserByEmail(email.value);
    if(!user){
       emailError.textContent = "There is no account with this email."
        return;
    }
    emailError.textContent = '';
    const isSend = await sendTokenToEmail(email.value)
    if(!isSend){
        codeError.textContent = "Email has not sent please try again later."
        return;
    }
    codeError.textContent = '';
    codeInputContainer.style.display = 'flex';

})

document.getElementById('newPasswordContainer').addEventListener('submit', async (event) => {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    await changePassword(email, newPassword, confirmPassword);
});
