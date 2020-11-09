import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length!=2){
            System.out.println(MusicFileExtension.MP3.toString());
            System.out.println(Arrays.toString(args) +" length = "+args.length);
            System.err.println("Le programme nécessite un argument.");
        }else{
            FileLoader loader = new FileLoader(args[0]) ;
            MusicDirectory music1 = loader.load();
            MusicDirectory music2 = loader.load(args[1]) ;
            LibraryComparator comparator = new LibraryComparator(music1,music2);

            music1.writeFile("/home/guest/Musique_Info/PC_music.txt");
            music2.writeFile("/home/guest/Musique_Info/SD_music.txt");
            music1.printing(0);
            music2.printing(0);
            comparator.sizeComparison();
//            comparator.rawComparison();
        }
    }
}
