import java.util.ArrayList;

public enum MusicFileExtension {
    FLAC,
    M4A,
    MP3,
    WMA,
    WAV,
    UNKOWN ;

    private String extension ;

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String toString(){
        return extension ;
    }
}
