public class LibraryComparator {
    private MusicDirectory library1 ;
    private MusicDirectory library2 ;
    private MusicDirectory result ;
    private final Integer[] sizeCount ;
    private final Integer[] directoryCount ;
    private final Integer[] fileCount ;
    private final Integer[] musicFileCount ;

    public LibraryComparator(MusicDirectory library1,MusicDirectory library2){
        this.library1 = library1;
        this.library2 = library2;
        this.sizeCount = new Integer[2] ;
        this.directoryCount = new Integer[2] ;
        this.fileCount = new Integer[2] ;
        this.musicFileCount = new Integer[2];
        this.firstProcess();
    }

    private void firstProcess(){
        if(library1==null || library2==null){
            library1 = library1==null ? new MusicDirectory() : library1 ;
            library2 = library2==null ? new MusicDirectory() : library2 ;
        }
        for(int i=0;i<2;i++){
            sizeCount[i] = i==0 ? library1.size() : library2.size() ;
            directoryCount[i] = i==0 ? library1.directoryCount() : library2.directoryCount();
            fileCount[i] = i==0 ? library1.allFileCount() : library2.allFileCount();
            musicFileCount[i] = i==0 ? library1.musicFileCount() : library2.musicFileCount() ;
        }
    }

    public void sizeComparison(){
        for (int i=0;i<2;i++){
            if (i != 0) {
                System.out.println("File 2 :");
            } else {
                System.out.println("File 1 :");
            }
            System.out.println("\tSize : "+sizeCount[i]);
            System.out.println("\tDirectories : "+directoryCount[i]);
            System.out.println("\tFiles : "+fileCount[i]);
            System.out.println("\tMusic Files : "+musicFileCount[i]);
            System.out.println();
        }
    }

    /*
    public void rawComparison(){
        result = library1.difference(library2);
        System.out.println("Details Des Différences");
        result.printing(0);
    }*/
}
