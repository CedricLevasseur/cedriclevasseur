package checker



/**
 * Check the size of a file exposed on the network via http or on the filesystem.
 *
 * @author Cédric Levasseur
 */
public class Checker{


    private float errorPercent=0.05;

    public static FILE_NOT_FOUND_OR_EMPTY = -1;
    public static FILE_SIZE_VALID = 0;
    public static FILE_SIZE_EXPECTED_INCORRECT = -2;




    public  ConfigObject config;


    /**
     * @param float the percent of size permitted :
     * 0.05 means the size could differs of 5%
     */
    public setErrorPercent(float percent){
	errorPercent=percent;
    }	

    /**
     *
     * @return the error percent
     */
    public float getErrorPercent(){
	return errorPercent;
    }	


    /**
     * the constructor load the config File
     */
    public Checker(){
 	load();	
    }
    
    /**
     * Convert a numbers of bytes into human reading size
     * in example : 1024 is 1K
     * @param a number of bytes
     */
    private static String roundMe(long bytesNumber){

        def unites=["b","K","M","G","T"]
        def iter=0;
        while (bytesNumber > 1024){
            bytesNumber= Math.round(bytesNumber/1024)
            iter++;
        }
        return bytesNumber.toString() + unites[iter];
    }

    /**
     *
     *
     */

    public Object getConfig(){
	return config;
    }


    /**
     * This main method check ALL the files specified into the SizeConfig.groovy
     *
     */

    public static void main(String[] args){

        def checker = new Checker();

        checker.diskCheck();
	for (e in checker.config){
		def url=e.getValue().get('url');
		def size=e.getValue().get('size');

		int res=0;
		switch (res=checker.fileCheck(url, size)){
			case FILE_NOT_FOUND_OR_EMPTY :
	            		System.out.println("File Not Found or empty : "+e.getValue().get('url'));
				break;
			case FILE_SIZE_EXPECTED_INCORRECT :
	            		System.out.println("Expected File Size is invalid : "+e.getValue().get('url'));
				break;
			case FILE_SIZE_VALID :
	            		System.out.println("CONFORME : "+e.getValue().get('url'));
				break;
			default :
				System.out.println("FILE SIZE NOT CONFORME, error=" + res +"%");
		}
    	}
    }

    /**
     * load the list of file from checker/SizeConfig.groovy
     */
    
    private void load(){

	config = new ConfigSlurper().parse(new File('checker/SizeConfig.groovy').toURL())
    }

    /**
     * @deprecated
     *
     */

    private void save(){

	new File("checker/SizeConfig.groovy").withWriter { writer ->config.writeTo(writer)}
    }


    /**
     * Print out the place on the disk
     * To be continued
     */

    public void diskCheck(){
        //File f = new File("c:/");
        File f = new File("/");
        // prints the volume size in bytes.
        System.out.println("Total Space="+Checker.roundMe(f.getTotalSpace()));
        // prints the total free bytes for the volume in bytes.
        System.out.println("Free Space="+Checker.roundMe(f.getFreeSpace()));
        // prints an accurate estimate of the total free (and available) bytes
        // on the volume. This method may return the same result as 'getFreeSpace()' on
        // some platforms.
        
        System.out.println("Usable Space="+Checker.roundMe(f.getUsableSpace()));
    }


    /**
     * check a file
     * @param src a location of a file, either http:// or a path (in java syntax which means which slash like c:/directory/file )
     * @param fileLenghtExpected a size in number of bytes
     */
    public int fileCheck(String src, long fileLengthExpected ){
	if(url.startsWith("http")){
		return fileHttpCheck(src,fileLengthExpected);
	}else{
		return fileFsCheck(src,fileLengthExpected);
	}
    }


    /**
     * check a file exposed via http
     * @param src a location of a file, on  http://
     * @param fileLenghtExpected a size in number of bytes
     */

    private int fileHttpCheck(String httpurl, long fileLengthExpected ){

	if(fileLengthExpected<1){
		return FILE_SIZE_EXPECTED_INCORRECT;
	}

        URL url                  = new URL(httpurl);
        URLConnection connection = url.openConnection();

        String fileType          = connection.getContentType();
        long fileLength           = connection.getContentLength();
/*	Date lastModified = new Date(connection.getLastModified());
 *	System.out.println(lastModified);
 *	Date date = new Date(connection.getDate());
 *	System.out.println(date);
 */
	return fileIntCheck(fileLength, fileLengthExpected);

    }


    /**
     * check a file on the filesystem
     * @param src the path of the file (in java syntax which means which slash like c:/directory/file )
     * @param fileLenghtExpected a size in number of bytes
     */
    private int fileFsCheck(String path, fileLengthExpected ){

	if(fileLengthExpected<1){
		return FILE_SIZE_EXPECTED_INCORRECT 
	}
	File file = new File(path);
        long fileLength = file.length();
/*	Date lastModified = new Date(file.lastModified());
 *	System.out.println(lastModified);
 */	

	return fileIntCheck(fileLength, fileLengthExpected);

	}

    /**
     * check two size in bytes with a errorPercent margin
     * @param fileLenght a size in number of bytes
     * @param fileLenghtExpected a size in number of bytes
     * @return zero if valide, a negative if file is not found, an errorPercent if not valid
     */
   private int fileIntCheck(long fileLength, long fileLengthExpected){

        if (fileLength <= 0){
            return FILE_NOT_FOUND_OR_EMPTY;
        }

	if ( ((fileLengthExpected * (1-errorPercent)) <= fileLength  ) && ( fileLength <= (fileLengthExpected * (1+errorPercent))) ){
	   return FILE_SIZE_VALID;		
	}

    
    	return Math.round(Math.abs(fileLengthExpected/fileLength*100));  

    }

}
