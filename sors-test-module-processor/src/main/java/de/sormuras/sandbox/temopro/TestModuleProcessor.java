package de.sormuras.sandbox.temopro;

import static java.lang.String.format;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Set;

public class TestModuleProcessor extends AbstractProcessor {

  private static final String OPTION_VERBOSE = "de.sormuras.sandbox.temopro.verbose";

  private int roundCounter = 0;
  private boolean verbose = Boolean.getBoolean(OPTION_VERBOSE);

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of(TestModule.class.getCanonicalName());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latest();
  }

  @Override
  public Set<String> getSupportedOptions() {
    return Set.of(OPTION_VERBOSE);
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round) {
    note("Processing magic round #%d -> %s", roundCounter, round);
    note("Living inside %s", getClass().getModule());
    if (round.processingOver()) {
      return true;
    }

    processAllElementsAnnotatedWithTestModule(round.getElementsAnnotatedWith(TestModule.class));
    roundCounter++;
    return true;
  }

  private void error(Element element, String format, Object... args) {
    processingEnv.getMessager().printMessage(ERROR, format(format, args), element);
  }

  private void note(String format, Object... args) {
    if (!verbose) {
      return;
    }
    processingEnv.getMessager().printMessage(NOTE, format(format, args));
  }

  private void processAllElementsAnnotatedWithTestModule(Set<? extends Element> elements) {
    for (Element testModuleAnnotated : elements) {
      ElementKind kind = testModuleAnnotated.getKind();
      if (!kind.equals(ElementKind.PACKAGE)) {
        error(
            testModuleAnnotated,
            "@TestModule expects a package as target, not %s %s",
            kind,
            testModuleAnnotated);
      }
      note("Processing in enclosing: %s", testModuleAnnotated.getEnclosingElement());
      processElementAnnotatedWithTestModule((PackageElement) testModuleAnnotated);
    }
  }

  private void processElementAnnotatedWithTestModule(PackageElement packageElement) {
    var filer = processingEnv.getFiler();
    var testModule = packageElement.getAnnotation(TestModule.class);
    var packageName = packageElement.getQualifiedName().toString();
    note("Package %s is annotated with: %s", packageName, testModule);

    // from annotation usage
    var text = String.join(System.lineSeparator(), testModule.value());

    try {
      // from template source file
      var test =
          filer.getResource(
              testModule.sourceLocation(),
              testModule.sourceModuleAndPackageName(),
              testModule.sourceRelativeName());
      text = test.getCharContent(true).toString();
      note("Using content of `%s`", testModule.sourceRelativeName());
    } catch (IOException e) {
      // ignore
    }

    if (text.isEmpty()) {
      error(packageElement, "Text is empty?!");
      return;
    }

    try {
      note("Printing to `%s`: %s", testModule.targetRelativeName(), text);
      var file =
          filer.createResource(
              testModule.targetLocation(),
              testModule.targetModuleAndPackageName(),
              testModule.targetRelativeName());
      try (PrintStream stream = new PrintStream(file.openOutputStream(), false, "UTF-8")) {
        stream.print(text);
      }
    } catch (Exception e) {
      error(packageElement, e.toString());
    }
  }
}
