package com.agna.setmaster.module.profile;

import java.util.UUID;

/**
 *
 */
public class IdGenerator {

    public String generate(){
        return UUID.randomUUID().toString();
    }
}
