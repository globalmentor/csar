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

/**An object that can retrieve configurations.
@author Garret Wilson
@see Configurator
*/
public interface ConfigurationManaged
{

	/**Returns the configuration for the given configuration type.
	@param <C> The type of configuration to retrieve.
	@param configurationClass The class of configuration to retrieve.
	@return The configuration associated with the given class, or <code>null</code> if there was no configuration for that class.
 */
	public <C extends Configuration> C getConfiguration(final Class<C> configurationClass);

}