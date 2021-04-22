/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.core.composite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.functor.BaseFunctorTest;
import org.apache.commons.functor.NullaryProcedure;
import org.apache.commons.functor.core.Constant;
import org.apache.commons.functor.core.NoOp;
import org.junit.Test;

/**
 * @version $Revision: 1365329 $ $Date: 2012-07-24 19:34:23 -0300 (Tue, 24 Jul
 *          2012) $
 */
public class TestConditionalNullaryProcedure extends BaseFunctorTest {

	// Functor Testing Framework
	// ------------------------------------------------------------------------

	public static NullaryProcedure mockNullaryProcedure1() {
		NullaryProcedure mockInstance = mock(NullaryProcedure.class);
		return mockInstance;
	}

	@Override
	protected Object makeFunctor() {
		return new ConditionalNullaryProcedure(Constant.TRUE, NoOp.INSTANCE, NoOp.INSTANCE);
	}

	// Tests
	// ------------------------------------------------------------------------

	@Test
	public void testRun() throws Exception {
		{
			NullaryProcedure left = mock(NullaryProcedure.class);
			NullaryProcedure right = mock(NullaryProcedure.class);
			ConditionalNullaryProcedure p = new ConditionalNullaryProcedure(Constant.TRUE, left, right);
			verify(left, times(0)).run();
			verify(right, times(0)).run();
			p.run();
			verify(left, times(1)).run();
			verify(right, times(0)).run();
			p.run();
			verify(left, times(2)).run();
			verify(right, times(0)).run();
			p.run();
			verify(left, times(3)).run();
			verify(right, times(0)).run();
		}
		{
			NullaryProcedure left = mock(NullaryProcedure.class);
			NullaryProcedure right = mock(NullaryProcedure.class);
			ConditionalNullaryProcedure p = new ConditionalNullaryProcedure(Constant.FALSE, left, right);
			verify(left, times(0)).run();
			verify(right, times(0)).run();
			p.run();
			verify(left, times(0)).run();
			verify(right, times(1)).run();
			p.run();
			verify(left, times(0)).run();
			verify(right, times(2)).run();
			p.run();
			verify(left, times(0)).run();
			verify(right, times(3)).run();
		}
	}

	@Test
	public void testEquals() throws Exception {
		ConditionalNullaryProcedure p = new ConditionalNullaryProcedure(Constant.FALSE, NoOp.INSTANCE, NoOp.INSTANCE);
		assertEquals(p, p);
		assertObjectsAreEqual(p, new ConditionalNullaryProcedure(Constant.FALSE, NoOp.INSTANCE, NoOp.INSTANCE));
		assertObjectsAreNotEqual(p, new ConditionalNullaryProcedure(Constant.TRUE, NoOp.INSTANCE, NoOp.INSTANCE));
		assertObjectsAreNotEqual(p, new ConditionalNullaryProcedure(Constant.TRUE, NoOp.INSTANCE, NoOp.INSTANCE));
		assertObjectsAreNotEqual(p, new ConditionalNullaryProcedure(Constant.FALSE,
				TestConditionalNullaryProcedure.mockNullaryProcedure1(), NoOp.INSTANCE));
		assertObjectsAreNotEqual(p, new ConditionalNullaryProcedure(Constant.FALSE, NoOp.INSTANCE,
				TestConditionalNullaryProcedure.mockNullaryProcedure1()));
		assertObjectsAreNotEqual(p, new ConditionalNullaryProcedure(Constant.TRUE, NoOp.INSTANCE));
		assertTrue(!p.equals(null));
	}

	// Classes
	// ------------------------------------------------------------------------

}
