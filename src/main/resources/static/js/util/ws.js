import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

var stompClient = null;
const handlers = []

export function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/activity', university => {
            handlers.forEach(handler => handler(JSON.parse(university.body)))
            // showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

export function sendUniversity(university) {
    stompClient.send("/app/changeUniversity", {}, JSON.stringify(university));
}