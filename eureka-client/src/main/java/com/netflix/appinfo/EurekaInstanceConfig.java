/*
 * Copyright 2012 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.netflix.appinfo;

import java.util.Map;

import com.netflix.discovery.DiscoveryClient;

/**
 * Configuration information required by the instance to register with Eureka
 * server. Once registered, users can look up information from
 * {@link DiscoveryClient} based on virtual hostname (also called VIPAddress),
 * the most common way of doing it or by other means to get the information
 * necessary to talk to other instances registered with <em>Eureka</em>.
 * 
 * 
 * <p>
 * Note that all configurations are not effective at runtime unless and
 * otherwise specified.
 * </p>
 * 
 * @author Karthik Ranganathan
 * 
 */
public interface EurekaInstanceConfig {
    /**
     * Get the name of the application to be registered with eureka.
     * 
     * @return string denoting the name.
     */
    String getAppname();

    /**
     * Indicates whether the instance should be enabled for taking traffic as
     * soon as it is registered with eureka. Sometimes the application might
     * need to do some pre-processing before it is ready to take traffic.
     * 
     * @return true to immediately start taking traffic, false otherwise.
     */
    boolean isInstanceEnabledOnit();

    /**
     * Get the <code>non-secure</code> port on which the instance should receive
     * traffic.
     * 
     * @return the non-secure port on which the instance should receive traffic.
     */
    int getNonSecurePort();

    /**
     * Get the <code>Secure port</code> on which the instance should receive
     * traffic.
     * 
     * @return the non-secure port on which the instance should receive traffic.
     */
    int getSecurePort();

    /**
     * Indicates whether the <code>non-secure</code> port should be enabled for
     * traffic or not.
     * 
     * @return true if the <code>non-secure</code> port is enabled, false
     *         otherwise.
     */
    boolean isNonSecurePortEnabled();

    /**
     * Indicates whether the <code>secure</code> port should be enabled for
     * traffic or not.
     * 
     * @return true if the <code>secure</code> port is enabled, false otherwise.
     */
    boolean getSecurePortEnabled();

    /**
     * Indicates how often (in seconds) the eureka client needs to send
     * heartbeats to eureka server to indicate that it is still alive. If the
     * heartbeats are not received for the period specified in
     * {@link #getLeaseExpirationDurationInSeconds()}, eureka server will remove
     * the instance from its view, there by disallowing traffic to this
     * instance.
     * 
     * <p>
     * Note that the instance could still not take traffic if it implements
     * {@link HealthCheckCallback} and then decides to make itself unavailable.
     * </p>
     * 
     * @return time in seconds
     */
    int getLeaseRenewalIntervalInSeconds();

    /**
     * Indicates the time in seconds that the eureka server waits since it
     * received the last heartbeat before it can remove this instance from its
     * view and there by disallowing traffic to this instance.
     * 
     * <p>
     * Setting this value too long could mean that the traffic could be routed
     * to the instance even though the instance is not alive. Setting this value
     * too small could mean, the instance may be taken out of traffic because of
     * temporary network glitches.This value to be set to atleast higher than
     * the value specified in {@Link #getLeaseRenewalIntervalInSeconds()}
     * .
     * </p>
     * 
     * @return value indicating time in seconds.
     */
    int getLeaseExpirationDurationInSeconds();

    /**
     * Gets the virtual host name defined for this instance.
     * 
     * <p>
     * This is typically the way other instance would find this instance by
     * using the virtual host name.Think of this as similar to the fully
     * qualified domain name, that the users of your services will need to find
     * this instance.
     * </p>
     * 
     * @return the string indicating the virtual host name which the clients use
     *         to call this service.
     */
    String getVirtualHostName();

    /**
     * Gets the secure virtual host name defined for this instance.
     * 
     * <p>
     * This is typically the way other instance would find this instance by
     * using the secure virtual host name.Think of this as similar to the fully
     * qualified domain name, that the users of your services will need to find
     * this instance.
     * </p>
     * 
     * @return the string indicating the secure virtual host name which the clients use
     *         to call this service.
     */
    String getSecureVirtualHostName();

    /**
     * Gets the <code>AWS autoscaling group name</code> associated with this
     * instance. This information is specifically used in an AWS environment to
     * automatically put an instance out of service after the instance is
     * launched and it has been disabled for traffic..
     * 
     * @return the autoscaling group name associated with this instance.
     */
    String getASGName();

    /**
     * Gets the hostname associated with this instance. This is the exact name
     * that would be used by other instances to make calls.
     * 
     * @param refresh
     *            true if the information needs to be refetched, false
     *            otherwise.
     * @return hostname of this instance which is identifiable by other
     *         instances for making remote calls.
     */
    String getHostName(boolean refresh);

    /**
     * Gets the metadata name/value pairs associated with this instance. This
     * information is sent to eureka server and can be used by other instances.
     * 
     * @return Map containing application-specific metadata.
     */
    Map<String, String> getMetadataMap();

    /**
     * Returns the data center this instance is deployed. This information is
     * used to get some AWS specific instance information if the instance is
     * deployed in AWS.
     * 
     * @return information that indicates which data center this instance is
     *         deployed in.
     */
    DataCenterInfo getDataCenterInfo();

    /**
     * Get the IPAdress of the instance. This information is for academic
     * purposes only as the communication from other instances primarily happen
     * using the information supplied in {@link #getHostName(boolean)}.
     * 
     * @return the ip address of this instance.
     */
    String getIpAddress();
}
