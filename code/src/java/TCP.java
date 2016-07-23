package java;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by apple on 16/7/24.
 */
public class TCP {
    public void ReceiveMsg() {
        ServerSocket server = null;
        Socket socket = null;
        try {
            server = new ServerSocket(8888);// 创建一个ServerSocket对象，并让这个Socket在8080端口监听
            // 调用ServerSocket的accept()方法，接受客户端所发送的请求，同时创建一个Socket对象
            // 如果客户端没有发送数据，那么该线程就停滞不继续,也就是阻塞
            while(true){
                socket = server.accept();
                System.out.println(socket.getInetAddress().getHostName());
                System.out.println(socket.getInetAddress().getHostAddress());//得到当前发送数据Socket对象的主机名和ip地址
                InputStream input = socket.getInputStream();// 得到该Socket对象的输入流
                BufferedInputStream bis = new BufferedInputStream(input);
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = bis.read(b)) != -1) {// 从InputStream当中读取客户端所发送的数据
                    System.out.println(new String(b, 0, len,"UTF-8"));
                }
                //--------向客户端的返回信息-------------
                socket.shutdownInput();//结束读取
                OutputStream outputResult = socket.getOutputStream();//不需要关闭
                outputResult.write("ok,我已经收到！".getBytes());

                bis.close();//关闭缓存输入流，注意，输入流input不需要关闭，因为它只是在Socket中得到输入流对象，并没有创建
                socket.close();//接收这个Socket的数据后释放资源，因为每一次客户端发送数据都会在服务端创建一个Socket对象，注意ServerSocket不应该关闭，因为这是服务器ServerSocket对象，关闭了客户端就不能发送数据了
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //TCP向服务端发送数据
    public void TCP_sendMsg(String msg) {
        Socket socket = null;
        OutputStream output = null;
        InputStream input = null;
        try {
            // socket = new Socket(InetAddress.getByName("192.168.1.100"), 8888);//这种形式也行
            socket = new Socket("192.168.1.100", 8888);// 第一个参数是目标主机名或目标主机的ip地址，第二个参数是目标主机端口号
            output = socket.getOutputStream();
            output.write(msg.getBytes());// 把msg信息写入输出流中
            //--------接收服务端的返回信息-------------
            socket.shutdownOutput(); // 一定要加上这句，否则收不到来自服务器端的消息返回 ，意思就是结束msg信息的写入
            input = socket.getInputStream();
            byte[] b = new byte[1024];
            int len = -1;
            StringBuffer sb = new StringBuffer();
            while ((len = input.read(b)) != -1) {
                sb.append(new String(b, 0, len));// 得到返回信息
            }
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 注意，输出流不需要关闭，因为它只是在Socket中得到输出流对象，并没有创建
                if (socket != null) {
                    socket.close();// 释放资源，关闭这个Socket
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
