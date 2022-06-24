package com.jeeplus.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class FileProperties {

    @Value("${userfiles.allowedType}")
    private String fileType;

    @Value("${userfiles.extensions.file}")
    private String fileExtensions;

    @Value("${userfiles.extensions.image}")
    private String imageExtensions;

    @Value("${userfiles.extensions.video}")
    private String videoExtensions;

    @Value("${userfiles.extensions.audio}")
    private String audioExtensions;

    @Value("${userfiles.extensions.office}")
    private String officeExtensions;


    public boolean isAvailable(String fileName) {
        switch (fileType){
            case "all":
                return true;
            case "file":
                return isContain (fileExtensions, fileName);
            case "image":
                return  isContain (imageExtensions, fileName);
            case "video":
                return  isContain (videoExtensions, fileName);
            case "audio":
                return  isContain (audioExtensions, fileName);
            case "office":
                return isContain (officeExtensions, fileName);
        }

        return false;
    }

    public boolean isImage(String fileName) {
        return isContain (imageExtensions, fileName);
    }

    public  boolean isContain(String extensions, String fileName){
        String[] extensionArray = extensions.split (",");
        String fileExtension = fileName.substring (fileName.lastIndexOf (".")+1);
        for(String extension: extensionArray){
            if(extension.trim ().equals (fileExtension)){
                return true;
            }
        }
        return false;
    }


}
