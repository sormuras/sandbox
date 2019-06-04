package script.engines;

import java.util.*;
import javax.script.*;

public class ScriptEngines {

  public static void main(String... args) {
    System.out.println("ScriptEngines.main");

    ScriptEngineManager manager = new ScriptEngineManager();
    for (ScriptEngineFactory factory : manager.getEngineFactories()) {

      System.out.println("ScriptEngineFactory Info");

      String engName = factory.getEngineName();
      String engVersion = factory.getEngineVersion();
      String langName = factory.getLanguageName();
      String langVersion = factory.getLanguageVersion();

      System.out.printf("\tScript Engine: %s (%s)%n", engName, engVersion);

      List<String> engNames = factory.getNames();
      for(String name : engNames) {
        System.out.printf("\tEngine Alias: %s%n", name);
      }

      System.out.printf("\tLanguage: %s (%s)%n", langName, langVersion);

    }

  }
}
