package checker

public class Checker{

    private float errorPercent=0.05;
    public  ConfigObject config;

    public setErrorPercent(float percent){
	errorPercent=percent;
    }	
    
    public float getErrorPercent(){
	return errorPercent;
    }	

    public Checker(){
 	load();	
    }
    
    public static FILE_NOT_FOUND_OR_EMPTY = -1;
    public static FILE_SIZE_VALID = 0;
    public static FILE_SIZE_EXPECTED_INCORRECT = -2;

    

    public static String roundMe(long bytesNumber){

        def unites=["b","K","M","G","T"]
        def iter=0;
        while (bytesNumber > 1024){
            bytesNumber= Math.round(bytesNumber/1024)
            iter++;
        }
        return bytesNumber.toString() + unites[iter];
    }

    public Object getConfig(){
	return config;
    }


    public static void main(String[] args){

        def checker = new Checker();

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

    private void load(){

	config = new ConfigSlurper().parse(new File('checker/SizeConfig.groovy').toURL())
    }

    private void save(){

	new File("vidal/Size.groovy").withWriter { writer ->config.writeTo(writer)}
    }

    public void diskCheck(){
        //File f = new File("c:/");
        File f = new File("F:/");
        // prints the volume size in bytes.
        System.out.println("Total Space="+Checker.roundMe(f.getTotalSpace()));
        // prints the total free bytes for the volume in bytes.
        System.out.println("Free Space="+Checker.roundMe(f.getFreeSpace()));
        // prints an accurate estimate of the total free (and available) bytes
        // on the volume. This method may return the same result as 'getFreeSpace()' on
        // some platforms.
        
        System.out.println("Usable Space="+Checker.roundMe(f.getUsableSpace()));
    }

    public int fileCheck(String url, long fileLengthExpected ){
	if(url.startsWith("http")){
		return fileHttpCheck(url,fileLengthExpected);
	}else{
		return fileFsCheck(url,fileLengthExpected);
	}
    }

    public int fileHttpCheck(String httpurl, fileLengthExpected ){

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

    public int fileFsCheck(String path, fileLengthExpected ){

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
