

public abstract class DataFile {
    protected String name ;
    protected String path ;

    public void setPath(String path){
        this.path = this.path.concat("/"+path) ;
    }

    public String fullPath(){
        return path+'/'+name ;
    }

}
