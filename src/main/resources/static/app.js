let stompClient = null;
let currentUser = null;
connect();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        $("#recipientId").show();

    } else {
        $("#conversation").hide();
        $("#recipientId").hide();
    }
}


async function getCurrentUser() {
    await fetch('api/users/me')
        .then(function (response) {
            response.json()
                .then(data => {
                    currentUser = data;
                    console.log(data)
                    authUser(data.username);
                })
        })
}



async function connect() {

    await getCurrentUser();
    getAllUsers();

    let socket = new SockJS('/gs-guide-websocket');

    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        stompClient.subscribe(
            '/user/' + currentUser.id + '/queue/messages',
            function (messageDto) {

                const data = JSON.parse(messageDto.body);
                console.log(data);
                if (data.senderId === currentUser.id || data.senderId === $("#recipientId").val()) {
                    // appendNewMsg(data);
                } else
                    appendNewMsg(data);
                    // alert("new msg : " + data.text + " from " + data.senderName)

            }
        );
        stompClient.subscribe(
            '/user/' + currentUser.id + '/contact',
            function (){
                getAllUsers()
            }
        )
    });
}


function receiverChangeHandle(selectedObj) {
    let selectedReceiverId = selectedObj.value;
    getMessagesByReceiverId(selectedReceiverId)
}


function getAllUsers() {
    fetch("/api/users")
        .then(function (response) {
                if (response.ok) {
                    response.json().then(res => {
                        $("#recipientId").empty()
                        console.log(res);
                        res.map(user => {
                            let result = user.id === currentUser.id ? "Message to myself" : user.username;
                            $("#recipientId").append(
                                "<option value=" + user.id + ">" + result  + "</option>"
                            );
                        })
                        let user1 = res[0];
                        getMessagesByReceiverId(user1.id)

                    })
                }
            }
        )
}


function getMessagesByReceiverId(receiverId) {
    fetch("/api/messages/get-by-receiver-and-sender-id/" + receiverId)
        .then(function (response) {
                if (response.ok) {
                    response.json().then(res => {
                        console.log(res);
                        $("#messages").empty()
                        res.map(value => {
                            appendNewMsg(value)
                        })
                    })
                }
            }
        )
}



function sendText() {

    let dataBodyStr = JSON.stringify(
        {
            'title': $("#title").val(),
            'body': $("#text").val(),
            'recipientId': $("#recipientId").val(),
            'recipientName': $("#recipientName").val(),
            'senderId': currentUser.id,
            'senderName': currentUser.username
        });
    stompClient.send(
        "/app/send-message",
        {},
        dataBodyStr
    )

    appendNewMsg(JSON.parse(dataBodyStr))
    document.getElementById("title").value = "";
    document.getElementById("text").value = "";
}


function appendNewMsg(message) {
    const tdAlign = message.senderId === currentUser.id ? 'right' : 'left';

    $("#messages").append("<tr>" +
        "<td align='" + tdAlign + "'>" +
        "<h6>" + message.senderName + "</h6>" +
        "<h3>" + message.title + " - " + message.body + "</h3>" +
        "</td>" +
        "</tr>");
}


function authUser(user) {

    $("#authUser").append( "Welcome - "+ user);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#send").click(function () {
        sendText();
    });
});

