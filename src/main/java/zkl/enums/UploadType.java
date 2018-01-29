package zkl.enums;

/**
 * Created by Administrator on 2018/1/24.
 */
public enum UploadType {

    Img("图片"),Attach("附件");

    private String name;

    private UploadType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
