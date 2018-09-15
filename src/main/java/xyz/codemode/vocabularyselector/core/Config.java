package xyz.codemode.vocabularyselector.core;

import xyz.codemode.util.FileUtility;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Config {
    public static void init() {}

    private static File configFile;

    static {
        configFile = new File("./cfg/config.json");
        if(!configFile.exists())
            configFile.getParentFile().mkdirs();

        try {
            String jsonContent = FileUtility.loadAsString(configFile);
            JSONObject object = new JSONObject(jsonContent);
            generatorInitValue = object.getInt("GENERATOR_INIT_VALUE");
            wordsToDraw = object.getInt("WORDS_TO_DRAW");
            daysBetweenDraws = object.getInt("DAYS_BETWEEN_DRAWS");
            systemUI = object.getBoolean("SYSTEM_UI");
            fontSize = object.getInt("FONT_SIZE");
            fontColor = object.getString("FONT_COLOR");
            windowWidth = object.getInt("WINDOW_WIDTH");
            windowHeight = object.getInt("WINDOW_HEIGHT");
            windowX = object.getInt("WINDOW_X");
            windowY = object.getInt("WINDOW_Y");
            windowState = object.getInt("WINDOW_STATE");
        } catch (IOException e) {
            //file not found; set default values; create new json file
            setDefault();
            saveConfig();
        } catch (JSONException e) {
            //file does not contain specific key
            setDefault();
            saveConfig();
        }
    }

    private static void setDefault() {
        generatorInitValue = 21319;
        wordsToDraw = 5;
        daysBetweenDraws = 7;
        systemUI = true;
        fontSize = 12;
        fontColor = "BLACK";
        windowWidth = 800;
        windowHeight = 600;
        windowX = 500;
        windowY = 300;
        windowState = 0;
    }

    public static void saveConfig() {
        JSONStringer stringer = new JSONStringer();
        stringer.object();
        stringer.key("GENERATOR_INIT_VALUE");
        stringer.value(generatorInitValue);
        stringer.key("WORDS_TO_DRAW");
        stringer.value(wordsToDraw);
        stringer.key("DAYS_BETWEEN_DRAWS");
        stringer.value(daysBetweenDraws);
        stringer.key("SYSTEM_UI");
        stringer.value(systemUI);
        stringer.key("FONT_SIZE");
        stringer.value(fontSize);
        stringer.key("FONT_COLOR");
        stringer.value(fontColor);
        stringer.key("WINDOW_WIDTH");
        stringer.value(windowWidth);
        stringer.key("WINDOW_HEIGHT");
        stringer.value(windowHeight);
        stringer.key("WINDOW_X");
        stringer.value(windowX);
        stringer.key("WINDOW_Y");
        stringer.value(windowY);
        stringer.key("WINDOW_STATE");
        stringer.value(windowState);
        stringer.endObject();
        try {
            FileUtility.saveTextFile(configFile,stringer.toString());
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public static int getGeneratorInitValue() { return generatorInitValue; }
    public static int getWordsToDraw() { return wordsToDraw; }
    public static int getDaysBetweenDraws() { return daysBetweenDraws; }
    public static boolean getSystemUI() { return systemUI; }
    public static int getFontSize() { return fontSize; }
    public static String getFontColor() { return fontColor; }
    public static int getWindowWidth() { return windowWidth; }
    public static int getWindowHeight() { return windowHeight; }
    public static int getWindowState() { return windowState; }
    public static int getWindowX() { return windowX; }
    public static int getWindowY() { return windowY; }

    public static void setGeneratorInitValue(int arg) { generatorInitValue = arg; }
    public static void setWordsToDraw(int arg) { wordsToDraw = arg; }
    public static void setDaysBetweenDraws(int arg) { daysBetweenDraws = arg; }
    public static void setSystemUI(boolean arg) { systemUI = arg; }
    public static void setFontSize(int arg) { fontSize = arg; }
    public static void setFontColor(String arg) { fontColor = arg; }
    public static void setWindowWidth(int arg) { windowWidth = arg; }
    public static void setWindowHeight(int arg) { windowHeight = arg; }
    public static void setWindowState(int arg) { windowState = arg; }
    public static void setWindowX(int arg) { windowX = arg; }
    public static void setWindowY(int arg) { windowY = arg; }

    private static int generatorInitValue;
    private static int wordsToDraw;
    private static int daysBetweenDraws;
    private static boolean systemUI;
    private static int fontSize;
    private static String fontColor;
    private static int windowWidth;
    private static int windowHeight;
    private static int windowX;
    private static int windowY;
    private static int windowState;
}
