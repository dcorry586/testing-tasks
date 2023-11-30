package org.kanos.ea;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.kanos.ea.resources.TaskController;

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
        // TODO: application initialization
    }

    @Override
    public void run(final TestingTasksConfiguration configuration,
                    final Environment environment) {
   environment.jersey().register(new TaskController());
    }

}
