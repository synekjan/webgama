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

	public static String generateInputFilename(String username) {

		StringBuilder sb = new StringBuilder("webgama");
		sb.append("-");
		sb.append(username);
		sb.append("-");
		sb.append(RandomStringUtils.random(6, true, true));
		sb.append(".wxml");
		return sb.toString();
	}

	public static String generateSvgOutputFilename(String username) {

		StringBuilder sb = new StringBuilder("webgama");
		sb.append("-");
		sb.append(username);
		sb.append("-");
		sb.append(RandomStringUtils.random(6, true, true));
		sb.append(".wsvg");
		return sb.toString();
	}

	public static String generateHtmlOutputFilename(String username) {

		StringBuilder sb = new StringBuilder("webgama");
		sb.append("-");
		sb.append(username);
		sb.append("-");
		sb.append(RandomStringUtils.random(6, true, true));
		sb.append(".whtml");
		return sb.toString();
	}

	public static String generateTextOutputFilename(String username) {

		StringBuilder sb = new StringBuilder("webgama");
		sb.append("-");
		sb.append(username);
		sb.append("-");
		sb.append(RandomStringUtils.random(6, true, true));
		sb.append(".wtxt");
		return sb.toString();
	}
}
