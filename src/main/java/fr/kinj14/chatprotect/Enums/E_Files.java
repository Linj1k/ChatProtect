package fr.kinj14.chatprotect.Enums;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import fr.kinj14.chatprotect.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.List;

public enum E_Files {
    CONFIG("config.yml"),
    LANG("lang.yml"),
    DATA("data/chatprotect.json");

    private final String fileName;
    private final File dataFolder;

    protected final Main main = Main.getInstance();

    E_Files(String fileName){
        this.fileName = fileName;
        this.dataFolder = main.getDataFolder();
    }

    public File getFile(){
        return new File(dataFolder, fileName);
    }

    public FileConfiguration getConfig(){
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public List<String> getJSON(){
        try {
            Gson gson = new GsonBuilder().create();
            JsonReader reader = new JsonReader(new FileReader(getFile()));
            List<String> data = gson.fromJson(reader, new TypeToken<List<String>>() {
            }.getType());
            return data;
        } catch (IOException e) {
            main.logger.info("An error occured while saving "+fileName+"(IOException)");
            e.printStackTrace();
        }
        return null;
    }

    public void saveYML(FileConfiguration config){
        try {
            config.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveJSON(List<String> data){
        try {
            Gson gson = new GsonBuilder().create();
            FileWriter file = new FileWriter(getFile());
            gson.toJson(data, file);
            file.close();
        } catch (JsonIOException e) {
            main.logger.info("An error occured while saving "+fileName+" (JsonIOException)");
            e.printStackTrace();
        } catch (IOException e) {
            main.logger.info("An error occured while saving "+fileName+"(IOException)");
            e.printStackTrace();
        }
    }

    public String getFileName(){
        return this.fileName;
    }

    public void createYML(){
        if(fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("ResourcePath cannot be null or empty!");
        }

        InputStream in = main.getResource(fileName);
        if(in == null){
            throw new IllegalArgumentException("The resource '"+fileName+"' cannot be found in plugin jar!");
        }

        if(!dataFolder.exists() && !dataFolder.mkdir()){
            main.logger.severe("Failed to make plugin directory!");
        }

        File outFile = getFile();
        try{
            if(!outFile.exists()){
                main.logger.info("The "+fileName+" was not found, creation in progress ...");

                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;

                while((n = in.read(buf)) > 0){
                    out.write(buf, 0, n);
                }

                out.close();
                in.close();

                if(!outFile.exists()){
                    main.logger.severe("Unable to copy file !");
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void createJSON(){
        if(fileName == null || fileName.isEmpty()){
            throw new IllegalArgumentException("ResourcePath cannot be null or empty!");
        }

        if(!getFile().exists()){
            try {
                getFile().createNewFile();
                main.logger.info(main.getPrefix()+fileName+" is created !");
            } catch (IOException e) {
                main.logger.severe(main.getPrefix()+"An error occured while creating file "+fileName+"(IOException)");
                e.printStackTrace();
            }
        }
    }
}
