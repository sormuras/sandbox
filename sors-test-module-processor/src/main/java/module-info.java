module de.sormuras.sandbox.temopro {
  requires java.compiler;

  exports de.sormuras.sandbox.temopro;

  provides javax.annotation.processing.Processor with
      de.sormuras.sandbox.temopro.TestModuleProcessor;
}
