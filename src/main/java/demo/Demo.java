package demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Demo {
    private int port = 2345;
    private String serverAddress = "localhost";

    private WireMockServer wireMockServer = null;

    public Demo(){
        wireMockServer = new WireMockServer(port);
        configureFor(serverAddress, port);
    }

    public void startServer(){
        wireMockServer.start();
    }

    public void stopServer(){
        wireMockServer.stop();
    }


    //curl localhost:2345/api/demo1
    public void stubForGet(){
        stubFor(get(urlMatching("/api/demo1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withStatusMessage("Everything was just fine!")
                        .withHeader("Content-Type", "text/plain")
                        .withBody("Stub For demo1(Java)")));
    }

    //curl -X POST localhost:2345/api/demo2
    public void stubForPost(){
        stubFor(post(urlMatching("/api/demo2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Stub For Demo2(Java)")));
    }

    //curl -X POST --data apple localhost:2345/api/demo3
    public void stubForPostWithReq(){
        stubFor(post(urlMatching("/api/demo3"))
                .withRequestBody(equalTo("apple"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Stub For Demo3(Java)")));
    }

    public static void main(String[] args){
        Demo demo1 = new Demo();
        demo1.startServer();
        demo1.stubForGet();
        demo1.stubForPost();
        demo1.stubForPostWithReq();

//        demo1.stopServer();
        System.out.print("==========");
    }
}