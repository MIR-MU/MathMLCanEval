/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mir.domain;

import java.io.Serializable;
import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author siska
 */
@JsonIgnoreProperties({"data"})
public class UploadedFile implements Serializable {

    private static final long serialVersionUID = -5492177420662942928L;

    private String name;
    private Integer size;

    private byte[] data;

    public UploadedFile() {
        super();
    }

    public UploadedFile(String name, Integer size) {
        super();
        this.name = name;
        this.size = size;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public String getFormattedSize() {
        return FileUtils.byteCountToDisplaySize(this.size);
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
