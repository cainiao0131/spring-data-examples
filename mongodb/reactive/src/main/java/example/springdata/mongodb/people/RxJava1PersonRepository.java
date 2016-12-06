/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.mongodb.people;

import rx.Observable;
import rx.Single;

import org.springframework.data.mongodb.repository.InfiniteStream;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.RxJava1CrudRepository;

/**
 * Repository interface to manage {@link Person} instances.
 *
 * @author Mark Paluch
 */
public interface RxJava1PersonRepository extends RxJava1CrudRepository<Person, String> {

	/**
	 * Derived query selecting by {@code lastname}.
	 *
	 * @param lastname
	 * @return
	 */
	Observable<Person> findByLastname(String lastname);

	/**
	 * String query selecting one entity.
	 *
	 * @param lastname
	 * @return
	 */
	@Query("{ 'firstname': ?0, 'lastname': ?1}")
	Single<Person> findByFirstnameAndLastname(String firstname, String lastname);

	/**
	 * Derived query selecting by {@code lastname}. {@code lastname} uses deferred resolution that does not require
	 * blocking to obtain the parameter value.
	 *
	 * @param lastname
	 * @return
	 */
	Observable<Person> findByLastname(Single<String> lastname);

	/**
	 * Derived query selecting by {@code firstname} and {@code lastname}. {@code firstname} uses deferred resolution which
	 * does not require blocking to obtain the parameter value.
	 *
	 * @param firstname
	 * @param lastname
	 * @return
	 */
	Single<Person> findByFirstnameAndLastname(Single<String> firstname, String lastname);

	/**
	 * Use a tailable cursor to emit a stream of entities as new entities are written to the capped collection.
	 *
	 * @return
	 */
	@InfiniteStream
	Observable<Person> findWithTailableCursorBy();
}