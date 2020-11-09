import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;


public class MusicDirectory extends DataFile {
    private final HashMap<String,StandardFile> dataFiles;
    private final HashMap<String,MusicDirectory> subDirectories ;


    public MusicDirectory(String filepath){
        this.parseName(filepath);
        dataFiles = new HashMap<>();
        subDirectories = new HashMap<>();
    }

    private void parseName(String filepath) {
        int p = filepath.lastIndexOf("/");
        if (p==-1 || p==filepath.length()-1){
            this.name = filepath ;
            this.path = filepath ;
        }else{
            this.name = filepath.substring(p+1);
            this.path = filepath.substring(0,p);
        }
    }

    public MusicDirectory(){
        this.name = "unkown" ;
        dataFiles = new HashMap<>();
        subDirectories = new HashMap<>();
    }

    public void add(Object file){
        if (file instanceof StandardFile){
            ((StandardFile)file).setPath(this.path);
            dataFiles.put(((StandardFile) file).name,(StandardFile) file);
        }else if (file instanceof MusicDirectory){
            ((MusicDirectory)file).setPath(this.path+((MusicDirectory)file).getName());
            subDirectories.put(((MusicDirectory) file).name,(MusicDirectory) file);
        }else{
            System.out.println("Erreur lors de l'ajout de : "+file.toString());
        }
    }


    //./Classement par Artiste/Orchestra Lunatica   /      A Chance Encounter:
    public MusicDirectory getDirectory(String path) {
        if(path.contains("/")) {
            String[] tab = path.split("/", 2);
            MusicDirectory temp = this.getDirectory(tab[0]);
            return temp.getDirectory(tab[1]);
        }else{
            if(path.equals(".")){
                return this ;
            }else{
                return this.subDirectories.get(path);
            }
        }
    }


    public String getPath() {
        return path ;
    }

    public void setPath(String path){
        this.path = path ;
    }

    private String getName() {
        return name ;
    }

    public int size(){
        int size = dataFiles.size() + subDirectories.size() ;
        for (MusicDirectory dir : subDirectories.values()){
            size = size + dir.size() ;
        }
        return size ;
    }

    public int directoryCount() {
        int size = 0 ;
        for (MusicDirectory dir : subDirectories.values()) {
            size = size + dir.directoryCount();
        }
        size = size + subDirectories.size() ;
        return size;
    }

    public int musicFileCount() {
        int size = 0 ;
        for (MusicDirectory dir : subDirectories.values()) {
            size = size + dir.musicFileCount();
        }
        for (StandardFile file : dataFiles.values()) {
            if(!file.getExtension().equals(MusicFileExtension.UNKOWN)){
                size = size + 1 ;
            }
        }
        return size;
    }

    public int allFileCount(){
        int size = dataFiles.size() ;
        for (MusicDirectory dir : subDirectories.values()) {
            size = size + dir.allFileCount();
        }
        return size;
    }

    public MusicDirectory searchDirectory(String file){
        return subDirectories.get(file) ;
    }

    public StandardFile searchFile(String file){
        return dataFiles.get(file) ;
    }

    public MusicDirectory difference(MusicDirectory dir){
        MusicDirectory diff = new MusicDirectory(name);
        Collection<MusicDirectory> test = subDirectories.values();

        for (MusicDirectory temp : subDirectories.values()){
            MusicDirectory object = dir.searchDirectory(temp.name);
            if(object==null){
                diff.add(temp);
            }else{
                MusicDirectory temp2 = temp.difference(object);
                if(temp2!=null){
                    diff.add(temp2);
                }
            }
        }
        for(StandardFile file : dataFiles.values()){
            StandardFile object = dir.searchFile(file.name) ;
            if(object==null){
                diff.add(file);
            }
        }
        return diff ;
    }

    public void printing(int i){
        printing(i,System.out);
    }

    private void printing(int i, PrintStream stream) {
        String tab = "";
        for(int j=0;j<i;j++) {
            tab = tab.concat("\t");
        }
        stream.println(tab+name+":");
        for(MusicDirectory temp : subDirectories.values()){
            temp.printing(i+1,stream);
        }
        for(StandardFile file : dataFiles.values()){
            if(file.extension!=MusicFileExtension.UNKOWN){
                stream.println(tab+"\t"+file.toString());
            }
        }
    }

    public void writeFile(String path){
        try (PrintStream l_out = new PrintStream(new FileOutputStream(path, false))) {
            System.out.println("Writing..." + path);
            l_out.println(path);
            this.printing(0, l_out);
            l_out.println();
            l_out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("End");
        }
    }
}
