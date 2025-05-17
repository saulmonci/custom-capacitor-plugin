package com.mycompany.plugins.example;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.io.InputStreamByteChunkProvider;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.PrinterShare;
import com.hierynomus.smbj.share.Share;
import com.hierynomus.smbj.auth.AuthenticationContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Base64;


@CapacitorPlugin(name = "Example")
public class ExamplePlugin extends Plugin {

    private Example implementation = new Example();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod()
    public void showAlert(PluginCall call) {
        String value = call.getString("msg");
        JSObject ret = new JSObject();
        ret.put("value", value);
        call.resolve(ret);
    }

    @PluginMethod()
    public void print(PluginCall call) {
        // Recuperar datos del PluginCall
        String host = call.getString("host");
        String shareName = call.getString("shareName");
        String username = call.getString("username");
        String password = call.getString("password");
        String domain = call.getString("domain", ""); // Dominio puede ser opcional
        String dataToPrint = call.getString("dataToPrint");
        // Decodificar el string base64 a bytes
        byte[] dataToPrint64 = Base64.getDecoder().decode(dataToPrint);

        SMBClient client = new SMBClient();

        try (Connection connection = client.connect(host)) {
            AuthenticationContext ac = new AuthenticationContext(username, password.toCharArray(), domain);
            Session session = connection.authenticate(ac);

            try (Share share = session.connectShare(shareName)) {
                if (share instanceof PrinterShare) {
                    PrinterShare printerShare = (PrinterShare) share;

                    // Crear InputStream desde los datos a imprimir
                    InputStream inputStream = new ByteArrayInputStream(dataToPrint64);

                    // Enviar datos a la impresora
                    printerShare.print(inputStream);
                    call.resolve(); // Resuelve la llamada si todo va bien
                } else {
                    call.reject("El recurso compartido no es una impresora.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            call.reject("Error al imprimir: " + e.getMessage());
        }
    }

    @PluginMethod()
    public void printTcp(PluginCall call) {
        String host = call.getString("host");
        String dataToPrint = call.getString("dataToPrint");
        byte[] dataToPrint64 = Base64.getDecoder().decode(dataToPrint);

        int maxRetries = 7;  // Número máximo de intentos
        int retryCount = 0;
        int retryDelay = 2000;  // Tiempo de espera entre intentos (en milisegundos)

        while (retryCount < maxRetries) {
            try(Socket socket = new Socket(host, 9100)){
                OutputStream out = socket.getOutputStream();

                out.write(dataToPrint64);

                out.flush();

                call.resolve();

                return;
            }catch(IOException e){
                retryCount++;
                if (retryCount >= maxRetries) {
                    // Rechazar si se alcanza el número máximo de reintentos
                    call.reject("Error al imprimir tras " + maxRetries + " intentos: " + e.getMessage());
                    return;
                }
    
                // Esperar un tiempo antes de intentar de nuevo
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    call.reject("Error al imprimir: " + ie.getMessage());
                    return;
                }
            }
        } 
    }
}
