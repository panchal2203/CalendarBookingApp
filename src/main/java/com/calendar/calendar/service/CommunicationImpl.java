package com.calendar.calendar.service;

import org.springframework.stereotype.Service;

@Service
public class CommunicationImpl implements Communication {
    @Override
    public void sendCommunication(String emailAddress, String content) {
        // comm event trigger
    }
}
