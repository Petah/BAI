/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.petah.spring.bai.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import org.petah.common.util.GameMath;
import org.petah.spring.bai.GlobalOptions;
import org.petah.spring.bai.delegate.AIDelegate;
import org.petah.spring.bai.group.UnitGroup;
import org.petah.spring.bai.map.control.ControlZone;
import org.petah.spring.bai.util.BuilderUtil;
import org.petah.spring.bai.util.CommandUtil;
import org.petah.spring.bai.util.FormatUtil;
import org.petah.spring.bai.util.MapUtil;

/**
 *
 * @author Petah
 */
public class JavaScriptTask extends Task {

    public static void main(String[] args) {
        String uri = "org/petah/spring/bai/tasks/standard/script/DefaultScout.js";
        JavaScriptTask j = new JavaScriptTask(null, "DefaultScout", uri);
        j.update(new UnitGroup(null, "Null"), 100);
        j.update(new UnitGroup(null, "Null"), 500);
        j.reloadScript();
        j.update(new UnitGroup(null, "Null"), 100);
        j.update(new UnitGroup(null, "Null"), 500);
        j.update(new UnitGroup(null, "Null"), 800);
    }
    // Create a script engine manager
    private static ScriptEngineManager factory = new ScriptEngineManager();
    // Create a JavaScript engine
    private static ScriptEngine engine = factory.getEngineByName("JavaScript");
    private static Invocable invocable = (Invocable) engine;
    // Class properties
    private String name;
    private ScriptContext scriptContext;
    private Object task;
    private File file;
    private String resource;
    private boolean scriptReady;

    public JavaScriptTask(AIDelegate aiDelegate, String name, File file) {
        super(aiDelegate);
        this.name = name;
        this.file = file;
        reloadScript();
    }

    public JavaScriptTask(AIDelegate aiDelegate, String name, String resource) {
        super(aiDelegate);
        this.name = name;
        this.resource = resource;
        reloadScript();
    }

    private Reader getScriptReader() throws FileNotFoundException {
        if (file != null) {
            return new FileReader(file);
        }
        return new InputStreamReader(JavaScriptTask.class.getClassLoader().getResourceAsStream(resource));
    }

    public void reloadScript() {
        try {
            scriptContext = new SimpleScriptContext();
            engine.eval(getScriptReader(), scriptContext);
            engine.setContext(scriptContext);

            // Put static classes
            engine.put("MapUtil", new MapUtil());
            engine.put("CommandUtil", new CommandUtil());
            engine.put("BuilderUtil", new BuilderUtil());
            engine.put("FormatUtil", new FormatUtil());
            engine.put("GameMath", new GameMath());
            engine.put("ControlZone", new ControlZone(-1, -1));
            // Put delegates
            engine.put("aiDelegate", aiDelegate);

            task = engine.get(name);
            scriptReady = true;
        } catch (Exception ex) {
            Logger.getLogger(JavaScriptTask.class.getName()).log(Level.SEVERE, "Could not load JavaScript task.", ex);
            if (GlobalOptions.isDebug()) {
                ex.printStackTrace();
            }
            scriptReady = false;
        }
    }

    @Override
    public boolean update(UnitGroup group, int frame) {
        if (scriptReady) {
            try {
                engine.setContext(scriptContext);
                invocable.invokeMethod(task, "update", group, frame);
            } catch (Exception ex) {
                Logger.getLogger(JavaScriptTask.class.getName()).log(Level.SEVERE, "Could not update JavaScript task.", ex);
                if (GlobalOptions.isDebug()) {
                    ex.printStackTrace();
                }
            }
        }
        return false;
    }
}
