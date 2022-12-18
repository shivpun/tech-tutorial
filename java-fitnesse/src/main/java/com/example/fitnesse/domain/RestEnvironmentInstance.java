package com.example.fitnesse.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RestEnvironmentInstance {

	private static List<RestEnvironment> restEnvironment = new ArrayList<RestEnvironment>();
	

	public static RestEnvironment fetchByEnvironmentAndServiceName(String environment, String serviceName) {
		Predicate<RestEnvironment> environmentPredicate = (restEnvironment) -> restEnvironment.getEnvironment()
				.equalsIgnoreCase(environment);
		Predicate<RestEnvironment> servicePredicate = (restEnvironment) -> restEnvironment.getServiceName()
				.equals(serviceName);

		return restEnvironment.stream().filter(servicePredicate.and(environmentPredicate)).findFirst()
				.orElse(null);
	}
	
	public static void addRestEnvironment(RestEnvironment environment) {
		Predicate<RestEnvironment> environmentPredicate = (restEnvironment) -> restEnvironment.getEnvironment()
				.equalsIgnoreCase(environment.getEnvironment());
		Predicate<RestEnvironment> servicePredicate = (restEnvironment) -> restEnvironment.getServiceName()
				.equals(environment.getServiceName());
		
		if (restEnvironment.stream().filter(servicePredicate.and(environmentPredicate)).count() == 0) {
			restEnvironment.add(environment);
		}
	}
}
