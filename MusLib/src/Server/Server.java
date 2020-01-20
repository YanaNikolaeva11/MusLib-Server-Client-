package Server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket clientSocket;          // сокет для общения
   // private static ServerSocket server;          // серверсокет
    /*private static DataInputStream in;            // поток чтения из сокета
    private static DataOutputStream out;           // поток записи в сокет*/
    private static ObjectOutputStream outObject; // поток записи объекта в сокет
    private static ObjectInputStream inObject;   // поток чтение объекта в сокет


    public static void main(String[] args) throws InterruptedException, ClassNotFoundException {
//  стартуем сервер на порту 3345


        try (ServerSocket server = new ServerSocket(3345)){
// становимся в ожидание подключения к сокету под именем - "client" на серверной стороне
            clientSocket = server.accept();

// после хэндшейкинга сервер ассоциирует подключающегося клиента с этим сокетом-соединением
            System.out.print("Connection accepted.");

// инициируем каналы для  общения в сокете, для сервера

            // канал записи в сокет
            /*out = new DataOutputStream(clientSocket.getOutputStream());*/
            System.out.println("DataOutputStream  created");
            outObject = new ObjectOutputStream(clientSocket.getOutputStream());
            // канал чтения из сокета
            /*in = new DataInputStream(clientSocket.getInputStream());*/
            inObject = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("DataInputStream created");

// начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт
            while(!clientSocket.isClosed()){

                System.out.println("Server reading from channel");

// сервер ждёт в канале чтения (inputstream) получения данных клиента
                String entry = inObject.readUTF();
                //Object track = inObject.readObject();
// после получения данных считывает их
                System.out.println("READ from client message - "+entry);
               /*if(entry.equalsIgnoreCase("addtrack")){
                        /*library.createTrack(Model.inputTrack(in));*//*

                }*/

                // и выводит в консоль
                System.out.println("Server try writing to channel");

// инициализация проверки условия продолжения работы с клиентом по этому сокету по кодовому слову       - quit
                if(entry.equalsIgnoreCase("quit")){
                    System.out.println("Client initialize connections suicide ...");
                    outObject.writeUTF("Server reply - "+entry + " - OK");
                    outObject.flush();
                    Thread.sleep(3000);
                    break;
                }

// если условие окончания работы не верно - продолжаем работу - отправляем эхо-ответ  обратно клиенту
                outObject.writeObject("Server reply - "+entry + " - OK");
                System.out.println("Server Wrote message to client.");

// освобождаем буфер сетевых сообщений (по умолчанию сообщение не сразу отправляется в сеть, а сначала накапливается в специальном буфере сообщений, размер которого определяется конкретными настройками в системе, а метод  - flush() отправляет сообщение не дожидаясь наполнения буфера согласно настройкам системы
                outObject.flush();

            }

// если условие выхода - верно выключаем соединения
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            // закрываем сначала каналы сокета !
            inObject.close();
            outObject.close();

            // потом закрываем сам сокет общения на стороне сервера!
            clientSocket.close();

            // потом закрываем сокет сервера который создаёт сокеты общения
            // для возможности поставить этот серверный сокет обратно в ожидание нового подключения

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
