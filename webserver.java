 /** 
 * Sambulo Senda ID 
 * University of Hertfordshire 
 * Operating System and Computer Networks(HTTP Server Program)
 * Coursework
 */

//My imports 
import java.io.*;
import java.net.*;
import java.util.*;

public class first {
    public static void main(String args[]) throws Exception {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSock=new ServerSocket(port);
          byte buffer [] = new byte [1024];           

        while(true) {
            Socket conn = serverSock.accept();
            Scanner scanin = new Scanner(conn.getInputStream());
            String line=null;
             int nlines=0;
            String strn[] = new String[32];

            while (true) {
                line = scanin.nextLine();
                if(line.length()==0) break;
                strn[nlines] = line;
                nlines = nlines + 1;
            }
            for(int i = 0; i < nlines; i++){
                System.out.print("["+ i + "]" + strn[i]+ "\n ");
            }

            String firstline=strn[0];
            Scanner scan = new Scanner(firstline);
            String command = scan.next();
            String resourcename = scan.next();

            System.out.println("Command:" + command);
            System.out.println("resource name:" + resourcename );

            //Created a String filename which will be the (www) folder with the resourcename 
            String filename = "www"+resourcename;
            

            //prints the file name
            System.out.println(filename);
                
            /**Checks if the file requested exists and Returns a 404 Page of the file does not exist*/
            File requestfile = new File(filename);
            if( ! requestfile.exists()) 
            {
                String reply="HTTP/1.0 404 Not Found\r\n" +
                    "Connection: close\r\n" +
                    "Content-Type: text/html\r\n" +
                    "\r\n" +
                    "Sorry the file that you looking for is not found";

                OutputStream outs = conn.getOutputStream();
                outs.write(reply.getBytes());
                conn.close();
            }

             /** Return the reply of the 200 ok responce and out puts in the web browser that the file that was searched for has been found.*/
            else{
                String reply="HTTP/1.0 200 OK\r\n" +
                    "Connection: close\r\n" +

                    "Content-Type: text/html\r\n" +
		      "\r\n";
		// "The file that is searched for found ";
                OutputStream outs = conn.getOutputStream();
                outs.write(reply.getBytes());
               

            // Optional task the get 501 task 
		//  if(!requestfile("GET")))
		//  {
		//   String reply="HTTP/1.0 501 Not Implemented" +
		//   "Connection: close\r\n" +
			//   "Content-Length: 137"+
		//   "Content-Type: text/html\r\n" +
			// "\r\n" +
		//   "Not implemented";
		//  OutputStream outs = conn.getOutputStream();
                //outs.write(reply.getBytes());


                 InputStream ss = new FileInputStream(requestfile);


                while (true) {
                    int rc = ss.read(buffer,0,1024);                       
                  //the file is then read and then send down the OutPutStream "outs"
                if(rc <= 0) break;
                    outs.write(buffer,0,rc);

                        conn.close();

                    
                }
            }

        }
    }
}