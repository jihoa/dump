package org.springframework.samples.petclinic.upload;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Autowired
    private FilePersistService filePersistService;

    public AX5File upload(MultipartFile multipartFile) throws IOException {
        AX5File file = AX5File.of(multipartFile);
        filePersistService.persist(file);
        return file;
    }

    public List<AX5File> files() {
        return filePersistService.listFiles();
    }

    public AX5File getFile(String id) throws IOException {
        return filePersistService.getAx5File(id);
    }

    public void flush() {
        filePersistService.flush();
    }

    public void delete(List<AX5File> files) throws IOException {
        for (AX5File ax5File : files) {
            filePersistService.delete(ax5File);
        }
    }

    public ResponseEntity<byte[]> download(HttpServletRequest request, String id) throws IOException {
        AX5File ax5File = getFile(id);

        byte[] bytes = FileUtils.readFileToByteArray(ax5File.getFile());

        String fileName = getDisposition(request, ax5File.getFileName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public static String getDisposition(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
				String convertFileName = "";
				String userAgent = request.getHeader("User-Agent").toLowerCase();

			boolean isle = userAgent.indexOf("msie") != -1 || userAgent.indexOf("edge") != -1 || userAgent.indexOf("trident") != -1;

			if (isle) {
				convertFileName = URLEncoder.encode(fileName, "utf-8");
			} else {
				convertFileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
			}

			return convertFileName;
    }


    public void preview(HttpServletResponse response, String id) throws IOException {
        filePersistService.preview(response, id, Types.ImagePreviewType.ORIGIN);
    }

    public void thumbnail(HttpServletResponse response, String id) throws IOException {
        filePersistService.preview(response, id, Types.ImagePreviewType.THUMBNAIL);
    }
}
