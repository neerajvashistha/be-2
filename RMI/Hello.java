/*
 * Copyright (c) 2004, Oracle and/or its affiliates. All rights reserved.
 *
 */
package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}
