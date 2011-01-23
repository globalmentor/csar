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

/**A manager of configurations.
@author Garret Wilson
@see Configurator
*/
public interface ConfigurationManager extends ConfigurationManaged
{

	/**Sets the given configurations, associating them with their respective classes.
	@param configurations The configurations to set.
	*/
	public void setConfigurations(final Configuration... configurations);

	/**Sets the given configuration, associating it with its class.
	@param <C> The type of configuration being set.
	@param configuration The configuration to set.
	@return The configuration previously associated with the same class, or <code>null</code> if there was no previous configuration for that class.
	@throws NullPointerException if the given configuration is <code>null</code>.
	*/
	public <C extends Configuration> C setConfiguration(final C configuration);

	/**Sets the given configuration.
	@param <C> The type of configuration being set.
	@param configurationClass The class with which to associate the configuration.
	@param configuration The configuration to set.
	@return The configuration previously associated with the given class, or <code>null</code> if there was no previous configuration for that class.
	@throws NullPointerException if the given configuration is <code>null</code>.
	*/
	public <C extends Configuration> C setConfiguration(final Class<C> configurationClass, final C configuration);

	/**Removes a configuration of the given type.
	If no configuration is associated with the specified type, no action occurs.
	@param <C> The type of configuration being removed.
	@param configurationClass The class with which the configuration is associated.
	@return The configuration previously associated with the given class, or <code>null</code> if there was no previous configuration for that class.
	*/
	public <C extends Configuration> C removeConfiguration(final Class<C> configurationClass);

}
