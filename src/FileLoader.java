import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileLoader {
    String filename ;

    public static final ArrayList<String> musicExtension = new ArrayList<>() {{
        add("flac");
        add("m4a");
        add("mp3");
        add("wma");
        add("wav");
    }};


    //public FileLoader(){
    //    filename="unkown";
    //}

    public FileLoader(String filename){
        this.filename = filename;
    }

    public MusicDirectory load(String filename){
        this.filename = filename ;
        return this.load() ;
    }

    public MusicDirectory load(){
        if(filename.equals("unkown") || !(new File(filename).exists())){
            System.err.println("Le fichier "+filename+" n'existe pas.");
            return new MusicDirectory();
        }
        MusicDirectory root = new MusicDirectory("root");
        MusicDirectory current = root ;
        InputStream ips = null;
        InputStreamReader ipsr = null;
        BufferedReader br = null;
        try {
            ips = new FileInputStream(filename);
            ipsr = new InputStreamReader(ips);
            br = new BufferedReader(ipsr);
            Pattern directoryPattern = Pattern.compile("[:,/]$");
            Pattern filePattern = Pattern.compile("^[^.\\n].*[.].*");
            String ligne;
            while ((ligne = br.readLine()) != null) {
                Matcher dirMatcher = directoryPattern.matcher(ligne);
                Matcher fileMatcher = filePattern.matcher(ligne);
                if (dirMatcher.find()) {
                    if (ligne.charAt(ligne.length() - 1) == '/') {
                        String name = ligne.substring(0, ligne.length() - 1);
                        current.add((new MusicDirectory(name)));
                    } else if (ligne.charAt(ligne.length() - 1) == ':'){
                        if(ligne.length()>2){
                            String path = ligne.substring(0, ligne.length() - 1);
                            current = root.getDirectory(path);
                        }
                    }
                }else if (fileMatcher.find()){
                    int p = ligne.lastIndexOf(".");
                    String extension = ligne.substring(p+1);
                    String filename = ligne.substring(0,p);
                    if(musicExtension.contains(extension)){
                        MusicFile file = new MusicFile(filename,extension,current.getPath());
                        current.add(file);
                    }else{
                        OtherFile file = new OtherFile(filename,extension,current.getPath());
                        current.add(file);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (ipsr != null) {
                    ipsr.close();
                }
                if (ips != null) {
                    ips.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return root;
    }
}
