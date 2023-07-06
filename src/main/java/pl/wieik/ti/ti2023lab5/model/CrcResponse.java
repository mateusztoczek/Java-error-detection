package pl.wieik.ti.ti2023lab5.model;

public class CrcResponse {

    String expected;
    String crc;
    String div;

    String newData;


    public String getNewData() {
        return newData;
    }

    public void setNewData(String newData) {
        this.newData = newData;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getDiv() {
        return div;
    }

    public void setDiv(String div) {
        this.div = div;
    }

    public String getCrc() {return crc;}

    public void setCrc(String crc) {this.crc = crc;}

}
