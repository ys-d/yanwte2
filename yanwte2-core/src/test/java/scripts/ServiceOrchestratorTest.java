package scripts;

import com.github.winteryoung.yanwte2.core.ServiceOrchestrator;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import testmaterial.*;

/**
 * @author fanshen
 * @since 2017/12/12
 */
public class ServiceOrchestratorTest {
    @Test
    public void getOrchestratorByServiceType_normal() {
        NormalService orchestrator =
                ServiceOrchestrator.getOrchestratorByServiceType(NormalService.class);
        Assertions.assertThat(orchestrator.apply(2)).isEqualTo(5);
    }

    @Test(
        expectedExceptions = IllegalStateException.class,
        expectedExceptionsMessageRegExp =
                "Cannot find orchestrator for service: testmaterial.ServiceWithoutOrchestrator"
    )
    public void getOrchestratorByServiceType_noOrchestratorDefined() {
        ServiceOrchestrator.getOrchestratorByServiceType(ServiceWithoutOrchestrator.class);
    }

    @Test(
        expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp =
                "Service type is required to be a function: testmaterial.ServiceNotImplementFunction"
    )
    public void getOrchestratorByServiceType_serviceNotImplementFunction() {
        ServiceOrchestrator.getOrchestratorByServiceType(ServiceNotImplementFunction.class);
    }

    @Test(
        expectedExceptions = IllegalArgumentException.class,
        expectedExceptionsMessageRegExp =
                "Service type is required to be an interface: testmaterial.NormalServiceOrchestrator"
    )
    public void getOrchestratorByServiceType_serviceNotInterface() {
        ServiceOrchestrator.getOrchestratorByServiceType(NormalServiceOrchestrator.class);
    }

    @Test
    public void testInvoke() {
        SimpleService simpleService =
                ServiceOrchestrator.getOrchestratorByServiceType(SimpleService.class);
        String result = simpleService.apply("test");
        Assertions.assertThat(result).isEqualTo("test-abc");
    }
}