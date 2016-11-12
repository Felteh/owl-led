package com.owl.udpsockets;

import com.owl.udpsockets.sup.Server;
import com.owl.udpsockets.sup.impl.OperationListUtils;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public class BasicTest {

    private final String IP_ADDRESS = "192.168.1.70";
    private final String MAC_ADDRESS = "ac:cf:23:3e:10:56";

    @Test
    public void basicTest() throws IOException, InterruptedException, ExecutionException {
        Server s = new Server(IP_ADDRESS, MAC_ADDRESS);

        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
//        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
//        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
//        s.executeCommand(OperationListUtils.colourMigrate(1, 255));
//        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
//        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
//        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
//        s.executeCommand(OperationListUtils.colourMigrate(1, 255));
    }

}
