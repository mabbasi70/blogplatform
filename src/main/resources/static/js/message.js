'use strict'

const userDisplay = document.querySelector('.people');
const chatDisplay = document.querySelector('.chat-display');
const userId = document.querySelector("input[name='senderId']")?.value;
const receiverId = document.querySelector("input[name='receiverId']")?.value;
const userList = document.querySelector(".puser-list");
let messageCurrentPage = 0; 
let usersCurrentPage = 0;
let isLoadingUsers = false;

document.addEventListener('DOMContentLoaded', function() {
    if(userId && receiverId) loadMessages();
    if(userId) loadUsers();
});


function loadMessages() {
    fetch(`/api/messages/${userId}/${receiverId}?page=${messageCurrentPage}&size=12`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
       
            return response.json();
        })
        .then(messages => {
            if (messages.length > 0) {
                prependMessages(messages);
                messageCurrentPage++; 
            }
        })
        .catch(error => console.error('Error loading messages:', error));
}
function prependMessages(messages) {
    const oldScrollHeight = chatDisplay.scrollHeight;
    messages.forEach(message => {
        const messageElement = document.createElement('div');
        messageElement.className = message.sender.id === parseInt(userId, 10) ? 'message current-user' : 'message other-user';
        messageElement.innerHTML = `<p>${message.text}</p>`;
        chatDisplay.insertBefore(messageElement, chatDisplay.firstChild);
    });
    const newScrollHeight = chatDisplay.scrollHeight;
    chatDisplay.scrollTop = newScrollHeight - oldScrollHeight;
}

chatDisplay.addEventListener('scroll', function() {
    if (chatDisplay.scrollTop === 0) {
        loadMessages();
    }
});


function loadUsers(){
    if(isLoadingUsers) return;
    isLoadingUsers = true;
    fetch(`/api/messages/${userId}?page=${usersCurrentPage}&size=15`)
    .then(response => {
        if(!response.ok){
            throw new Error('Response was not ok');
        }
        return response.json();
    })
    .then(users =>{
        if(users.length>0){
           
            appendUsers(users);
            usersCurrentPage++;
            isLoadingUsers = false;
           
        }
    })
    .catch(error =>{
        isLoadingUsers = false;
        console.error('Error loading message:', error)
    }  
        );
}

function appendUsers(users){
    users.forEach(user=>{
        userList.insertAdjacentHTML('beforeend',`<li><a href="/messages/${userId}/${user.id}">${userId == user.id ? 'You' : user.email.split('@')[0]}</a></li>`);
    })
}

userDisplay.addEventListener('scroll', () => {
    if (userDisplay.scrollTop + userDisplay.clientHeight >= userDisplay.scrollHeight - 10) {
        loadUsers();  
    }
});



// web socketss


var stompClient = null;
var socket = null;
document.addEventListener("DOMContentLoaded", function(event) { 
    connect();
});



function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/user/queue/reply', function(messageOutput){
            showMessage(JSON.parse(messageOutput.body));
        });
    }, function(error) {
        console.log('Connection error: ' + error);
        setTimeout(connect, 5000); 
    });
}


function sendMessage(event) {
    var messageContent = document.getElementById('messageInput').value;
    if (messageContent && stompClient) {
        var chatMessage = {
            sender:{
                id: +userId,
            } ,
            receiver:{
                id: +receiverId ,
            } ,
            text: messageContent,
        };
        stompClient.send("/app/chat.private", {}, JSON.stringify(chatMessage));
        stompClient.send("/app/chat.private/" + receiverId, {}, JSON.stringify(chatMessage));
        document.getElementById('messageInput').value = '';
    }
    event.preventDefault();
}


function showMessage(message) {
    const messageElement = document.createElement('div');
    messageElement.className = message.sender.id === parseInt(userId, 10) ? 'message current-user' : 'message other-user';
    messageElement.innerHTML = `<p>${message.text}</p>`;
    chatDisplay.insertAdjacentElement('beforeend', messageElement)
}


document.getElementById('messageForm').addEventListener('submit', sendMessage);