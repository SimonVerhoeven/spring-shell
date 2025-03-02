/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.shell.samples.e2e;

import org.springframework.context.annotation.Bean;
import org.springframework.shell.Availability;
import org.springframework.shell.AvailabilityProvider;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.CommandAvailability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.stereotype.Component;

public class AvailabilityCommands {

	@ShellComponent
	public static class LegacyAnnotation extends BaseE2ECommands {

		// find from <methodName>Availability
		@ShellMethod(key = LEGACY_ANNO + "availability-1", group = GROUP)
		public String testAvailability1LegacyAnnotation(
		) {
			return "Hello";
		}

		public Availability testAvailability1LegacyAnnotationAvailability() {
			return Availability.unavailable("not available 1");
		}

		// find from method name in @ShellMethodAvailability
		@ShellMethod(key = LEGACY_ANNO + "availability-2", group = GROUP)
		@ShellMethodAvailability("testAvailability2LegacyAnnotationAvailability2")
		public String testAvailability2LegacyAnnotation(
		) {
			return "Hello";
		}

		public Availability testAvailability2LegacyAnnotationAvailability2() {
			return Availability.unavailable("not available 2");
		}

		// find backwards from @ShellMethodAvailability command name
		@ShellMethod(key = LEGACY_ANNO + "availability-3", group = GROUP)
		public String testAvailability3LegacyAnnotation(
		) {
			return "Hello";
		}

		@ShellMethodAvailability("e2e anno availability-3")
		public Availability testAvailability3LegacyAnnotationAvailability3() {
			return Availability.unavailable("not available 3");
		}
	}

	@Command(command = BaseE2ECommands.ANNO, group = BaseE2ECommands.GROUP)
	public static class Annotation extends BaseE2ECommands {

		@Command(command = "availability-1")
		@CommandAvailability(name = "testAvailability1AnnotationAvailability")
		public String testAvailability1Annotation(
		) {
			return "Hello";
		}

		@Bean
		public AvailabilityProvider testAvailability1AnnotationAvailability() {
			return () -> Availability.unavailable("not available");
		}
	}

	@Component
	public static class Registration extends BaseE2ECommands {

		@Bean
		public CommandRegistration testAvailability1Registration() {
			return getBuilder()
				.command(REG, "availability-1")
				.group(GROUP)
				.availability(() -> {
					return Availability.unavailable("not available");
				})
				.withTarget()
					.function(ctx -> {
						return "Hello";
					})
					.and()
				.build();
		}

		@Bean
		public CommandRegistration testAvailability2Registration() {
			return getBuilder()
				.command(REG, "availability-2")
				.group(GROUP)
				.availability(testAvailability2AnnotationAvailability())
				.withTarget()
					.function(ctx -> {
						return "Hello";
					})
					.and()
				.build();
		}

		AvailabilityProvider testAvailability2AnnotationAvailability() {
			return () -> Availability.unavailable("not available");
		}
	}
}
