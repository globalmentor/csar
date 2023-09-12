/*
 * Copyright © 2016 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.csar;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.*;

/**
 * Tests of {@link Csar}.
 * @author Garret Wilson
 */
public class CsarTest {

	/**
	 * Custom concern for testing Csar.
	 * @author Garret Wilson
	 */
	private static class Environment extends Properties implements Concern {
		private static final long serialVersionUID = 1L;
	}

	/**
	 * @see Csar#setDefaultConcerns(Concern...)
	 * @see Csar#run(Runnable, Concern...)
	 * @see Csar#getConcern(Class)
	 */
	@Test
	public void testCsarDefault() throws InterruptedException {
		final Environment defaultEnvironment = new Environment();
		defaultEnvironment.setProperty("test", "default");
		Csar.setDefaultConcerns(defaultEnvironment);

		final AtomicReference<String> result = new AtomicReference<>();

		Csar.run(() -> {
			final Environment env = Csar.getConcern(Environment.class);
			result.set(env.getProperty("test"));
		}).join();

		assertThat(result.get(), is("default"));
	}

	/**
	 * @see Csar#setDefaultConcerns(Concern...)
	 * @see Csar#run(Runnable, Concern...)
	 * @see Csar#getConcern(Class)
	 */
	@Test
	public void testCsarRunSingleThread() throws InterruptedException {
		final Environment defaultEnvironment = new Environment();
		defaultEnvironment.setProperty("test", "default");
		Csar.setDefaultConcerns(defaultEnvironment);

		final AtomicReference<String> result = new AtomicReference<>();

		final Environment environment = new Environment();
		environment.setProperty("test", "foobar");

		Csar.run(() -> {
			final Environment env = Csar.getConcern(Environment.class);
			result.set(env.getProperty("test"));
		}, environment).join();

		assertThat(result.get(), is("foobar"));
	}

	/**
	 * @see Csar#setDefaultConcerns(Concern...)
	 * @see Csar#run(Runnable, Concern...)
	 * @see Csar#getConcern(Class)
	 */
	@Test
	public void testCsarRunMultipleThreads() throws InterruptedException {
		final Environment defaultEnvironment = new Environment();
		defaultEnvironment.setProperty("test", "default");
		Csar.setDefaultConcerns(defaultEnvironment);

		final AtomicReference<String> result1 = new AtomicReference<>();
		final AtomicReference<String> result2 = new AtomicReference<>();
		final AtomicReference<String> result3 = new AtomicReference<>();

		final Environment environment1 = new Environment();
		environment1.setProperty("test", "foo");
		final Environment environment2 = new Environment();
		environment2.setProperty("test", "bar");
		final Environment environment3 = new Environment();
		environment3.setProperty("test", "foobar");

		final Thread thread1 = Csar.run(() -> {
			final Environment env = Csar.getConcern(Environment.class);
			result1.set(env.getProperty("test"));
		}, environment1);
		final Thread thread2 = Csar.run(() -> {
			final Environment env = Csar.getConcern(Environment.class);
			result2.set(env.getProperty("test"));
		}, environment2);
		final Thread thread3 = Csar.run(() -> {
			final Environment env = Csar.getConcern(Environment.class);
			result3.set(env.getProperty("test"));
		}, environment3);

		thread1.join();
		thread2.join();
		thread3.join();

		assertThat(result1.get(), is("foo"));
		assertThat(result2.get(), is("bar"));
		assertThat(result3.get(), is("foobar"));
	}

}
