export interface ChatroomEntity{
    id: number | null;
    timeOfSend: string | null;
    user_sender: any | null;
    user_receiver: any | null;
    text: string | null;
    readMsg: boolean | null;
}