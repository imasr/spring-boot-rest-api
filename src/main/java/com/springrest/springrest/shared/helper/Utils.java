package com.springrest.springrest.shared.helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Utils {
	public String createNewUserId() {
		return UUID.randomUUID().toString();

	}

	public final String UPLOAD_DIR = "C:\\Users\\002SML744\\Desktop\\spring-boot-rest-api\\src\\main\\resources\\static\\image";

	public boolean uploadFile(MultipartFile file) {
		boolean fileuploaded = false;

		try {

			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			// alternative way to uplaod using input stream

			// InputStream is = file.getInputStream();
			// byte data[] = new byte[is.available()];
			// is.read(data);
			// FileOutputStream fos = new FileOutputStream(UPLOAD_DIR + File.separator +
			// file.getOriginalFilename());
			// fos.write(data);
			// fos.flush();
			// fos.close();

			fileuploaded = true;

		} catch (Exception e) {
			e.printStackTrace();
			fileuploaded = false;
		}

		return fileuploaded;
	}

}
