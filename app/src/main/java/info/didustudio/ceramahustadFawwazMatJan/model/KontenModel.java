package info.didustudio.ceramahustadFawwazMatJan.model;

public class KontenModel {
    private String nUstad;
    private String jCeramah;
    private String alamatmp3;

    public KontenModel(String nUstad, String jCeramah, String alamatmp3) {
        this.nUstad = nUstad;
        this.jCeramah = jCeramah;
        this.alamatmp3 = alamatmp3;
    }

    public String getAlamatmp3() {
        return alamatmp3;
    }

    public String getnUstad() {
        return nUstad;
    }

    public String getjCeramah() {
        return jCeramah;
    }


}
