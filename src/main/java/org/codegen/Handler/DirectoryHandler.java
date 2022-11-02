package org.codegen.Handler;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

public class DirectoryHandler {
    private static final Logger log = LoggerFactory.getLogger(DirectoryHandler.class);
   public static String outerDirectoryPath= System.getProperty("directoryPath");
   public static String outerScriptDirectoryPath=System.getProperty("scriptPath");
//    public static String outerDirectoryPath= "C:\\Users\\di.garg1\\Desktop\\Autogenerated";
//    public static String outerScriptDirectoryPath="C:\\Users\\di.garg1\\Desktop\\MySql_Sripts\\ims.sql";
    public static void createDirectory(String directoryPath)
    {
        File file = new File(directoryPath);
        if(file.isDirectory())
            log.info("Directory already exists");
        else {
            file.mkdirs();
            log.info("Directory created--->"+directoryPath);
        }
    }
    public static String getScriptName()
    {
        File scriptName= new File(outerScriptDirectoryPath);
        return scriptName.getName().replace(".sql","");
    }

    public static String getSchemaName() {
        File schemaName= new File(outerDirectoryPath+"\\src\\main\\java\\com\\gemini\\"+getScriptName()+"\\entity");
        String[] directories = schemaName.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        return directories[0];
    }


    public static String generateDirectoryPath()
    {
        return outerDirectoryPath+"\\src\\main\\java\\com\\gemini\\"+getScriptName();
    }

    public static void renameDirectory(String filePath ){
        File file = new File(filePath);
        if(file.renameTo
                (new File(DirectoryHandler.generateDirectoryPath()+"\\entity"))) {
            log.info("File moved successfully");
        }
        else {
            log.info("Failed to move the file");
        }
    }

    public static void deleteDirectory(String directoryPath)  {
        File file =new File(directoryPath);
        try {
            if (file.isDirectory()) {
                FileUtils.deleteQuietly(file);
                log.info("Directory deleted successfully : {}", file);
            } else {
                log.info("Directory does not exist");
            }
        }
        catch (Exception e){
            log.info(e.getMessage(),e.getCause());
        }
    }


    public static void deleteFiles(List<String> classNames, String filePath, String fileExtension) throws InterruptedException, IOException {

        for (String className:classNames) {
            File file = new File(filePath+"\\"+className+fileExtension);
            if (file.exists()) {
                log.info("Required Path----->{}",file);
                file.delete();
                log.info("{}{} deleted successfully",className,fileExtension);
            } else {
                log.info("{}{} does not exist",className,fileExtension);
            }

        }
    }

}
