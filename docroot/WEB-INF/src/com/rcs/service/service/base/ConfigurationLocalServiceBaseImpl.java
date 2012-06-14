/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.rcs.service.service.base;

import com.liferay.counter.service.CounterLocalService;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import com.rcs.service.model.Configuration;
import com.rcs.service.service.ConfigurationLocalService;
import com.rcs.service.service.persistence.ConfigurationPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base implementation of the configuration local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.rcs.service.service.impl.ConfigurationLocalServiceImpl}.
 * </p>
 *
 * @author RCS - Pablo Rendon
 * @see com.rcs.service.service.impl.ConfigurationLocalServiceImpl
 * @see com.rcs.service.service.ConfigurationLocalServiceUtil
 * @generated
 */
public abstract class ConfigurationLocalServiceBaseImpl
	implements ConfigurationLocalService, IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.rcs.service.service.ConfigurationLocalServiceUtil} to access the configuration local service.
	 */

	/**
	 * Adds the configuration to the database. Also notifies the appropriate model listeners.
	 *
	 * @param configuration the configuration
	 * @return the configuration that was added
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration addConfiguration(Configuration configuration)
		throws SystemException {
		configuration.setNew(true);

		configuration = configurationPersistence.update(configuration, false);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.reindex(configuration);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}

		return configuration;
	}

	/**
	 * Creates a new configuration with the primary key. Does not add the configuration to the database.
	 *
	 * @param configurationId the primary key for the new configuration
	 * @return the new configuration
	 */
	public Configuration createConfiguration(long configurationId) {
		return configurationPersistence.create(configurationId);
	}

	/**
	 * Deletes the configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param configurationId the primary key of the configuration
	 * @throws PortalException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteConfiguration(long configurationId)
		throws PortalException, SystemException {
		Configuration configuration = configurationPersistence.remove(configurationId);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.delete(configuration);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}
	}

	/**
	 * Deletes the configuration from the database. Also notifies the appropriate model listeners.
	 *
	 * @param configuration the configuration
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteConfiguration(Configuration configuration)
		throws SystemException {
		configurationPersistence.remove(configuration);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.delete(configuration);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return configurationPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return configurationPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return configurationPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return configurationPersistence.countWithDynamicQuery(dynamicQuery);
	}

	public Configuration fetchConfiguration(long configurationId)
		throws SystemException {
		return configurationPersistence.fetchByPrimaryKey(configurationId);
	}

	/**
	 * Returns the configuration with the primary key.
	 *
	 * @param configurationId the primary key of the configuration
	 * @return the configuration
	 * @throws PortalException if a configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration getConfiguration(long configurationId)
		throws PortalException, SystemException {
		return configurationPersistence.findByPrimaryKey(configurationId);
	}

	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return configurationPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	 * </p>
	 *
	 * @param start the lower bound of the range of configurations
	 * @param end the upper bound of the range of configurations (not inclusive)
	 * @return the range of configurations
	 * @throws SystemException if a system exception occurred
	 */
	public List<Configuration> getConfigurations(int start, int end)
		throws SystemException {
		return configurationPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of configurations.
	 *
	 * @return the number of configurations
	 * @throws SystemException if a system exception occurred
	 */
	public int getConfigurationsCount() throws SystemException {
		return configurationPersistence.countAll();
	}

	/**
	 * Updates the configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param configuration the configuration
	 * @return the configuration that was updated
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration updateConfiguration(Configuration configuration)
		throws SystemException {
		return updateConfiguration(configuration, true);
	}

	/**
	 * Updates the configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param configuration the configuration
	 * @param merge whether to merge the configuration with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	 * @return the configuration that was updated
	 * @throws SystemException if a system exception occurred
	 */
	public Configuration updateConfiguration(Configuration configuration,
		boolean merge) throws SystemException {
		configuration.setNew(false);

		configuration = configurationPersistence.update(configuration, merge);

		Indexer indexer = IndexerRegistryUtil.getIndexer(getModelClassName());

		if (indexer != null) {
			try {
				indexer.reindex(configuration);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(se, se);
				}
			}
		}

		return configuration;
	}

	/**
	 * Returns the configuration local service.
	 *
	 * @return the configuration local service
	 */
	public ConfigurationLocalService getConfigurationLocalService() {
		return configurationLocalService;
	}

	/**
	 * Sets the configuration local service.
	 *
	 * @param configurationLocalService the configuration local service
	 */
	public void setConfigurationLocalService(
		ConfigurationLocalService configurationLocalService) {
		this.configurationLocalService = configurationLocalService;
	}

	/**
	 * Returns the configuration persistence.
	 *
	 * @return the configuration persistence
	 */
	public ConfigurationPersistence getConfigurationPersistence() {
		return configurationPersistence;
	}

	/**
	 * Sets the configuration persistence.
	 *
	 * @param configurationPersistence the configuration persistence
	 */
	public void setConfigurationPersistence(
		ConfigurationPersistence configurationPersistence) {
		this.configurationPersistence = configurationPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the resource remote service.
	 *
	 * @return the resource remote service
	 */
	public ResourceService getResourceService() {
		return resourceService;
	}

	/**
	 * Sets the resource remote service.
	 *
	 * @param resourceService the resource remote service
	 */
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * Returns the resource persistence.
	 *
	 * @return the resource persistence
	 */
	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	/**
	 * Sets the resource persistence.
	 *
	 * @param resourcePersistence the resource persistence
	 */
	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		PersistedModelLocalServiceRegistryUtil.register("com.rcs.service.model.Configuration",
			configurationLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.rcs.service.model.Configuration");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return Configuration.class;
	}

	protected String getModelClassName() {
		return Configuration.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = configurationPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = ConfigurationLocalService.class)
	protected ConfigurationLocalService configurationLocalService;
	@BeanReference(type = ConfigurationPersistence.class)
	protected ConfigurationPersistence configurationPersistence;
	@BeanReference(type = CounterLocalService.class)
	protected CounterLocalService counterLocalService;
	@BeanReference(type = ResourceLocalService.class)
	protected ResourceLocalService resourceLocalService;
	@BeanReference(type = ResourceService.class)
	protected ResourceService resourceService;
	@BeanReference(type = ResourcePersistence.class)
	protected ResourcePersistence resourcePersistence;
	@BeanReference(type = UserLocalService.class)
	protected UserLocalService userLocalService;
	@BeanReference(type = UserService.class)
	protected UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private static Log _log = LogFactoryUtil.getLog(ConfigurationLocalServiceBaseImpl.class);
	private String _beanIdentifier;
}