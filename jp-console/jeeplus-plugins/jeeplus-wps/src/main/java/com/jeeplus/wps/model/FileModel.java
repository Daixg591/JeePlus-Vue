package com.jeeplus.wps.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileModel {
    public String id;
    public String name;
    public long version;
    public long size;
    public String creator;
    public String modifier;
    public static String download_url;

    public FileModel() {
    }

    public FileModel(String id, String name, long version, long size, String creator, String modifier) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.size = size;
        this.creator = creator;
        this.modifier = modifier;
    }
}
