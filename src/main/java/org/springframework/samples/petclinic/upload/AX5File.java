package org.springframework.samples.petclinic.upload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AX5File implements Comparable {

    private String id;

    private String fileName;

    private String ext;

    private long fileSize;

    private String createdAt;

	//add
	private String subPath;

    @JsonIgnore
    private File file;

    @JsonIgnore
    private MultipartFile multipartFile;

    private long lastModified;

    public static AX5File of(MultipartFile multipartFile) {
        AX5File ax5File = new AX5File();

        ax5File.setId(UUID.randomUUID().toString());
        ax5File.setFileName(FilenameUtils.getName(multipartFile.getOriginalFilename()));
        ax5File.setExt(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        ax5File.setFileSize(multipartFile.getSize());
        ax5File.setCreatedAt(LocalDateTime.now().toString());
        ax5File.setMultipartFile(multipartFile);

        return ax5File;
    }

    public String getSaveName() {
        return String.format("%s.%s", id, ext);
    }

    public String getThumbnailSaveName() {
        return String.format("%s-thumbnail.%s", id, ext);
    }

    public String getJsonName() {
        return String.format("%s.json", id, ext);
    }

    @Override
    public int compareTo(Object o) {
        AX5File ax5File = (AX5File) o;

        long result = lastModified - ax5File.lastModified;
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @JsonProperty("preview")
    public String preview() {
        return "/api/v1/ax5uploader/preview?id=" + getId();
    }

    @JsonProperty("thumbnail")
    public String thumbnail() {
        return "/api/v1/ax5uploader/thumbnail?id=" + getId();
    }

    @JsonProperty("download")
    public String download() {
        return "/api/v1/ax5uploader/download?id=" + getId();
    }
}
