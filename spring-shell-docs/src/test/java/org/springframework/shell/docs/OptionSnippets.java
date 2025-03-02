/*
 * Copyright 2022-2023 the original author or authors.
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
package org.springframework.shell.docs;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.shell.command.CommandRegistration;
import org.springframework.shell.command.CommandRegistration.OptionArity;
import org.springframework.shell.command.CommandRegistration.OptionNameModifier;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

public class OptionSnippets {

	class Dump1Legacy {
		// tag::option-with-annotation[]
		public String example(@ShellOption(value = { "--argx" }) String arg1) {
			return "Hello " + arg1;
		}
		// end::option-with-annotation[]
	}

	class Dump1 {
		// tag::option-with-option-annotation[]
		public String example(@Option(longNames = "argx") String arg1) {
			return "Hello " + arg1;
		}
		// end::option-with-option-annotation[]
	}

	class Dump7 {
		// tag::option-with-annotation-without-prefix[]
		public String example(@ShellOption(value = { "argx" }) String arg1) {
			return "Hello " + arg1;
		}
		// end::option-with-annotation-without-prefix[]
	}

	class Dump2 {
		// tag::option-without-annotation[]
		public String example(String arg1) {
			return "Hello " + arg1;
		}
		// end::option-without-annotation[]
	}

	class Dump3 {
		// tag::option-with-annotation-shortarg[]
		public String example(
			@ShellOption(value = { "-a" }) String arg1,
			@ShellOption(value = { "-b" }) String arg2,
			@ShellOption(value = { "-c" }) String arg3
		) {
			return "Hello " + arg1;
		}
		// end::option-with-annotation-shortarg[]
	}

	class Dump4 {
		// tag::option-with-annotation-arity[]
		public String example(@ShellOption(arity = 1) String arg1) {
			return "Hello " + arg1;
		}
		// end::option-with-annotation-arity[]
	}

	class Dump5 {
		// tag::option-with-annotation-optional[]
		public String example(
			@ShellOption(defaultValue = ShellOption.NULL) String arg1
		) {
			return "Hello " + arg1;
		}
		// end::option-with-annotation-optional[]
	}

	class Dump6 {
		// tag::option-with-annotation-default[]
		public String example(
			@ShellOption(defaultValue = "defaultValue") String arg1
		) {
			return "Hello " + arg1;
		}
		// end::option-with-annotation-default[]
	}

	public void dump1() {

		// tag::option-registration-longarg[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.and()
			.build();
		// end::option-registration-longarg[]

		// tag::option-registration-shortarg[]
		CommandRegistration.builder()
			.withOption()
				.shortNames('a')
				.and()
			.withOption()
				.shortNames('b')
				.and()
			.withOption()
				.shortNames('c')
				.and()
			.build();
		// end::option-registration-shortarg[]

		// tag::option-registration-shortargbooleans[]
		CommandRegistration.builder()
			.withOption()
				.shortNames('a')
				.type(boolean.class)
				.and()
			.withOption()
				.shortNames('b')
				.type(boolean.class)
				.and()
			.withOption()
				.shortNames('c')
				.type(boolean.class)
				.and()
			.build();
		// end::option-registration-shortargbooleans[]

		// tag::option-registration-arityenum[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.arity(OptionArity.EXACTLY_ONE)
				.and()
			.build();
		// end::option-registration-arityenum[]

		// tag::option-registration-arityints[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.arity(0, 1)
				.and()
			.build();
		// end::option-registration-arityints[]

		// tag::option-registration-aritystrings-sample[]
		CommandRegistration.builder()
			.command("arity-errors")
			.withOption()
				.longNames("arg1")
				.type(String[].class)
				.required()
				.arity(1, 2)
				.and()
			.withTarget()
				.function(ctx -> {
					String[] arg1 = ctx.getOptionValue("arg1");
					return "Hello " + Arrays.asList(arg1);
				})
				.and()
			.build();
		// end::option-registration-aritystrings-sample[]

		// tag::option-registration-aritystrings-position[]
		CommandRegistration.builder()
			.command("arity-strings-2")
			.withOption()
				.longNames("arg1")
				.required()
				.type(String[].class)
				.arity(0, 2)
				.position(0)
				.and()
			.withTarget()
				.function(ctx -> {
					String[] arg1 = ctx.getOptionValue("arg1");
					return "Hello " + Arrays.asList(arg1);
				})
				.and()
			.build();
		// end::option-registration-aritystrings-position[]

		// tag::option-registration-aritystrings-noposition[]
		CommandRegistration.builder()
			.command("arity-strings-1")
			.withOption()
				.longNames("arg1")
				.required()
				.type(String[].class)
				.arity(0, 2)
				.and()
			.withTarget()
				.function(ctx -> {
					String[] arg1 = ctx.getOptionValue("arg1");
					return "Hello " + Arrays.asList(arg1);
				})
				.and()
			.build();
		// end::option-registration-aritystrings-noposition[]

		// tag::option-registration-optional[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.required()
				.and()
			.build();
		// end::option-registration-optional[]

		// tag::option-registration-positional[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.position(0)
				.and()
			.build();
		// end::option-registration-positional[]

		// tag::option-registration-default[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.defaultValue("defaultValue")
				.and()
			.build();
		// end::option-registration-default[]

		// tag::option-registration-label[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.and()
			.withOption()
				.longNames("arg2")
				.label("MYLABEL")
				.and()
			.build();
		// end::option-registration-label[]

		// tag::option-registration-naming-case-req[]
		CommandRegistration.builder()
			.withOption()
				.longNames("arg1")
				.nameModifier(name -> "x" + name)
				.and()
			.build();
		// end::option-registration-naming-case-req[]
	}

	class Dump8 {
		// tag::option-registration-naming-case-bean[]
		@Bean
		OptionNameModifier sampleOptionNameModifier() {
			return name -> "x" + name;
		}
		// end::option-registration-naming-case-bean[]

		// tag::option-registration-naming-case-sample1[]
		@ShellMethod(key = "option-naming-sample")
		public void optionNamingSample(
			@ShellOption("from_snake") String snake,
			@ShellOption("fromCamel") String camel,
			@ShellOption("from-kebab") String kebab,
			@ShellOption("FromPascal") String pascal
		) {}
		// end::option-registration-naming-case-sample1[]

	}

}
