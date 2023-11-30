package org.kainos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.kainos.ea.resources.TaskController;

public class TestingTasksApplication extends Application<TestingTasksConfiguration> {

  public static void main(final String[] args) throws Exception {
    new TestingTasksApplication().run(args);
  }

  @Override
  public String getName() {
    return "TestingTasks";
  }

  @Override
  public void initialize(final Bootstrap<TestingTasksConfiguration> bootstrap) {
    bootstrap.addBundle(new SwaggerBundle<TestingTasksConfiguration>() {
      @Override
      protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(TestingTasksConfiguration testingTasksConfiguration) {
        return testingTasksConfiguration.getSwagger();
      }
    });
  }

  @Override
  public void run(final TestingTasksConfiguration configuration,
                  final Environment environment) {
    environment.jersey().register(new TaskController());
  }

}
