package org.springframework.samples.petclinic.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigTest {

	@Test
	void encryptTest() {
		String username = "system";
		String password = "1234";
		String path = "/console";

		System.out.println(jasyptEnCoding(username));
		System.out.println(jasyptEnCoding(password));
		System.out.println(jasyptEnCoding(path));
	}

	public String jasyptEnCoding(String value) {
		String key = "around_hub_studio";
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWithMD5AndDES");
		pbeEnc.setPassword(key);
		return pbeEnc.encrypt(value);
	}
}
