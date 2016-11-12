package com.owl.udpsockets;

import com.owl.light.LightServer;
import com.owl.light.OperationListUtils;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public class BasicTest {

    private final String IP_ADDRESS = "192.168.1.70";
    private final String MAC_ADDRESS = "ac:cf:23:3e:10:56";

    @Test
    public void basicTest() throws IOException, InterruptedException, ExecutionException {
        LightServer s = new LightServer(IP_ADDRESS, MAC_ADDRESS);
        CompletableFuture<Void> fut = s.initialise();

        CompletableFuture<Void> fin = fut.thenRun(() -> {
            try {
                s.executeCommand(OperationListUtils.brightMigrate(1, 27));
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        fin.get();
    }

}
