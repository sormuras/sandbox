import static java.lang.String.format;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class Checker extends AbstractProcessor {

  private int roundCounter = 0;
  private boolean verbose = Boolean.getBoolean("de.sormuras.verbose");

  private void note(String format, Object... args) {
    if (!verbose) {
      return;
    }
    Diagnostic.Kind kind = Diagnostic.Kind.NOTE;
    processingEnv.getMessager().printMessage(kind, format(format, args));
  }

  private void error(Element element, String format, Object... args) {
    Diagnostic.Kind kind = Diagnostic.Kind.ERROR;
    processingEnv.getMessager().printMessage(kind, format(format, args), element);
  }

  public Set<String> getSupportedAnnotationTypes() {
    Set<String> set = new HashSet<>();
    set.add(Api.class.getCanonicalName());
    return set;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
    note("Round #%d -> %s", roundCounter, round);
    if (round.processingOver()) {
      return true;
    }
    processAllApiAnnotatedElements(round.getElementsAnnotatedWith(Api.class));
    roundCounter++;
    return true;
  }

  private void processAllApiAnnotatedElements(Set<? extends Element> apiAnnotatedElements) {
    for (Element apiAnnotated : apiAnnotatedElements) {
      ElementKind kind = apiAnnotated.getKind();
      Api api = apiAnnotated.getAnnotation(Api.class);
      note("Element %s is annotated with %s", apiAnnotated, api);
    }
  }
}
