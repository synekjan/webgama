package cz.cvut.fsv.webgama.util;

import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;

public class Generator {

    public static String generatePassword() {

	return RandomStringUtils.random(10, true, true);
    }

    public static String generateConfirmationID() {

	return UUID.randomUUID().toString();
    }
}
