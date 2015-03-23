package com.owl.udpsockets;

import com.owl.udpsockets.sup.Server;
import com.owl.udpsockets.sup.impl.OperationListUtils;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public class BasicTest {

    @Test
    public void basicTest() throws IOException, InterruptedException, ExecutionException {       
        Server s = new Server();
        
        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
        s.executeCommand(OperationListUtils.colourMigrate(1, 255));
        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
        s.executeCommand(OperationListUtils.brightMigrate(1, 27));
        s.executeCommand(OperationListUtils.colourMigrate(1, 255));
    }

}
