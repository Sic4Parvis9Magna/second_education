import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

let stompClient = null;
const handlers = []

export function connect() {
    const socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket)
    stompClient.debug = () => {} // disable logging
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/activity', university => {
            handlers.forEach(handler => handler(JSON.parse(university.body)))
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