import {Client, IFrame} from "@stomp/stompjs";
import axios, {AxiosResponse} from "axios";

export class WebSocketClient {
    private client: Client;
    private onConnect: () => void = () => {
    };
    private onStompError: (frame: any) => void = (frame) => {
    };
    private clientQueueId: string | null = null;

    constructor() {
        this.client = new Client({
            brokerURL: 'ws://' + location.host + '/ws',
            onConnect: () => {
                console.log("Connected to WebSocket server!");
                this.onConnect();
            },
            onStompError: (frame: IFrame) => {
                this.onStompError(frame)
            }
        });
    }

    public setOnConnect(callback: () => void) {
        this.onConnect = callback;
    }

    public setOnStompError(callback: (frame: any) => void) {
        this.onStompError = callback;
    }

    public subscribe(destination: string, callback: (message: any) => void) {
        this.client.subscribe(destination, (message) => {
            callback(message);
        });
    }

    public send(destination: string, body: any) {
        if (!this.client.connected) {
            console.error("Cannot send message, client is not connected.");
            return;
        }
        this.client.publish({destination: destination, body: JSON.stringify(body)});
    }

    activate() {
        console.log("Activating WebSocket client...");
        this.client.activate();
    }
}

export interface GamePlayCreateResponse {
    gamePlayId: string;
    clientQueueId: string;
}

export interface Language {
    isoCode: string;
    displayNameEnglish: string;
    displayNameLocal: string;
    ltr: boolean;
}

export const ENGLISH = { isoCode: "eng", displayNameEnglish: "English", displayNameLocal: "English", ltr: true };

export async function createGamePlay(): Promise<GamePlayCreateResponse> {
    const response: AxiosResponse<GamePlayCreateResponse> = await axios.post("/api/v1/gameplay/");
    console.log(response);
    return response.data;
}

export async function startGamePlay(gamePlayId: string) {
    const response: AxiosResponse<GamePlayCreateResponse> = await axios.post("/api/v1/gameplay/" + gamePlayId + "/start");
    console.log(response);
}

export async function getSupportedLanguages(): Promise<Array<Language>> {
    const response: AxiosResponse<Array<Language>> = await axios.get("/api/v1/languages");
    return response.data;
}

export async function setGamePlayLanguage(gamePlayId: string, isoCode: string) {
    return axios.post("/api/v1/gameplay/" + gamePlayId + "/language", {
        isoCode: isoCode
    });
}