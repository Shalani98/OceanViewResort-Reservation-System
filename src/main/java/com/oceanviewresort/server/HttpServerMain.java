package com.oceanviewresort.server;

public class HttpServerMain {
    private int host;
    private String port;


    public HttpServerMain(int host,String port){
        this.host=host;
        this.port=port;

    }

    public void start(){
        System.out.println("Starting server at " + host + ":" + port);
    }

    
    public void stop(){
        System.out.println("Stopping server at " + host + ":" + port);
    }


    public static void main(String[] args) {
        HttpServerMain server= new HttpServerMain(8080, "localhost");
        server.start();

        // Add shutdown hook to stop the server gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            server.stop();
        }));
    }

}
