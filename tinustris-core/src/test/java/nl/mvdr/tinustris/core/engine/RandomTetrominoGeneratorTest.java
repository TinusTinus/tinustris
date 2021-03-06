/*
 * Copyright 2013-2015 Martijn van de Rijdt 
 * 
 * This file is part of Tinustris.
 * 
 * Tinustris is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Tinustris is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Tinustris. If not, see <http://www.gnu.org/licenses/>.
 */
package nl.mvdr.tinustris.core.engine;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import nl.mvdr.tinustris.core.engine.RandomTetrominoGenerator;
import nl.mvdr.tinustris.core.model.Tetromino;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test class for {@link RandomTetrominoGenerator}.
 * 
 * @author Martijn van de Rijdt
 */
public class RandomTetrominoGeneratorTest {
    /** Random seed. */
    private static final long SEED = 693741264L;
    /** The number of threads in the executor service used for concurrency tests. */
    int NUM_THREADS = 5;
    
    /** Invokes the get method a few times and checks that it returns non-null values. */
    @Test
    public void testGet() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator();
        for (int i = 0; i != 10; i++) {
            Assert.assertNotNull("failed on i = " + i, generator.get(i));
        }
    }
    
    /** Tests that the get method returns the same value when invoked with the same index multiple times. */
    @Test
    public void testGetMultipleCalls() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator();
        Tetromino first = generator.get(0);
        
        Assert.assertEquals(first, generator.get(0));
    }
    
    /**
     * Tests that the get method returns the same value when invoked with the same index multiple times, even when other
     * indices have been used as well.
     */
    @Test
    public void testGetMultipleCallsLater() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator();
        Tetromino first = generator.get(0);
        generator.get(1);
        generator.get(2);
        
        Assert.assertEquals(first, generator.get(0));
    }
    
    /**
     * Tests that the get method works correctly when skipping a tetromino index (even though this should never happen
     * in practice).
     */
    @Test
    public void testGetSkipValue() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator();
        Assert.assertNotNull(generator.get(1));
    }
    
    /**
     * Tests that the get method works correctly when skipping a tetromino index (even though this should never happen
     * in practice).
     */
    @Test
    public void testGetSkipValueMultipleTimes() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator();
        Tetromino first = generator.get(1);
        
        Assert.assertEquals(first, generator.get(1));
    }
    
    /** Test case with a negative index. */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testNegativeValue() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator();
        generator.get(-1);
    }
    
    /** Tests that reusing the same random seed produces the same results. */
    @Test
    public void testRandomSeed() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator(0L);
        Tetromino first = generator.get(0);
        generator = new RandomTetrominoGenerator(0L);
        
        Assert.assertEquals(first, generator.get(0));
    }
    
    /** Tests that reusing the same random seed in a new JVM instance still produces the same result. */
    @Test
    public void testRandomSeedFixed() {
        RandomTetrominoGenerator generator = new RandomTetrominoGenerator(0L);
        
        Assert.assertEquals(Tetromino.S, generator.get(0));
        Assert.assertEquals(Tetromino.T, generator.get(1));
        Assert.assertEquals(Tetromino.L, generator.get(2));
        Assert.assertEquals(Tetromino.T, generator.get(3));
        Assert.assertEquals(Tetromino.L, generator.get(4));
        Assert.assertEquals(Tetromino.I, generator.get(5));
        Assert.assertEquals(Tetromino.T, generator.get(6));
        Assert.assertEquals(Tetromino.O, generator.get(7));
        Assert.assertEquals(Tetromino.Z, generator.get(8));
        Assert.assertEquals(Tetromino.T, generator.get(9));
    }
    
    /** Tests whether generators with the same random seed also result in the same value. */
    @Test
    public void testRepeatable() {
        RandomTetrominoGenerator generator0 = new RandomTetrominoGenerator(SEED);
        RandomTetrominoGenerator generator1 = new RandomTetrominoGenerator(SEED);
        
        Tetromino tetromino0 = generator0.get(100);
        Tetromino tetromino1 = generator1.get(100);
        
        Assert.assertEquals(tetromino0, tetromino1);
    }

    /**
     * Test case where {@link RandomTetrominoGenerator#get(int)} is called from several different threads. We expect
     * each call to return the same tetromino.
     */
    @Test
    public void testGetMultiThreaded() {
        // repeat the test a few times, to increase the likelyhood of failure in case of synchronisation bugs
        for (int i = 0; i != 100; i++) {
            RandomTetrominoGenerator generator = new RandomTetrominoGenerator();

            testMultithreaded(generator);
        }
    }
    
    /** Tests whether generators with the same random seed also result in the same value, in a multithreaded environment. */
    @Test
    public void testRepeatableMultithreaded() {
        // repeat the test a few times, to increase the likelyhood of failure in case of synchronisation bugs
        for (int i = 0; i != 100; i++) {
            RandomTetrominoGenerator generator0 = new RandomTetrominoGenerator(SEED);
            RandomTetrominoGenerator generator1 = new RandomTetrominoGenerator(SEED);

            Tetromino tetromino0 = testMultithreaded(generator0);
            Tetromino tetromino1 = testMultithreaded(generator1);

            Assert.assertEquals("Failed at iteration " + 0, tetromino0, tetromino1);
        }
    }

    /**
     * Calls {@link RandomTetrominoGenerator#get(int)} with the same value from several different threads. Checks that
     * each thread results in the same tetromino value.
     * 
     * @param generator generator
     * @return tetromino value
     */
    private Tetromino testMultithreaded(final RandomTetrominoGenerator generator) {
        ExecutorService service = Executors.newFixedThreadPool(NUM_THREADS);
        Callable<Tetromino> getTetrominoTask = () -> generator.get(10);
        List<Future<Tetromino>> futures = IntStream.range(0, NUM_THREADS)
            .mapToObj(i -> getTetrominoTask)
            .map(service::submit)
            .collect(Collectors.toList());
        
        // wait for all threads to complete and combine the results into a Set
        Set<Tetromino> results = futures.stream()
            .map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new AssertionError("Unexpected exception." , e);
                }
            })
            .collect(Collectors.toSet());
        
        // check that each thread resulted in the same, non-null, value
        Assert.assertFalse(results.contains(null));
        Assert.assertEquals("more than one unique tetromino returned", 1, results.size());
        
        // return the tetromino
        return results.iterator().next();
    }
}
