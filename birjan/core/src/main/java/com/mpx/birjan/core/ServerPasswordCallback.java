package com.mpx.birjan.core;

import java.io.IOException;

import javax.annotation.Resource;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.stereotype.Component;

@Component
public class ServerPasswordCallback implements CallbackHandler {

    public void handle(Callback[] callbacks) throws IOException, 
        UnsupportedCallbackException {

    	WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];

        if (pc.getIdentifier().equals("xris")) {
           if (!pc.getPassword().equals("xris")) {
                throw new IOException("wrong password");
           }
        }
    }

}
