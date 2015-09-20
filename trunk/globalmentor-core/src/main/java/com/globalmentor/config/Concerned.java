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

package com.globalmentor.config;

/**
 * An object that can retrieve <a href="https://en.wikipedia.org/wiki/Concern_%28computer_science%29>concerns</a>s.
 * @author Garret Wilson
 * @see Concern
 * @see Concerns
 */
public interface Concerned {

	/**
	 * Returns the concern for the given concern type.
	 * @param <C> The type of concern to retrieve.
	 * @param concernClass The class of concern to retrieve.
	 * @return The concern associated with the given class, or <code>null</code> if there was no concern for that class.
	 */
	public <C extends Concern> C getConcern(final Class<C> concernClass);

}