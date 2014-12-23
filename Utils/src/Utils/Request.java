package Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author nakim
 */
public class Request implements Serializable
{
    //<editor-fold defaultstate="collapsed" desc="Static variables">
    public static final String SOCK_ERROR = "SOCK_ERROR";
    public static final String NO_COMMAND = "NO_COMMAND";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Private variables">
    private String command;
    private ArrayList<byte[]> args;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Default Request constructor
     */
    public Request()
    {
        this(null);
    }

    /**
     * Request constructor
     * @param command the request command
     */
    public Request(String command)
    {
        this.args = new ArrayList<>();
        this.command = command;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    /**
     * Change Request command
     * @param command the new command
     */
    public final void setCommand(String command)
    {
        this.command = command;
    }

    /**
     * Add a new argument as string
     * @param argument the new string argument
     */
    public void addArg(String argument)
    {
        this.addArg(argument.getBytes());
    }

    /**
     * Add a new argument as byte array
     * @param argument the new byte array argument
     */
    public void addArg(byte[] argument)
    {
        this.args.add(argument);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * Get the request command
     * @return the request command
     */
    public String getCommand()
    {
        return this.command;
    }

    public boolean is(String command)
    {
        return this.command.compareTo(command) == 0;
    }

    /**
     * Get the request argument at specified index
     * @param index the argument's index
     * @return the argument as a byte array
     */
    public byte[] getArg(int index)
    {
        if(this.args.size() <= index)
            throw new IllegalAccessError("Invalid index");

        return this.args.get(index);
    }

    /**
     * Get the request argument at specified index
     * @param index the argument's index
     * @return the argument as a string
     */
    public String getStringArg(int index)
    {
        return BytesConverter.byteArrayToString(this.getArg(index));
    }

    /**
     * Get all request arguments
     * @return all arguments as an array if byte array
     */
    public ArrayList<byte[]> getArgs()
    {
        return this.args;
    }

    /**
     * Get the number of request arguments
     * @return the number of arguments
     */
    public int getArgsCount()
    {
        return this.args.size();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Utils">
    /**
     * Send the requet
     * @param sock the socket
     * @return true if the request has been successfuly send, otherwise false
     */
    public boolean send(Socket sock)
    {
        if(sock == null)
            return false;

        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(sock.getOutputStream());

            out.writeObject(this);
            out.flush();

            return true;
        }
        catch (IOException ex)
        {
            System.err.println(this.getCommand() + " sending error : " + ex);
            return false;
        }
    }

    /**
     * Receive a requet
     * @param sock the socket
     * @return the received request
     */
    public static Request recv(Socket sock)
    {
        if(sock == null)
            return new Request(Request.SOCK_ERROR);

        try
        {
            ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
            Request requ = (Request)in.readObject();

            return requ;
        }
        catch (IOException | ClassNotFoundException ex)
        {
            //System.err.println("Receipt error : " + ex);
            return new Request(Request.NO_COMMAND);
        }
    }

    /**
     * Send the current request and receive a new request
     * @param sock the socket
     * @return the received request
     */
    public Request sendAndRecv(Socket sock)
    {
        if(this.send(sock))
            return recv(sock);
        return new Request(Request.NO_COMMAND);
    }

    /**
     * Send a new request with no argument
     * @param command the request command
     * @param sock the socket
     */
    public static void quickSend(String command, Socket sock)
    {
        Request request = new Request(command);
        request.send(sock);
    }

    /**
     * Send a new request with one argument as string
     * @param command the request command
     * @param argument the request argument
     * @param sock the socket
     */
    public static void quickSend(String command, String argument, Socket sock)
    {
        Request request = new Request(command);
        request.addArg(argument);
        request.send(sock);
    }

    /**
     * Remove all arguments
     */
    public void clearArgs()
    {
        this.args.clear();
    }

    /**
     * Remove all arguments and reset command
     */
    public void reset()
    {
        this.clearArgs();
        this.command = null;
    }
    //</editor-fold>
}
