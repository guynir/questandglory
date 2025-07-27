"use client"

import "./client";
import React, {useEffect, useState} from "react";
import {createGamePlay, startGamePlay, WebSocketClient} from "@/app/client";
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';

enum ConnectionStatus {
    CONNECTING = "CONNECTING",
    CONNECTED = "CONNECTED",
    DISCONNECTED = "DISCONNECTED",
    ERROR = "ERROR"
}

enum ServerMessageTypeEnum {

    TEXT_INPUT = "TEXT_INPUT",
    SHOW_TEXT_MESSAGE = "SHOW_TEXT_MESSAGE",
    REMOVE_COMPONENT = "REMOVE_COMPONENT"

}

enum ClientMessageTypeEnum {

    TEXT_INPUT = "TEXT_INPUT"

}

interface ServerMessage {
    componentKey?: string;
    messageType: ServerMessageTypeEnum;
}

interface RequestUserTextInputMessage extends ServerMessage {
    responseMailboxId: string;
}

interface ShowTextMessageMessage extends ServerMessage {
    message: string;
}

function Home() {
    const [,setClient] = useState<WebSocketClient | null>(null);
    const [connectionStatus, setConnectionStatus] = useState<ConnectionStatus>(ConnectionStatus.DISCONNECTED);
    const [contents, setContents] = useState<Array<React.JSX.Element>>([]);

    useEffect(() => {

        return () => {
        }
    }, []);

    function addItem(item: React.JSX.Element) {
        setContents(contents => [...contents, item]);
    }

    function removeItemByKey(key: string) {
        setContents(contents => contents.filter(item => item.key !== key));
    }

    function connect() {
        if (connectionStatus === ConnectionStatus.DISCONNECTED || connectionStatus === ConnectionStatus.ERROR) {
            setConnectionStatus(ConnectionStatus.CONNECTING);

            createGamePlay().then(response => {
                console.log("Game play ID: " + response.gamePlayId);
                console.log("Client queue ID: ", response.clientQueueId);
                const client = new WebSocketClient();

                client.setOnConnect(() => {
                    console.log("Connected!");
                    console.log("Starting game play with ID: " + response.gamePlayId);
                    startGamePlay(response.gamePlayId).then(() => {
                        console.log("Game play started successfully!");
                    });

                    client.subscribe(response.clientQueueId, (message) => {
                        console.log("Message body: ", message.body);
                        const messageObj: ServerMessage = JSON.parse(message.body);
                        console.log("Message type: ", messageObj.messageType);

                        if (messageObj.messageType === ServerMessageTypeEnum.SHOW_TEXT_MESSAGE) {
                            const textMessage = messageObj as ShowTextMessageMessage;
                            if (textMessage.message.trim().length > 0) {
                                addItem(<div key={messageObj.componentKey}>{textMessage.message}</div>);
                            } else {
                                addItem(<div key={messageObj.componentKey}><br/></div>);
                            }
                        } else if (messageObj.messageType === ServerMessageTypeEnum.TEXT_INPUT) {
                            const textInputMessage = messageObj as RequestUserTextInputMessage;
                            addItem(<TextField
                                key={textInputMessage.componentKey}
                                variant="standard"
                                fullWidth ={true}
                                onKeyDown={event => {
                                    if (event.key === 'Enter') {
                                        const inputValue = (event.target as HTMLInputElement).value;
                                        const destination = "/app/messages/" + response.gamePlayId + "/" + textInputMessage.responseMailboxId;
                                        client.send(destination, {
                                            messageType: ClientMessageTypeEnum.TEXT_INPUT,
                                            text: inputValue
                                        });

                                    }
                                }
                                }
                                autoFocus={true}
                            />);
                        } else if (messageObj.messageType === ServerMessageTypeEnum.REMOVE_COMPONENT) {
                            const removeMessage = messageObj as ServerMessage;
                            removeItemByKey(removeMessage.componentKey!);
                        }
                    });

                    setConnectionStatus(ConnectionStatus.CONNECTED);
                });
                client.setOnStompError(() => {
                    setConnectionStatus(ConnectionStatus.ERROR);
                });

                setClient(client);

                client?.activate();
            });


        }
    }

    let connectionMessage;
    if (connectionStatus === ConnectionStatus.CONNECTING) {
        connectionMessage = <div>Connecting...</div>;
    } else if (connectionStatus === ConnectionStatus.CONNECTED) {
        connectionMessage = <div>Connected!</div>;
    } else if (connectionStatus === ConnectionStatus.DISCONNECTED) {
        connectionMessage = null;
    } else if (connectionStatus === ConnectionStatus.ERROR) {
        connectionMessage = <div>Error connecting to the server.</div>;
    } else {
        connectionMessage = null;
    }

    return (
        <div className={'flex flex-col m-auto h-screen '}>

            <div
                className={'w-1/4 text-black bg-gray-300 h-16 align-middle pt-1 pb-2 flex items-center pl-4 mx-auto rounded-bl-2xl rounded-br-2xl'}>
                <Button variant="outlined" onClick={() => connect()}
                        disabled={connectionStatus != ConnectionStatus.DISCONNECTED}>Connect</Button>
                <span className={'pl-3'}>{connectionMessage}</span>
            </div>

            <div
                className={'m-auto h-screen w-2/3 border-2 border-gray-300 mt-10 mb-10 border-radius-2xl rounded-xl p-2'}>
                {contents}
            </div>
        </div>
    );
}

export default Home;