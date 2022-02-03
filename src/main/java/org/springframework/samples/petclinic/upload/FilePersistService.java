package org.springframework.samples.petclinic.upload;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.upload.util.JsonUtils;
import org.springframework.stereotype.Service;

@Service
public class FilePersistService implements InitializingBean {

    @Value("${ax5uploader.repository.path}")
    private String path;

    public void persist(AX5File ax5File) throws IOException {
        clean(ax5File.getSubPath());

        File file = new File(path + File.separator + ax5File.getSaveName());

        // 파일 로컬시스템에 저장
        ax5File.getMultipartFile().transferTo(file);

        // JSON 정보 저장
        FileUtils.writeStringToFile(new File(path + File.separator + ax5File.getJsonName()), JsonUtils.toJson(ax5File), "UTF-8");

//        String fileType = getFileType(ax5File.getExt());

		//썸네일 기능 제거..
//        if (fileType.equals(Types.FileType.IMAGE)) {
//            try {
//                Thumbnails.of(file)
//                        .crop(Positions.CENTER)
//                        .size(320, 320)
//                        .toFiles(new File(path), Rename.SUFFIX_HYPHEN_THUMBNAIL);
//            } catch (Exception e) {
//            }
//        }
    }

    int maxSaveFileCount = 20;

    public void clean(String subPath) throws IOException {
        List<AX5File> ax5Files = listFiles(subPath);

        if (ax5Files.size() > maxSaveFileCount) {
            for (int i = maxSaveFileCount - 1; i < ax5Files.size(); i++) {
                delete(ax5Files.get(i));
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FileUtils.forceMkdir(new File(path));
    }

    public List<AX5File> listFiles(String subPath) {
        List<AX5File> files = FileUtils.listFiles(new File(path), new String[]{"json"}, false).stream().map(file -> {
            try {
                return getAx5File(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(toList());

        Collections.sort(files);

        return files;
    }

    public AX5File getAx5File(File file) throws IOException {
        AX5File ax5File = JsonUtils.fromJson(FileUtils.readFileToString(file, "UTF-8"), AX5File.class);
        ax5File.setLastModified(file.lastModified());
        //ax5File.setFile(new File(path + File.separator + ax5File.getSaveName()));
		ax5File.setFile(new File(ax5File.getSaveName()));
        return ax5File;
    }

    public AX5File getAx5File(String id) throws IOException {
        return getAx5File(new File(path + File.separator + id + ".json"));
    }

    public void flush() {
        FileUtils.deleteQuietly(new File(path));
    }

    public void delete(AX5File ax5File) throws IOException {
        ax5File = getAx5File(ax5File.getId());
        FileUtils.deleteQuietly(new File(path + File.separator + ax5File.getSaveName()));
        FileUtils.deleteQuietly(new File(path + File.separator + ax5File.getJsonName()));
    }

    public void preview(HttpServletResponse response, String id, String type) throws IOException {
        AX5File ax5File = getAx5File(id);

        if (ax5File == null)
            return;

        MediaType mediaType = null;
        String imagePath = "";

        switch (ax5File.getExt().toUpperCase()) {
            case Types.FileExtensions.JPEG:
            case Types.FileExtensions.JPG:
                mediaType = MediaType.IMAGE_JPEG;
                break;

            case Types.FileExtensions.PNG:
                mediaType = MediaType.IMAGE_PNG;
                break;

            case Types.FileExtensions.GIF:
                mediaType = MediaType.IMAGE_GIF;
                break;

            default:
        }

        switch (type) {
            case Types.ImagePreviewType.ORIGIN:
                imagePath = ax5File.getSaveName();
                break;

            case Types.ImagePreviewType.THUMBNAIL:
                imagePath = ax5File.getThumbnailSaveName();
                break;
        }

        if (mediaType != null) {
            File file = new File(path + File.separator + imagePath);
            byte[] bytes = FileUtils.readFileToByteArray(file);

            response.setContentType(mediaType.toString());
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
        }
    }

    private String getFileType(String extension) {
        switch (extension.toUpperCase()) {
            case Types.FileExtensions.PNG:
            case Types.FileExtensions.JPG:
            case Types.FileExtensions.JPEG:
            case Types.FileExtensions.GIF:
            case Types.FileExtensions.BMP:
            case Types.FileExtensions.TIFF:
            case Types.FileExtensions.TIF:
                return Types.FileType.IMAGE;

            case Types.FileExtensions.PDF:
                return Types.FileType.PDF;

            default:
                return Types.FileType.ETC;
        }
    }
}
