package xyz.codemode.vocabularyselector.core;

import xyz.codemode.util.FileUtility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Vocabulary {
    public Vocabulary() {
        if(Config.getCustomVocabulary()) {
            lastDrawnVocabularyTXT = new File("./cfg/lastDrawnVocabulary-custom.txt");
            drawnJSON = new File("./cfg/drawn-custom.json");
        } else {
            setDefaultFiles();
        }

        loadFromFiles();
    }

    public void loadFromFiles() {
        loadVocabulary();
        loadDrawnArray();
        filterVocabulary();
    }

    private void setDefaultFiles() {
        lastDrawnVocabularyTXT = new File("./cfg/lastDrawnVocabulary.txt");
        drawnJSON = new File("./cfg/drawn.json");
    }

    private String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for(String element : list) {
            sb.append(element);
            sb.append('\n');
        }

        if(sb.length()>0)
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public String getAllVocabulary() { return listToString(vocabularyList); }

    public String getLastDrawnVocabulary() { return listToString(lastDrawnVocabulary); }

    public String getAllDrawnVocabulary() {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<vocabularyList.size(); i++) {
            if(drawn[i]) {
                sb.append(vocabularyList.get(i));
                sb.append('\n');
            }
        }

        if(sb.length()!=0)
            sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public void drawVocabulary(int amount, int generatorInitValue) {
        RandomMachine rm = new RandomMachine(generatorInitValue);
        rm.random(amount);
    }

    private void saveLastDrawnVocabulary() {
        try {
            FileUtility.saveTextFile(lastDrawnVocabularyTXT,lastDrawnVocabulary);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves data about vocabulary that has been drawn
     */
    private void saveDrawnArray() {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        stringer.key("RANGE");
        stringer.value(drawn.length);
        stringer.key("ARRAY");
        stringer.array();

        for(int i=0; i<drawn.length; i++) {
            stringer.value(drawn[i]);
        }

        stringer.endArray();
        stringer.endObject();

        try {
            FileUtility.saveTextFile(drawnJSON, stringer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads data about vocabulary that has been drawn
     */
    private void loadDrawnArray() {
        try {
            String jsonContent = FileUtility.loadAsString(drawnJSON);
            JSONObject object = new JSONObject(jsonContent);
            int range = object.getInt("RANGE");
            drawn = new boolean[range];
            JSONArray array = object.getJSONArray("ARRAY");

            for(int i=0; i<range; i++) {
                drawn[i] = array.getBoolean(i);
            }
        } catch (IOException e) {
            drawn = new boolean[1024];  //TODO: add custom size
        }
    }

    private void changeToDefault(String message, String title) {
        Config.setCustomVocabulary(false);
        Config.saveConfig();
        setDefaultFiles();
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE));
    }

    private void loadVocabulary() {
        InputStream vocabularyStream;
        if(Config.getCustomVocabulary()) {
            try {
                vocabularyStream = new FileInputStream(Config.getVocabularyPath());
            } catch (FileNotFoundException e) {
                vocabularyStream = Vocabulary.class.getResourceAsStream("/vocabulary.txt");
                changeToDefault("Custom file with vocabulary not found!\nLoading default one.", "File not found");
            }
        } else {
            vocabularyStream = Vocabulary.class.getResourceAsStream("/vocabulary.txt");
        }

        vocabularyList = FileUtility.loadAsList(vocabularyStream,"UTF-8");

        try {
            lastDrawnVocabulary = FileUtility.loadAsList(lastDrawnVocabularyTXT);
        } catch(IOException e) {
            lastDrawnVocabulary = new LinkedList<>();
        }
    }

    private void filterVocabulary() {
        indexesOfDrawableVocabulary = new LinkedList<>();

        try {
            for(int i=0; i<vocabularyList.size(); i++) {
                if(!drawn[i])
                    indexesOfDrawableVocabulary.add(i);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            changeToDefault("File with vocabulary is incorrect\nor has more than 1024 lines", "Custom file error");
            loadVocabulary();   //Load default vocabulary
            loadDrawnArray();
        }

    }

    private File lastDrawnVocabularyTXT;
    private File drawnJSON;

    private List<String> vocabularyList;
    private List<String> lastDrawnVocabulary;
    private List<Integer> indexesOfDrawableVocabulary;
    private boolean[] drawn;

    private class RandomMachine {
        RandomMachine() { rand = new Random(); }
        RandomMachine(int initValueForRandom) { rand = new Random(initValueForRandom); }
        private void random(int amount) {

            lastDrawnVocabulary = new LinkedList<>();       //get rid of old list
            filterVocabulary();

            for(int i=0; i<amount; i++) {
                int range = indexesOfDrawableVocabulary.size();

                if(range>0) {
                    int indexOfVocabularyToDraw = rand.nextInt(range);
                    int index = indexesOfDrawableVocabulary.get(indexOfVocabularyToDraw);   //get index for entire vocabulary list

                    lastDrawnVocabulary.add(vocabularyList.get(index));
                    drawn[index] = true;
                }
            }

            saveDrawnArray();
            saveLastDrawnVocabulary();
        }

        private Random rand;
    }
}
