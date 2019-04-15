package integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.platform.launcher.EngineFilter.includeEngines;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.core.LauncherFactory;

@TestMethodOrder(Alphanumeric.class)
class JupiterIntegrationTests {

  @Test
  void packageName() {
    assertEquals("integration", getClass().getPackageName());
  }

  @Test
  void moduleIsNamed() {
    assumeTrue(getClass().getModule().isNamed(), "not running on the module-path");
    assertEquals("integration", getClass().getModule().getName());
  }

  @Test
  void resolve() {
    assumeTrue(getClass().getModule().isNamed(), "not running on the module-path");

    var selector = DiscoverySelectors.selectModule(getClass().getModule().getName());
    assertEquals(getClass().getModule().getName(), selector.getModuleName());

    var factory = LauncherFactory.create();
    var jupiter = includeEngines("junit-jupiter");
    var plan = factory.discover(request().selectors(selector).filters(jupiter).build());
    var engine = plan.getRoots().iterator().next();
    assertEquals(1, plan.getChildren(engine).size()); // JupiterIntegrationTests.class
    assertEquals(
        4,
        plan
            .getChildren(plan.getChildren(engine).iterator().next())
            .size()); // 4 test methods
  }

  @Test
  @EnabledIf("1 == 1")
  void javaScriptingModuleIsAvailable() {
    /* empty */
  }
}
