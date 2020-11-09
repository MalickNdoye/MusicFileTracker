public abstract class StandardFile extends DataFile{
    protected MusicFileExtension extension ;

    protected StandardFile(String name,String extension,String path){
        this.name = name ;
        this.extension = this.parseExtension(extension) ;
        this.path = path ;
    }

    private MusicFileExtension parseExtension(String extension){
        MusicFileExtension temp ;
        switch (extension) {
            case "mp3":
                temp = MusicFileExtension.MP3;
                break;
            case "wav":
                temp = MusicFileExtension.WAV;
                break;
            case "m4a":
                temp = MusicFileExtension.M4A;
                break;
            case "flac":
                temp = MusicFileExtension.FLAC;
                break;
            case "wma":
                temp = MusicFileExtension.WMA;
                break;
            default:
                temp = MusicFileExtension.UNKOWN;
                break;
        }
        temp.setExtension(extension);
        return temp;
    }

    @Override
    public String toString() {
        return name+'.'+extension.toString();
    }

    public String fullPath(){
        return super.fullPath()+extension.toString();
    }

    public MusicFileExtension getExtension(){
        return extension ;
    }
}
