package enums;

public enum DataSourceTypeEnum {

    READ(1,"写库"),WRITE(2,"读库");
    private int index;
    private String name;

    DataSourceTypeEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
