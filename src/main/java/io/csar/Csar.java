/*
 * Copyright © 2009 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.csar;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Stream;

import javax.annotation.*;

/**
 * The Concern Separation Aspect Registrar (Csar /zɑːr/) provides access to some concern (usually cross-cutting) that may configured globally or locally to some
 * section of the program.
 * <p>
 * A <dfn>concern</dfn> is some implementation of {@link Concern} and represents some concern such as logging or internationalization. Csar supports associating
 * a concern of a specific type with some thread group, which allows local concerns to be set. A global concern can also be set, which which serves as a
 * fallback for that concern type when no thread group-specific concern is defined. No fallback need be implemented if a thread group-specific concern is always
 * provided to requesting threads.
 * </p>
 * <p>
 * A concern can be made local by registering it with a thread group that is {@link Concerned}, such as {@link ConcernRegistryThreadGroup}, and creating a
 * {@link Thread} using one of the thread's constructors that specify a {@link ThreadGroup}. All threads that run in the thread group will have access to the
 * concern by calling {@link Csar#getConcern(Class)}, specifying the class of the {@link Concern} implementation. The concern registered with the
 * {@link Concerned} thread group will be returned. A concern can thus be restricted to specific areas of the program.
 * </p>
 * <p>
 * If no thread group is found that implements the concern type, a global default concern is searched for by using {@link Csar#getDefaultConcern(Class)}. If no
 * local or global concern of the requested type is found, a {@link ConcernNotFoundException} is thrown.
 * </p>
 * <p>
 * For transparent and automatic installation of default concerns, a particular concern implementation (such as an internationalization package based upon
 * resource bundles) can implement {@link ConcernProvider}, indicating the the concerns implemented by that library as specified by {@link ServiceLoader}. The
 * concern library will provide a text file <code>META-INF/services/io.csar.ConcernProvider</code> containing a line indicating the class name of the concern
 * provider:
 * </p>
 * 
 * <pre>
 * <code>com.example.FooConcernProvider</code>
 * </pre>
 * <p>
 * When Csar is first loaded, it will ask all registered concern providers for their concerns and register them as default concern providers for the concern
 * type indicated by the {@link Concern#getConcernType()} indicated by each.
 * </p>
 * @author Garret Wilson
 * @see <a href="https://en.wikipedia.org/wiki/Concern_%28computer_science%29">Concern (computer science)</a>
 * @see <a href="https://en.wikipedia.org/wiki/Cross-cutting_concern">Cross-cutting concern</a>
 */
public class Csar {

	/** The registry of fallback concerns. */
	private static final ConcernRegistry defaultConcernRegistry = new DefaultConcernRegistry();

	/** Install concerns in the default concern registry. */
	static {
		for(final ConcernProvider concernProvider : ServiceLoader.load(ConcernProvider.class)) {
			setDefaultConcerns(concernProvider.concerns());
		}
	}

	/** This class cannot be publicly instantiated. */
	private Csar() {
	}

	/**
	 * Registers the given concerns as defaults for their respective classes.
	 * @param concerns The concerns to register as defaults.
	 */
	public static void setDefaultConcerns(@Nonnull final Concern... concerns) {
		defaultConcernRegistry.registerConcerns(concerns);
	}

	/**
	 * Registers the given concerns as defaults for their respective classes.
	 * @param concerns The concerns to register as defaults.
	 */
	public static void setDefaultConcerns(@Nonnull final Stream<Concern> concerns) {
		defaultConcernRegistry.registerConcerns(concerns);
	}

	/**
	 * Registers the given concern as default for its concern type.
	 * @param <C> The type of concern being set as the default of its concern type.
	 * @param <D> The type of concern previously set as the default for its concern type.
	 * @param concern The concern to register.
	 * @return The concern previously associated with the same class.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 */
	public static <C extends Concern, D extends Concern> Optional<D> registerDefaultConcern(@Nonnull final C concern) {
		return defaultConcernRegistry.registerConcern(concern);
	}

	/**
	 * Registers the given concern as default.
	 * @param <T> The registration concern type.
	 * @param <C> The type of concern being registered.
	 * @param concernType The class with which to associate the concern.
	 * @param concern The concern to register.
	 * @return The concern previously associated with the given class.
	 * @throws NullPointerException if the given concern is <code>null</code>.
	 */
	public static <T extends Concern, C extends T> Optional<T> registerDefaultConcern(@Nonnull final Class<T> concernType, @Nonnull final C concern) {
		return defaultConcernRegistry.registerConcern(concernType, concern);
	}

	/**
	 * Returns the default concern for the given concern type.
	 * @param <T> The type of concern to retrieve.
	 * @param concernType The class of concern to retrieve.
	 * @return The concern associated with the given class, or <code>null</code> if there was no concern for that class.
	 */
	public static <T extends Concern> Optional<T> getDefaultConcern(@Nonnull final Class<T> concernType) {
		return defaultConcernRegistry.getConcern(concernType);
	}

	/**
	 * Unregisters a default concern of the given type. If no concern is associated with the specified type, no action occurs.
	 * @param <T> The type of concern being removed.
	 * @param concernType The class with which the concern is associated.
	 * @return The concern previously associated with the given class, or <code>null</code> if there was no previous concern for that class.
	 */
	public static <T extends Concern> Optional<T> unregisterDefaultConcern(@Nonnull final Class<T> concernType) {
		return defaultConcernRegistry.unregisterConcern(concernType);
	}

	/**
	 * Retrieves the concern of the given type for the current thread.
	 * <p>
	 * A local concern is first searched for using the first {@link Concerned} thread group, if any, of the current thread. If no {@link Concerned} thread group
	 * is found for the thread, or no such concern is set for the thread group, a default concern is searched for using {@link #getDefaultConcern(Class)}. If no
	 * appropriate concern can be found, a {@link ConcernNotFoundException} is thrown.
	 * </p>
	 * @param <C> The type of concern to retrieve.
	 * @param concernType The class indicating the type of concern to retrieve.
	 * @return The concern of the requested type.
	 * @throws ConcernNotFoundException if no concern of the requested type could be found.
	 * @see Concerned#getConcern(Class)
	 * @see #getDefaultConcern(Class)
	 * @see Thread#currentThread()
	 */
	public static @Nonnull <C extends Concern> C getConcern(@Nonnull final Class<C> concernType) {
		return getConcern(Thread.currentThread(), concernType); //retrieve a concern for the current thread
	}

	/**
	 * Retrieves the concern of the given type for the indicated thread.
	 * <p>
	 * A local concern is first searched for using the first {@link Concerned} thread group, if any, of the given thread. If no {@link Concerned} thread group is
	 * found for the thread, or no such concern is set for the thread group, a default concern is searched for using {@link #getDefaultConcern(Class)}. If no
	 * appropriate concern can be found, a {@link ConcernNotFoundException} is thrown.
	 * </p>
	 * @param <T> The type of concern to retrieve.
	 * @param thread The thread for which a concern should be retrieved.
	 * @param concernType The class indicating the type of concern to retrieve.
	 * @return The concern of the requested type.
	 * @throws ConcernNotFoundException if no concern of the requested type could be found.
	 * @see Concerned#getConcern(Class)
	 * @see #getDefaultConcern(Class)
	 */
	protected static @Nonnull <T extends Concern> T getConcern(@Nonnull final Thread thread, @Nonnull final Class<T> concernType) {
		Optional<T> concern = Optional.empty(); //search for a thread-group-local concern
		final Concerned concernedThreadGroup = getThreadGroup(thread, Concerned.class); //get the concerned thread group
		if(concernedThreadGroup != null) { //if we found the concerned thread group
			concern = concernedThreadGroup.getConcern(concernType); //ask the concerned thread group for the concern
		}
		if(!concern.isPresent()) { //search for a default concern (can be improved with Optional.or() in Java 9)
			concern = getDefaultConcern(concernType); //find a default concern
		}
		return concern.orElseThrow(() -> new ConcernNotFoundException("No local or default concern could be found for concern type " + concernType.getName()));
	}

	/**
	 * Walks up the thread group chain of the given thread to find the thread group of the given type.
	 * <p>
	 * Necessary duplication of code originally in <code>com.globalmentor.java.Threads</code>.
	 * </p>
	 * @param <TG> The type of thread group to find.
	 * @param thread The thread at which the thread group search should begin.
	 * @param threadGroupClass The class of the type of thread group to find.
	 * @return The first thread group of the given type, or <code>null</code> if no thread group of the given type could be found.
	 * @throws NullPointerException if the given thread and/or thread group class is <code>null</code>.
	 */
	private static @Nullable <TG> TG getThreadGroup(@Nonnull final Thread thread, @Nonnull final Class<TG> threadGroupClass) {
		final ThreadGroup threadGroup = thread.getThreadGroup(); //get the thread's thread group
		return threadGroup != null ? getThreadGroup(threadGroup, threadGroupClass) : null; //if the thread has a thread group, look for the thread group of the requested type 
	}

	/**
	 * Walks up the thread group chain of the given thread group to find the thread group of the given type or that implements the given interface.
	 * <p>
	 * Necessary duplication of code originally in <code>com.globalmentor.java.Threads</code>.
	 * </p>
	 * @param <TG> The type of thread group to find.
	 * @param threadGroup The thread group at which the search should begin.
	 * @param threadGroupClass The class of the type of thread group to find.
	 * @return The first thread group of the given type, or <code>null</code> if no thread group of the given type could be found.
	 * @throws NullPointerException if the given thread group and/or thread group class is <code>null</code>.
	 */
	private static @Nullable <TG> TG getThreadGroup(@Nonnull ThreadGroup threadGroup, @Nonnull final Class<TG> threadGroupClass) {
		do {
			if(threadGroupClass.isInstance(threadGroup)) { //if this is the required thread group type
				return threadGroupClass.cast(threadGroup); //return the thread group as the type
			}
			threadGroup = threadGroup.getParent(); //check this thread group's parent thread group
		} while(threadGroup != null); //stop looking if we run out of thread groups
		return null; //we were unable to find the required thread group
	}

}
